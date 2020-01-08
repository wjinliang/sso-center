package com.topie.ssocenter.freamwork.authorization.services;

public interface SynInfoService {
	public abstract String hello(String username);
	
	public abstract String getUserInfo(String userId,String appkey);
	
	public abstract String getOrgInfo(Long orgId,String appkey);
	
	public abstract String getRoleInfo(String roleId,String appkey);
	
	public abstract String getDivisionInfo(String divisionId,String appkey);
}
