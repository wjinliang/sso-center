package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;
import java.util.Map;

import com.topie.ssocenter.freamwork.authorization.model.UserRole;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/3 说明：
 */
public interface UserRoleService extends IService<UserRole> {

    int insertUserRole(UserRole role);

    int updateUserRole(UserRole role);

    UserRole findUserRoleById(String id);

    int deleteUserRole(String id);

    int insertUserRoleFunction(String roleId, Long functionId);

    List<Map> findUserRoleMatchUpFunctions();
}
