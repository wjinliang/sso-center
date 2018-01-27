package com.topie.ssocenter.freamwork.authorization.services;

public interface ClinetService {
	public abstract String hello(String username);

	String SynchronizedInfo(String opType, String infoCode);

}
