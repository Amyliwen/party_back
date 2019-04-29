package com.party.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.party.example.service.AccountService;
import com.party.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: liwen
 * @description: 用户/角色/权限相关controller
 */
@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountService accountService;

	/**
	 * 查询用户列表
	 */
	@RequiresPermissions("account:list")
	@GetMapping("/list")
	public JSONObject listAccount(HttpServletRequest request) {
		return accountService.listAccount(CommonUtil.request2Json(request));
	}

	@RequiresPermissions("account:add")
	@PostMapping("/addAccount")
	public JSONObject addAccount(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "accountname, password, nickname,   roleId");
		return accountService.addAccount(requestJson);
	}

	@RequiresPermissions("account:update")
	@PostMapping("/updateAccount")
	public JSONObject updateAccount(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, " nickname,   roleId, deleteStatus, accountId");
		return accountService.updateAccount(requestJson);
	}

	@RequiresPermissions(value = {"account:add", "account:update"}, logical = Logical.OR)
	@GetMapping("/getAllRoles")
	public JSONObject getAllRoles() {
		return accountService.getAllRoles();
	}

	/**
	 * 角色列表
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listRole")
	public JSONObject listRole() {
		return accountService.listRole();
	}

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listAllPermission")
	public JSONObject listAllPermission() {
		return accountService.listAllPermission();
	}

	/**
	 * 新增角色
	 */
	@RequiresPermissions("role:add")
	@PostMapping("/addRole")
	public JSONObject addRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleName,permissions");
		return accountService.addRole(requestJson);
	}

	/**
	 * 修改角色
	 */
	@RequiresPermissions("role:update")
	@PostMapping("/updateRole")
	public JSONObject updateRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId,roleName,permissions");
		return accountService.updateRole(requestJson);
	}

	/**
	 * 删除角色
	 */
	@RequiresPermissions("role:delete")
	@PostMapping("/deleteRole")
	public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId");
		return accountService.deleteRole(requestJson);
	}
}
