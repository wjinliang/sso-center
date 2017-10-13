package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.UserMenu;

public interface UserMenuMapper extends Mapper<UserMenu> {

	List<UserMenu> selectMenusByRoleCode(String code);

	List<UserMenu> findMenuList(UserMenu menu);

	List<UserMenu> findMenusByUserId(String id);
}