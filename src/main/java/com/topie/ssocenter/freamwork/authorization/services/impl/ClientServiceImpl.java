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
		return "000";
	}

}
