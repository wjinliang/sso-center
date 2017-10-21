package com.topie.ssocenter.common.utils;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
	public static void main(String[] args) {
		String uuid = UUIDUtil.getUUID();
		System.out.println(uuid);
	}
}
