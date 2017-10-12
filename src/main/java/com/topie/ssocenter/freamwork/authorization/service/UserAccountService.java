package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/2 说明：
 */
public interface UserAccountService extends IService<UserAccount> {

    int insertUserAccount(UserAccount user);

    int updateUserAccount(UserAccount user);

    UserAccount findUserAccountById(String id);

    UserAccount findUserAccountByLoginName(String loginName);

    int deleteUserAccount(String id);

    int insertUserAccountRole(String userId, String roleId);

    List<String> findUserAccountRoleByUserAccountId(String userId);

    PageInfo<UserAccount> findUserAccountList(Integer pageNum, Integer pageSize, UserAccount user);

    int findExistUserAccount(UserAccount user);
}
