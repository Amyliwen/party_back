package com.party.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.party.example.service.UserService;
import com.party.example.util.CommonUtil;
import com.party.example.util.FileUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: hxy
 * @description: 用户/角色/权限相关controller
 * @date: 2017/11/2 10:19
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	private FileUtils fileUtils;

	/**
	 * 查询用户列表
	 */
	@RequiresPermissions("user:list")
	@GetMapping("/list")
	public JSONObject listUser(HttpServletRequest request) {
		return userService.listUser(CommonUtil.request2Json(request));
	}

	@RequiresPermissions("user:add")
	@PostMapping("/addUser")
	public JSONObject addUser(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "username, password, chineseName,   roleId");
		return userService.addUser(requestJson);
	}

	@RequiresPermissions("user:update")
	@PostMapping("/updateUser")
	public JSONObject updateUser(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, " chineseName,  roleId, deleteStatus, userId");
		return userService.updateUser(requestJson);
	}

	@RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
	@GetMapping("/getAllRoles")
	public JSONObject getAllRoles() {
		return userService.getAllRoles();
	}

//	@PostMapping("/addPhoto")
//	public JSONObject addPhoto(MultipartFile file, String userId){
//		String serverPort = "8080";
//		String fileUploadPath = "./temp";
//		try {
//			fileUtils.storeFile(file, userId, serverPort, fileUploadPath);
//			userService.updateUser()
//			return ;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
	/**
	 * 角色列表
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listRole")
	public JSONObject listRole() {
		return userService.listRole();
	}

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listAllPermission")
	public JSONObject listAllPermission() {
		return userService.listAllPermission();
	}

	/**
	 * 新增角色
	 */
	@RequiresPermissions("role:add")
	@PostMapping("/addRole")
	public JSONObject addRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleName,permissions");
		return userService.addRole(requestJson);
	}

	/**
	 * 修改角色
	 */
	@RequiresPermissions("role:update")
	@PostMapping("/updateRole")
	public JSONObject updateRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId,roleName,permissions");
		return userService.updateRole(requestJson);
	}

	/**
	 * 删除角色
	 */
	@RequiresPermissions("role:delete")
	@PostMapping("/deleteRole")
	public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId");
		return userService.deleteRole(requestJson);
	}
}
