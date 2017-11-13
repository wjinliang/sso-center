package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.dao.UserAccountMapper;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/2 说明：
 */
@Service("userService")
public class UserAccountServiceImpl extends BaseService<UserAccount>
        implements UserAccountService {
    @Autowired
    UserAccountMapper userMapper;

    @Override
    public int insertUserAccount(UserAccount user) {
        user.setPassword(SecurityUtils.encodeString(user.getPassword()));
        return getMapper().insert(user);
    }

    @Override
    public int updateUserAccount(UserAccount user) {
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(SecurityUtils.encodeString(user.getPassword()));
        }
        return getMapper().updateByPrimaryKey(user);
    }

    @Override
    public UserAccount findUserAccountById(String id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public UserAccount findUserAccountByLoginName(String loginName) {
        return userMapper.findUserByLoginName(loginName);
    }

    @Override
    public int deleteUserAccount(String id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int insertUserAccountRole(String userId, String roleId) {
        return userMapper.insertUserRole(userId, roleId);
    }

    @Override
    public List<String> findUserAccountRoleByUserAccountId(String userId) {
        return userMapper.findUserRoleByUserId(userId);
    }

    @Override
    public PageInfo<UserAccount> findUserAccountList(Integer pageNum, Integer pageSize, UserAccount user) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserAccount> list = userMapper.findUserList(user);
        PageInfo<UserAccount> page = new PageInfo<UserAccount>(list);
        return page;
    }

    @Override
    public int findExistUserAccount(UserAccount user) {
        return userMapper.findExistUser(user);
    }

	@Override
	public String selectMaxUserLoginNameByOrgCode(String code) {
		return  userMapper.selectMaxUserLoginNameByOrgCode(code);
	}
}
