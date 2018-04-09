package com.topie.ssocenter.freamwork.authorization.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.services.SynInfoService;

public class SynInfoServiceImpl implements SynInfoService{
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private DivisionService divisionService;
	@Override
	public String hello(String username) {
		// TODO Auto-generated method stub
		return "hello"+username;
	}

	@Override
	public String getUserInfo(String userId) {
		try{
			UserAccount user  = this.userAccountService.selectByKey(userId);
			Map map = new HashMap();
			map.put("code", user.getCode());
			map.put("birthday", user.getBirthday());
			map.put("userType", user.getUserType());
			map.put("orgId", user.getOrgId());
			map.put("loginname", user.getLoginname());
			map.put("username", user.getName());
			map.put("name", user.getName());
			map.put("title", user.getTitle());
			map.put("faxno", user.getFaxNo());
			map.put("code", user.getCode());
			
			map.put("schoolAge", user.getSchoolAge());
			map.put("duty", user.getDuty());
			map.put("delete", user.getIsDelete());
			map.put("gender", user.getGender());
			map.put("createDate", user.getCreateDate());
			map.put("lastLoginTime", user.getLastlogintime());
			map.put("synPassword", user.getSynpassword());
			map.put("speciality", user.getSpeciality());
			map.put("originId", user.getOriginId());
			map.put("loginCount", user.getLogincount());
			
			map.put("createUser", user.getCreateUser());
			map.put("bizPhoneNo", user.getBizphoneNo());
			map.put("address", user.getAddress());
			map.put("email", user.getEmail());
			map.put("mobile", user.getMobile());
			return  JSON.toJSONString(map);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getOrgInfo(Long orgId) {
		try{
			Org org  = this.orgService.selectByKey(orgId);
			return JSON.toJSONString(org);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getRoleInfo(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivisionInfo(String divisionId) {
		try{
			Division divison  = this.divisionService.selectByKey(divisionId);
			return  JSON.toJSONString(divison);
		}catch(Exception e){
			return null;
		}
	}

}
