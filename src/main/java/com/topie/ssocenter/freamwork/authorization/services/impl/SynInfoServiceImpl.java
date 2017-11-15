package com.topie.ssocenter.freamwork.authorization.services.impl;

import com.topie.ssocenter.freamwork.authorization.services.SynInfoService;


public class SynInfoServiceImpl implements SynInfoService{

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
