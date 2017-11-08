package com.topie.ssocenter.freamwork.authorization.service;

public interface SynInfoService {
	public abstract String hello(String username);
	
	public abstract String getUserInfo(String userId);
	
	public abstract String getOrgInfo(String orgId);
	
	public abstract String getRoleInfo(String roleId);
	
	public abstract String getDivisionInfo(String divisionId);
}
