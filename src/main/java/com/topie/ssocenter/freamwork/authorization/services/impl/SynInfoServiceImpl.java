package com.topie.ssocenter.freamwork.authorization.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.DivisionSystem;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.DivisionSystemService;
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
	@Autowired
	private DivisionSystemService divisionSystemService;
	@Override
	public String hello(String username) {
		return "hello"+username;
	}

	@Override
	public String getUserInfo(String userId,String appkey) {
		try{
			UserAccount user  = this.userAccountService.selectByKey(userId);
			Map map = new HashMap();
			map.put("code", user.getCode());
			map.put("birthday", user.getBirthday());
			map.put("userType", user.getUserType());
			map.put("orgId", user.getOrgId());
			map.put("loginname", user.getLoginname());
			map.put("username", user.getName());
			map.put("name", user.getName());
			map.put("title", user.getTitle());
			map.put("faxno", user.getFaxNo());
			map.put("code", user.getCode());
			
			map.put("schoolAge", user.getSchoolAge());
			map.put("duty", user.getDuty());
			map.put("delete", user.getIsDelete());
			map.put("gender", user.getGender());
			map.put("createDate", user.getCreateDate()==null?"":user.getCreateDate());
			map.put("lastLoginTime", user.getLastlogintime());
			map.put("synPassword", user.getSynpassword());
			map.put("speciality", user.getSpeciality());
			map.put("originId", user.getOriginId());
			map.put("loginCount", user.getLogincount()==null?1:user.getLogincount());
			
			map.put("seq", user.getSeq()==null?1:user.getSeq());
			map.put("createUser", user.getCreateUser()==null?"":user.getCreateUser());
			map.put("bizPhoneNo", user.getBizphoneNo()==null?"":user.getBizphoneNo());
			map.put("address", user.getAddress()==null?"":user.getAddress());
			map.put("email", user.getEmail()==null?"":user.getEmail());
			map.put("enabled", user.getEnabled());
			map.put("mobile", user.getMobile()==null?"":user.getMobile());
			//return  JSON.toJSONString(map,filter);
			JsonConfig fillter = new JsonConfig();
			JSONObject json = JSONObject.fromObject(map,fillter);
			System.out.println("jsonStr:"+json.toString());
			return  json.toString();
		}catch(Exception e){
			System.err.println("接口获取用户异常,userId:"+userId);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getOrgInfo(Long orgId,String appkey) {
		try{
			Org org  = this.orgService.selectByKey(orgId);
			Map map = new HashMap();
			map.put("code", org.getCode());
			map.put("createDate", org.getCreateDate()==null?"":org.getCreateDate());
			map.put("createUser", org.getCreateUser());
			map.put("divisionCode", org.getDivisionCode());
			map.put("divisionId", org.getDivisionId());
			map.put("faxno", org.getFaxNo());
			map.put("id", org.getId());
			map.put("leadName", org.getLeadName()==null?"": org.getLeadName());
			map.put("linkman", org.getLinkMan()==null?"":org.getLinkMan());
			map.put("name", org.getName());
			map.put("orgType", org.getOrgType()==null?"":org.getOrgType());
			map.put("originId", org.getOriginId());
			map.put("parentId", org.getParentId());
			map.put("phoneno", org.getPhoneNo());
			map.put("postalAddress", org.getPostalAddress());
			map.put("postalCode", org.getPostalCode());
			map.put("seq", org.getSeq()==null?1:org.getSeq());
			map.put("systemId", org.getSystemId());
			map.put("type", org.getType());
			JsonConfig fillter = new JsonConfig();
			setDivisionCode(org.getDivisionId(),appkey,map);
			JSONObject json = JSONObject.fromObject(map,fillter);
			System.out.println("jsonStr:"+json.toString());
			return  json.toString();
			//return JSON.toJSONString(org,filter);
		}catch(Exception e){
			System.err.println("接口获取机构异常，orgId:"+orgId);
			e.printStackTrace();
			return null;
		}
	}

	private void setDivisionCode(String divisionId, String appkey, Map map) {
		if(StringUtils.isEmpty(appkey)){return;}
		DivisionSystem divisionSystem = new DivisionSystem();
		divisionSystem.setDivisionId(divisionId);
		divisionSystem.setSystemId(appkey);
		List<DivisionSystem> list = divisionSystemService.findPage(1, 2, divisionSystem).getList();
		if(list.size()>0){
			String code = list.get(0).getNewDivisionCode();
			if(StringUtils.isNotEmpty(code)){
				map.put("divisionCode",code);
			}
		}
		
	}

	@Override
	public String getRoleInfo(String roleId,String appkey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivisionInfo(String divisionId,String appkey) {
		try{
			Division divison  = this.divisionService.selectByKey(divisionId);
			//return  JSON.toJSONString(divison,filter);
			JSONObject json = JSONObject.fromObject(divison);
			return json.toString();
		}catch(Exception e){
			System.err.println("接口获取区划异常，divisionId："+divisionId);
			e.printStackTrace();
			return null;
		}
	}
	
	private static ValueFilter filter = new ValueFilter() {
	    @Override
	    public Object process(Object obj, String s, Object v) {
	        if (v == null)
	            return "";
	        return v;
	    }
	};

}
