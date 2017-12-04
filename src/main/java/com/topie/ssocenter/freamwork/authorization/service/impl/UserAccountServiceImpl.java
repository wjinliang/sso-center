package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.dao.UserAccountMapper;
import com.topie.ssocenter.freamwork.authorization.dao.UserRoleMapper;
import com.topie.ssocenter.freamwork.authorization.exception.RuntimeBusinessException;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SimpleCrypto;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/2 说明：
 */
@Service("userService")
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount,String>
        implements UserAccountService {
    @Autowired
    UserAccountMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public UserAccount findUserAccountByLoginName(String loginName) {
        return userMapper.findUserByLoginName(loginName);
    }

    @Override
    public int insertUserAccountRole(String userId, String roleId) {
        return userRoleMapper.insertUserAccountRole(userId, roleId);
    }

    @Override
    public List<String> findUserAccountRoleByUserAccountId(String userId) {
        return userRoleMapper.selectRolesByUserId(userId);
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

	@Override
	public Mapper<UserAccount> getMapper() {
		
		return super.getMapper();
	}

	@Override
	public UserAccount selectByKey(String key) {
		
		return super.selectByKey(key);
	}

	@Override
	public int save(UserAccount entity) {
		
		return super.save(entity);
	}

	@Override
	public int delete(String key) {
		
		return super.delete(key);
	}

	@Override
	public int updateAll(UserAccount entity) {
		
		return super.updateAll(entity);
	}

	@Override
	public int updateNotNull(UserAccount entity) {
		
		return super.updateNotNull(entity);
	}

	@Override
	public List<UserAccount> selectByExample(Example example) {
		
		return super.selectByExample(example);
	}

	@Override
	public List<UserAccount> selectAll() {
		
		return super.selectAll();
	}

	@Override
	public void updatePassword(String userId, String oldPassword,
			String enPassword) {
		String dePassword ="";
		try {
			dePassword = SimpleCrypto.decrypt("zcpt@123456",
					enPassword);
		} catch (Exception e) {
			throw new RuntimeBusinessException("修改密码时解码错误");
		}
		UserAccount record = getMapper().selectByPrimaryKey(userId);
		if(!record.getPassword().equals(oldPassword)){
			throw new RuntimeBusinessException("原密码输入错误");
		}
		UserAccount user = new UserAccount();
		user.setCode(userId);
		user.setSynpassword(enPassword);
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		user.setPassword(sha.encodePassword(dePassword, null));
		getMapper().updateByPrimaryKeySelective(user);
	}

	
	
}
