package com.topie.ssocenter.freamwork.authorization.service.impl;

import org.springframework.stereotype.Service;

import com.topie.ssocenter.freamwork.authorization.service.SynInfoService;

@Service("synInfoServiceImpl")
public class SynInfoServiceImp implements SynInfoService{

	@Override
	public String hello(String username) {
		// TODO Auto-generated method stub
		return "hello"+username;
	}

	@Override
	public String getUserInfo(String userId) {
		// TODO Auto-generated method stub
		return "{s:w}";
	}

	@Override
	public String getOrgInfo(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRoleInfo(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivisionInfo(String divisionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
