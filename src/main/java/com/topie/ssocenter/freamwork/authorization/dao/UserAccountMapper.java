package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.UserAccount;

public interface UserAccountMapper extends Mapper<UserAccount> {
	UserAccount findUserByLoginName(String loginName);

    int insertUserRole(String userId, String roleId);

    List<String> findUserRoleByUserId(String userId);

    List<UserAccount> findUserList(UserAccount user);

    int findExistUser(UserAccount user);

	String selectMaxUserLoginNameByOrgCode(String code);
}