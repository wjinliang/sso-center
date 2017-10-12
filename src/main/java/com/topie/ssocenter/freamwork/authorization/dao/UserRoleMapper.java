package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.UserRole;

public interface UserRoleMapper extends Mapper<UserRole> {

    int insertRoleMenu(String roleId, Long menuId);

    List<Map> findRoleMatchUpMenus();
}