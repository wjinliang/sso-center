package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topie.ssocenter.freamwork.authorization.dao.UserRoleMapper;
import com.topie.ssocenter.freamwork.authorization.model.UserRole;
import com.topie.ssocenter.freamwork.authorization.service.UserRoleService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/3 说明：
 */
@Service("roleService")
public class UserRoleServiceImpl extends BaseService<UserRole> implements UserRoleService {

    @Autowired
    UserRoleMapper roleMapper;

    @Override
    public int insertUserRole(UserRole role) {
        return getMapper().insert(role);
    }

    @Override
    public int updateUserRole(UserRole role) {
        return getMapper().updateByPrimaryKey(role);
    }

    @Override
    public UserRole findUserRoleById(String id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public int deleteUserRole(String id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int insertUserRoleFunction(String roleId, Long functionId) {
        return roleMapper.insertRoleMenu(roleId, functionId);
    }

    @Override
    public List<Map> findUserRoleMatchUpFunctions() {
        return roleMapper.findRoleMatchUpMenus();
    }
}
