package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
public interface UserMenuService extends IService<UserMenu> {
    int insertUserMenu(UserMenu function);

    int updateUserMenu(UserMenu function);

    UserMenu findUserMenuById(Long id);

    int deleteUserMenuById(Long id);

	List<UserMenu> selectMenusByRoleCode(String code);

	PageInfo<UserMenu> findUserMenuList(int pageNum, int pageSize, UserMenu menu);

	List<UserMenu> findMenusByUserId(String id);
}
