package com.topie.ssocenter.common.utils;

import java.util.HashMap;

public class KV extends HashMap<String, Object>{
	
	public KV(String key,Object val){
		this.put(key, val);
	}
	
	public KV add(String key,Object val){
		this.put(key, val);
		return this;
	}
	public KV append(String key,Object val){
		this.add(key, val);
		return this;
	}
	public KV remove(String key){
		this.remove(key);
		return this;
	}
}
