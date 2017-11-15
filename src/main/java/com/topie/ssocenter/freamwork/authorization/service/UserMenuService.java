package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;

import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
public interface UserMenuService extends IService<UserMenu> {
	
	List<UserMenu> selectMenusByRoleCode(String code);

	List<UserMenu> findMenusByUserId(String id);

	void deleteRecord(Long menuId);

	void seqList(Long currentid, Long targetid, String moveType,
			String moveMode);
}
