package com.topie.ssocenter.freamwork.authorization.services.impl;

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
			return  JSON.toJSONString(user);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getOrgInfo(String orgId) {
		try{
			Long oid = Long.valueOf(orgId);
			Org org  = this.orgService.selectByKey(oid);
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
