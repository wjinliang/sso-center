package com.topie.ssocenter.freamwork.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

@Controller
@RequestMapping("/syn")
public class SynIndexController {

	@Autowired
	private ApplicationInfoService appService;
	
	@RequestMapping("listSynApps")
	@ResponseBody
	public Object listSynApps(){
		OrangeSideSecurityUser u = SecurityUtils.getCurrentSecurityUser();
		PageInfo<ApplicationInfo> list = this.appService.selectCurrentUserSynApps();
		return list;
	}
}
