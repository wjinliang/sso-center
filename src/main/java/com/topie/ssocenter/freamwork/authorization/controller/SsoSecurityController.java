package com.topie.ssocenter.freamwork.authorization.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SimpleCrypto;

/**2019年11月29日
 */
@Controller
@RequestMapping("/security")
public class SsoSecurityController {
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private ApplicationInfoService applicationInfoService;
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
    	String enString = "";
    	try{
    		enString = SimpleCrypto.encrypt(seed, password);
    	}catch(Exception e){
    		e.printStackTrace();
    		return ResponseUtil.error("password error");	
    	}
    	if(!enString.equals(user.getSynpassword())){
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

    
}
