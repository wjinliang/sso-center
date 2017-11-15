package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topie.ssocenter.freamwork.authorization.dao.UserMenuMapper;
import com.topie.ssocenter.freamwork.authorization.dao.UserRoleMapper;
import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.authorization.service.UserMenuService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
@Service("userMenuService")
public class UserMenuServiceImpl extends BaseService<UserMenu> implements UserMenuService {

	@Autowired
    UserMenuMapper menuMapper;
	@Autowired
    UserRoleMapper roleMapper;

	@Override
	public List<UserMenu> selectMenusByRoleCode(String code) {
		return menuMapper.selectMenusByRoleCode(code);
	}

	@Override
	public List<UserMenu> findMenusByUserId(String id) {
		return this.menuMapper.findMenusByUserId(id);
	}

	@Override
	public void deleteRecord(Long menuId) {
		roleMapper.deleteRoleMenu(null, menuId);
		this.menuMapper.deleteByPrimaryKey(menuId);
		
	}

}
