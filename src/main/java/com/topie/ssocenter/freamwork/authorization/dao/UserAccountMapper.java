package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.UserAccount;

public interface UserAccountMapper extends Mapper<UserAccount> {
	UserAccount findUserByLoginName(String loginName);

    List<UserAccount> findUserList(UserAccount user);

    int findExistUser(UserAccount user);

	String selectMaxUserLoginNameByOrgCode(String code);
	
	long selectSynCount(String appId);
}