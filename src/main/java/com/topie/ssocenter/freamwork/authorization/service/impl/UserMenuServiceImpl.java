package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topie.ssocenter.freamwork.authorization.dao.UserMenuMapper;
import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.authorization.service.UserMenuService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
@Service("functionService")
public class UserMenuServiceImpl extends BaseService<UserMenu> implements UserMenuService {

	@Autowired
    UserMenuMapper menuMapper;
    @Override
    public int insertUserMenu(UserMenu function) {
        return getMapper().insert(function);
    }

    @Override
    public int updateUserMenu(UserMenu function) {
        return getMapper().updateByPrimaryKey(function);
    }

    @Override
    public UserMenu findFuntionById(Long id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public int deleteUserMenuById(Long id) {
        return getMapper().deleteByPrimaryKey(id);
    }

	@Override
	public List<UserMenu> selectMenusByRoleCode(String code) {
		return menuMapper.selectMenusByRoleCode(code);
	}
}
