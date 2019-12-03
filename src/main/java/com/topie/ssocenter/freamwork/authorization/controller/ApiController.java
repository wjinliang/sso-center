package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.SynDivision;
import com.topie.ssocenter.freamwork.authorization.model.SynOrg;
import com.topie.ssocenter.freamwork.authorization.model.SynUser;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.SynService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.service.UserRoleService;

@Controller
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private OrgService orgService;
	@Autowired
	private DivisionService divisionService;
	@Autowired
	private SynService synService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserAccountService userAccountService;
	
	@RequestMapping("/page")
	public Object page(){
		return "api/page";
	}
	
	@RequestMapping("addSynUser")
	@ResponseBody
	public Object addSynUser(String id,String appId){
		String today = getTime();
		SynUser synUser = new SynUser();
		String uuid = UUIDUtil.getUUID();
		synUser.setAppId(appId);
		synUser.setId(uuid);
		synUser.setUserId(id);
		synUser.setSynTime(today);
		this.synService.save(synUser);
		return ResponseUtil.success();
		
	}
	private String getTime() {
		return DmDateUtil.DateToStr(new Date(),"yyyy-MM-dd")+" 00:00:00";
	}
	@RequestMapping("addSynOrg")
	@ResponseBody
	public Object addSynOrg(String id,String appId){
		String today = getTime();
		SynOrg synOrg = new SynOrg();
		String uuid = UUIDUtil.getUUID();
		synOrg.setAppId(appId);
		synOrg.setId(uuid);
		synOrg.setOrgId(id);
		synOrg.setSynTime(today);
		this.synService.save(synOrg);
		return ResponseUtil.success();
		
	}
	@RequestMapping("addSynDivision")
	@ResponseBody
	public Object addSynDivision(String id,String appId){
		String today = getTime();
		SynDivision synDivision = new SynDivision();
		String uuid = UUIDUtil.getUUID();
		synDivision.setAppId(appId);
		synDivision.setId(uuid);
		synDivision.setDivisionId(id);
		synDivision.setSynTime(today);
		this.synService.save(synDivision);
		return ResponseUtil.success();
		
	}
	
	@RequestMapping("deleteSynUser")
	@ResponseBody
	public Object deleteSynUser(String id,String appId){
		synService.deleteSynUser(id, appId);
		return ResponseUtil.success();
		
	}
	@RequestMapping("deleteSynOrg")
	@ResponseBody
	public Object deleteSynOrg(String id,String appId){
		synService.deleteSynOrg(id, appId);
		return ResponseUtil.success();
		
	}
	@RequestMapping("deleteSynDivision")
	@ResponseBody
	public Object deleteSynDivision(String id,String appId){
		
		return ResponseUtil.success();
		
	}
	
	@RequestMapping("mvOrg")
	@ResponseBody
	public Object mvOrg(Long id,String appId){
		Org org = new Org();
		org.setId(id);
		org.setDivisionId(appId);
		Division d = divisionService.selectByKey(appId);
		org.setDivisionCode(d.getCode()+"");
		orgService.updateNotNull(org);
		return ResponseUtil.success();
		
	}
	@RequestMapping("mvDivision")
	@ResponseBody
	public Object mvDivision(String id,String appId){
		Division division = new Division();
		division.setId(id);
		division.setParentId(appId);
		divisionService.updateNotNull(division);
		return ResponseUtil.success();
	}
	
	@RequestMapping("addRole")
	@ResponseBody
	public Object addRole(String id,String appId){
		userRoleService.insertUserAccountRole(id, appId);
		return ResponseUtil.success();
	}
	@RequestMapping("deleteRole")
	@ResponseBody
	public Object deleteRole(String id,String appId){
		userRoleService.deleteUserAccountRole(id, appId);
		return ResponseUtil.success();
	}
	
	@RequestMapping("updateUserSystem")
	@ResponseBody
	public Object updateUserSystem(String id,String appId){
		long start = System.currentTimeMillis();
		userAccountService.updateUserSystem(id, appId);
		System.out.println(System.currentTimeMillis()-start);
		return ResponseUtil.success();
	}
	
	@RequestMapping("updateUserOrg")
	@ResponseBody
	public Object updateUserOrg(String id,String appId){
		long start = System.currentTimeMillis();
		userAccountService.updateUserOrg(id, appId);
		System.out.println(System.currentTimeMillis()-start);
		return ResponseUtil.success();
	}
	@RequestMapping("/updateDivision")
	@ResponseBody
	public Object updateDivision(
			String id,Integer appId) {
		Division d = new Division();
		d.setId(id);
		d.setIsdelete(appId);
		this.divisionService.updateNotNull(d);
		return ResponseUtil.success();
	}
	
}
