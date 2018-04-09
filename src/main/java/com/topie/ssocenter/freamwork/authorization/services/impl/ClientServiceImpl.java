package com.topie.ssocenter.freamwork.authorization.services.impl;

import com.topie.ssocenter.freamwork.authorization.services.ClinetService;


public class ClientServiceImpl implements ClinetService{

	@Override
	public String hello(String username) {
		return "hello! "+username;
	}

	@Override
	public String SynchronizedInfo(String opType, String infoCode) {
		System.out.println("opType:"+opType+"     infoCode:"+infoCode);
		switch (opType) {
		case "11":
			//TODO 新增用户  根据infoCode 通过webService 在单点接口上获取 用户信息保存导本系统
			//return addUser(infoCode);
			break;
		case "12":
			//TODO 更新用户
			break;
		case "13":
			//TODO 删除用户
			break;
		case "41":
			//TODO 新增机构  
			break;
		case "42":
			//TODO 更新机构  
			break;
		case "43":
			//TODO 删除机构  
			break;

		default:
			break;
		}
		
		return "000";
	}

}
