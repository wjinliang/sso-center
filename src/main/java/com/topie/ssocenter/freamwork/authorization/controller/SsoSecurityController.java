package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.DivisionSystem;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.DivisionSystemService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;

/**2019年11月29日
 */
@Controller
@RequestMapping("/security")
public class SsoSecurityController {
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private ApplicationInfoService applicationInfoService;
	@Autowired
	private DivisionSystemService divisionSystemService;
	@Autowired
	private DivisionService divisionService;
    @Value("${encrypt.seed}")
	private String seed;
    
    @RequestMapping(value = "/userPassword/getUserCode")
    public
    @ResponseBody
    Object getUserCode(HttpServletRequest request) {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String appkey = request.getHeader("appkey");
    	if(StringUtil.isEmpty(appkey)){
    		return ResponseUtil.error(401,"missing header[appkey]");
    	}
    	if(StringUtil.isEmpty(username)){
    		return ResponseUtil.error(400,"missing parameter[username]");
    	}
    	if(StringUtil.isEmpty(password)){
    		return ResponseUtil.error(400,"missing parameter[password]");
    	}
    	ApplicationInfo app = applicationInfoService.selectByKey(appkey);
    	if(app==null){
    		return ResponseUtil.error("header appkey error");
    	}
    	UserAccount user = userAccountService.findUserAccountByLoginName(username);
    	if(user==null||user.getIsDelete()){
    		return ResponseUtil.error("user not exist");
    	}
    	ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		String md5 = sha.encodePassword(password, null);
    	if(!md5.equals(user.getPassword())){
    		return ResponseUtil.error("password error");
    	}
    	if(StringUtils.isEmpty(user.getMergeUuid())){
    		JSONObject json = new JSONObject();
    		json.put("userCode", user.getCode());
    		json.put("userName",user.getName());
    		return ResponseUtil.success(json);
    	}
    	UserAccount appUser= userAccountService.selectUserByXtbs(user.getMergeUuid(), appkey);
    	if(appUser!=null){
    		JSONObject json = new JSONObject();
    		json.put("userCode", appUser.getCode());
    		json.put("userName",appUser.getName());
    		return ResponseUtil.success(json);
    	}
    	return ResponseUtil.error("user not exist");
    }
    
    

    @RequestMapping(value = "/user/getUserDivisionCode")
    public
    @ResponseBody
    Object getUserDivisionCode(HttpServletRequest request) {
    	String userCode = request.getParameter("userCode");
    	String appkey = request.getHeader("appkey");
    	if(StringUtil.isEmpty(appkey)){
    		return ResponseUtil.error(401,"missing header[appkey]");
    	}
    	if(StringUtil.isEmpty(userCode)){
    		return ResponseUtil.error(400,"missing parameter[userCode]");
    	}
    	ApplicationInfo app = applicationInfoService.selectByKey(appkey);
    	if(app==null){
    		return ResponseUtil.error("header appkey error");
    	}
    	UserAccount user = userAccountService.selectByKey(userCode);
    	if(user==null||user.getIsDelete()){
    		return ResponseUtil.error("user not exist");
    	}
    	Map<String, Object> division = getOrgDivisionCode(user.getOrgId(),appkey);
    	if(division==null){
    		return ResponseUtil.error("org not exist");
    	}
    	return ResponseUtil.success(division);
    }
    

    @RequestMapping(value = "/org/getOrgDivisionCode")
    public
    @ResponseBody
    Object getOrgDivisionCode(HttpServletRequest request) {
    	String orgId = request.getParameter("orgId");
    	String appkey = request.getHeader("appkey");
    	if(StringUtil.isEmpty(appkey)){
    		return ResponseUtil.error(401,"missing header[appkey]");
    	}
    	long orgID = 0L;
    	if(StringUtil.isEmpty(orgId)){
    		return ResponseUtil.error(400,"missing parameter[orgId]");
    	}
    	try{
    		orgID = Long.valueOf(orgId);
    	}catch(Exception e){
    		return ResponseUtil.error("org not exist");
    	}
    	ApplicationInfo app = applicationInfoService.selectByKey(appkey);
    	if(app==null){
    		return ResponseUtil.error("header appkey error");
    	}
    
    	Map<String, Object> division = getOrgDivisionCode(orgID,appkey);
    	if(division==null){
    		return ResponseUtil.error("org not exist");
    	}
    	return ResponseUtil.success(division);
    }



	private Map<String, Object> getOrgDivisionCode(long orgId, String appkey) {
		Org org = orgService.selectByKey(orgId);
		if(org==null)return null;
		String divisionId = org.getDivisionId();
		DivisionSystem divisionSystem = new DivisionSystem();
		divisionSystem.setDivisionId(divisionId);
		divisionSystem.setSystemId(appkey);
		List<DivisionSystem> list = divisionSystemService.findPage(1, 2, divisionSystem).getList();
		Division dv = divisionService.selectByKey(divisionId);
		Map<String, Object>  d = new HashMap<String, Object>();
		d.put("divisionName",dv.getName() );
		d.put("divisionFullname",dv.getFullname());
		d.put("id", dv.getId());
		d.put("divisionCode", dv.getCode());
		if(list.size()>0){
			String code = list.get(0).getNewDivisionCode();
			if(StringUtils.isNotEmpty(code)){
				d.put("divisionCode", code);
			}
		}
		return d;
		
		
	}

    
}
