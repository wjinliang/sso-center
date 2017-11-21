package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 */
public interface UserAccountService extends IService<UserAccount,String> {


    UserAccount findUserAccountByLoginName(String loginName);

    int insertUserAccountRole(String userId, String roleId);

    List<String> findUserAccountRoleByUserAccountId(String userId);

    PageInfo<UserAccount> findUserAccountList(Integer pageNum, Integer pageSize, UserAccount user);

    int findExistUserAccount(UserAccount user);

	String selectMaxUserLoginNameByOrgCode(String code);
}
