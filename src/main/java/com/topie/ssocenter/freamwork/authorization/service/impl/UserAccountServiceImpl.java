package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.dao.UserAccountMapper;
import com.topie.ssocenter.freamwork.authorization.dao.UserRoleMapper;
import com.topie.ssocenter.freamwork.authorization.exception.RuntimeBusinessException;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.SynLog;
import com.topie.ssocenter.freamwork.authorization.model.SynUser;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.SynService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.authorization.utils.SimpleCrypto;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/2 说明：
 */
@Service("userService")
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount,String>
        implements UserAccountService {
	public static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);
    @Autowired
    UserAccountMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    SynService synService;
    @Autowired
    ApplicationInfoService appService;
    @Value("${encrypt.seed}")
	private String seed;

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
			dePassword = SimpleCrypto.decrypt(seed,
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
		//更新密码后同步到系统  //目前仅限追溯
		List<SynUser> listInfo = synService.selectUserSynInfo(user.getCode());//已经同步过的APP
		String appId="402881e854e6aca40154e6ca00f50006";//追溯
		for(SynUser synUser:listInfo){
			if(synUser.getAppId().equals(appId)){
				Map u = synOneUser(user,appId,"12","更新密码");
				
			}
		}
			
		
	}

	@Override
	public PageInfo<UserAccount> listNotMergeUsers(Integer pageNum,
			Integer pageSize, UserAccount user) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(UserAccount.class);
		Criteria c = example.createCriteria();
		c.andNotEqualTo("code", user.getCode());
		c.andEqualTo("isDelete",false);
		c.andIsNull("mergeUuid");
		if(!StringUtils.isEmpty(user.getName())){
			c.andEqualTo("name", user.getName());
		}
		if(!StringUtils.isEmpty(user.getLoginname())){
			c.andEqualTo("loginname", user.getLoginname());
		}
		List<UserAccount> list = this.selectByExample(example);
		return new PageInfo<UserAccount>(list);
	}

	@Override
	public PageInfo<UserAccount> listMergeUsers(UserAccount user, Integer pageNum,
			Integer pageSize) {
		UserAccount u = this.userMapper.selectByPrimaryKey(user.getCode());
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(UserAccount.class);
		Criteria c = example.createCriteria();
		c.andNotEqualTo("code", user.getCode());
		c.andEqualTo("isDelete",false);
		if(!StringUtils.isEmpty(u.getMergeUuid())){
			c.andEqualTo("mergeUuid", u.getMergeUuid());
		}else{//如果当前用户没有mergeId
			return ResponseUtil.emptyPage();
		}
		if(!StringUtils.isEmpty(user.getName())){
			c.andEqualTo("name", user.getName());
		}
		if(!StringUtils.isEmpty(user.getLoginname())){
			c.andEqualTo("loginname", user.getLoginname());
		}
		List<UserAccount> list = this.selectByExample(example);
		return new PageInfo<UserAccount>(list);
	}

	@Override
	public PageInfo<UserAccount> findSynUserByAppId(Integer thispage,
			Integer pagesize, UserAccount user, String appId) {
		PageHelper.startPage(thispage, pagesize);
		List<UserAccount> list = userMapper.selectSynUserByAppId(user,appId);
		return new PageInfo<UserAccount>(list);
	}

	@Override
	public PageInfo<UserAccount> findToSynUserByAppId(Integer thispage,
			Integer pagesize, UserAccount user, String appId) {
		PageHelper.startPage(thispage, pagesize);
		List<UserAccount> list = userMapper.selectToSynUserByAppId(user,appId);
		return new PageInfo<UserAccount>(list);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Map synOneUser(UserAccount user, String appId,String type,String typeName) {
		ApplicationInfo app = appService.selectByKey(appId);
		Map u = new HashMap();
		u.put("opType", typeName);
		u.put("opTypeCode", type);
		if(app==null){
			String s = "同步"+typeName+"User时 {appId="+appId+"}未找到对应的应用";
			logger.info(s);
			u.put("result", s);
			u.put("status", false);
			return u;
		}
		u.put("appName", app.getAppName());
		u.put("appCode", app.getAppCode());
		u.put("appId", app.getId());
		u.put("status", true);
		logger.info("开始同步【"+app.getAppName()+"】->["+user.getLoginname()+"]"+typeName);
		if(app.getStatus().equals("2")){
			u.put("result", app.getAppName()+"-系统维护中，暂无操作");
			logger.info(app.getAppName()+"=系统维护中，暂无操作");
			u.put("isAuthorize",false);
			return u;
		}
		String today = DmDateUtil.Current();
		String result="000";
		if(app.getIsUserSyn()){
			result = this.synService.synStart(appId, user.getCode()
					, type);
			if (result != null && result.equals("000")) {
				result = "同步成功";
				if(type.equals("11")){//新增
					SynUser synUser = new SynUser();
					String uuid = UUIDUtil.getUUID();
					synUser.setAppId(appId);
					synUser.setId(uuid);
					synUser.setUserId(user.getCode());
					synUser.setSynTime(today);
					this.synService.save(synUser);
					u.put("isAuthorize",app.getIsUserAuthorize());
				}
				if(type.equals("12")){//更新
					//nothing todo
				}
				if(type.equals("13")){//删除
					this.synService.deleteSynUser(user.getCode(),appId);
				}
			}else{
				u.put("status", false);
			}
		}else{//不同不到该系统
			result = "该系统设置为不同步用户";
		}
		u.put("result", result);
		/*
		 * 添加记录日志的操作
		 */
		SynLog synLog = new SynLog();
		synLog.setId(UUIDUtil.getUUID());
		synLog.setAppId(appId);
		synLog.setAppName(app.getAppName());
		synLog.setSynTime(today);
		String msg = "用户(" + user.getName()+"["+user.getLoginname()+"]" + ")"+typeName+"操作："
				+ result;
		logger.info(msg);
		synLog.setSynResult(msg);
		OrangeSideSecurityUser currentUser = SecurityUtils.getCurrentSecurityUser();
		synLog.setSynUserid(currentUser.getId());
		synLog.setSynUsername(currentUser.getDisplayName());
		this.synService.save(synLog);
		logger.info("====结束同步【"+app.getAppName()+"】->["+user.getLoginname()+"]"+typeName);
		return u;
		
	}

	@Override
	public void updateUserSystem(String id, String startTime) {
		Integer pageNum=1;
		Integer pageSize =10000;
		boolean f = true;
		do{
			PageHelper.startPage(pageNum, pageSize);
			List<UserAccount> list = userMapper.selectUserSystem(id,startTime);
			PageInfo<UserAccount> page = new PageInfo<UserAccount>(list);
			updateSystemId(page.getList());
			f = page.isHasNextPage();
			pageNum++;
		}while(f);
	}

	private void updateSystemId(List<UserAccount> list) {
		for(UserAccount user:list){
			userMapper.updateSystemIdByPrimaryKey(user);
		}
		
	}

	@Override
	public UserAccount selectUserByXtbs(String mergeUuid,String appId) {
		UserAccount user = new UserAccount();
		user.setMergeUuid(mergeUuid);
		List<UserAccount> list = userMapper.selectSynUserByAppId(user,appId);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public void updateUserOrg(String id, String orgId) {
		UserAccount user = new UserAccount();
		user.setCode(id);
		user.setOrgId(Long.valueOf(orgId));
		userMapper.updateByPrimaryKeySelective(user);
		
	}
	
	
}
