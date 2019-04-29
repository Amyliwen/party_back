package com.party.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.party.example.dao.AccountDao;
import com.party.example.service.AccountService;
import com.party.example.util.CommonUtil;
import com.party.example.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: hxy
 * @description: 用户/角色/权限
 * @date: 2017/11/2 10:18
 */
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;

	/**
	 * 用户列表
	 */
	@Override
	public JSONObject listAccount(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = accountDao.countAccount(jsonObject);
		List<JSONObject> list = accountDao.listAccount(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 添加用户
	 */
	@Override
	public JSONObject addAccount(JSONObject jsonObject) {
		int exist = accountDao.queryExistUsername(jsonObject);
		if (exist > 0) {
			return CommonUtil.errorJson(ErrorEnum.E_10009);
		}
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(now);
		jsonObject.put("createTime",nowTime);
		accountDao.addAccount(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 查询所有的角色
	 * 在添加/修改用户的时候要使用此方法
	 */
	@Override
	public JSONObject getAllRoles() {
		List<JSONObject> roles = accountDao.getAllRoles();
		return CommonUtil.successPage(roles);
	}

	/**
	 * 修改用户
	 */
	@Override
	public JSONObject updateAccount(JSONObject jsonObject) {
		accountDao.updateAccount(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 角色列表
	 */
	@Override
	public JSONObject listRole() {
		List<JSONObject> roles = accountDao.listRole();
		return CommonUtil.successPage(roles);
	}

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	@Override
	public JSONObject listAllPermission() {
		List<JSONObject> permissions = accountDao.listAllPermission();
		return CommonUtil.successPage(permissions);
	}

	/**
	 * 添加角色
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject addRole(JSONObject jsonObject) {

		accountDao.insertRole(jsonObject);
		accountDao.insertRolePermission(jsonObject.getString("roleId"), (List<Integer>) jsonObject.get("permissions"));
		return CommonUtil.successJson();
	}

	/**
	 * 修改角色
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject updateRole(JSONObject jsonObject) {
		String roleId = jsonObject.getString("roleId");
		List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
		JSONObject roleInfo = accountDao.getRoleAllInfo(jsonObject);
		Set<Integer> oldPerms = (Set<Integer>) roleInfo.get("permissionIds");
		//修改角色名称
		dealRoleName(jsonObject, roleInfo);
		//添加新权限
		saveNewPermission(roleId, newPerms, oldPerms);
		//移除旧的不再拥有的权限
		removeOldPermission(roleId, newPerms, oldPerms);
		return CommonUtil.successJson();
	}

	/**
	 * 修改角色名称
	 */
	private void dealRoleName(JSONObject paramJson, JSONObject roleInfo) {
		String roleName = paramJson.getString("roleName");
		if (!roleName.equals(roleInfo.getString("roleName"))) {
			accountDao.updateRoleName(paramJson);
		}
	}

	/**
	 * 为角色添加新权限
	 */
	private void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
		List<Integer> waitInsert = new ArrayList<>();
		for (Integer newPerm : newPerms) {
			if (!oldPerms.contains(newPerm)) {
				waitInsert.add(newPerm);
			}
		}
		if (waitInsert.size() > 0) {
			accountDao.insertRolePermission(roleId, waitInsert);
		}
	}

	/**
	 * 删除角色 旧的 不再拥有的权限
	 */
	private void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
		List<Integer> waitRemove = new ArrayList<>();
		for (Integer oldPerm : oldPerms) {
			if (!newPerms.contains(oldPerm)) {
				waitRemove.add(oldPerm);
			}
		}
		if (waitRemove.size() > 0) {
			accountDao.removeOldPermission(roleId, waitRemove);
		}
	}

	/**
	 * 删除角色
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject deleteRole(JSONObject jsonObject) {
		JSONObject roleInfo = accountDao.getRoleAllInfo(jsonObject);
		List<JSONObject> accounts = (List<JSONObject>) roleInfo.get("accounts");
		if (accounts != null && accounts.size() > 0) {
			return CommonUtil.errorJson(ErrorEnum.E_10008);
		}
		accountDao.removeRole(jsonObject);
		accountDao.removeRoleAllPermission(jsonObject);
		return CommonUtil.successJson();
	}
}
