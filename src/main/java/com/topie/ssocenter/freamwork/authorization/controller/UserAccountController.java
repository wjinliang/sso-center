package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.R;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

/**
 * Created by wjl
 */
@Controller
@RequestMapping("useraccount")
public class UserAccountController {
	@Autowired
	private UserAccountService userService;
	@Autowired
	private SessionRegistry sessionRegistry;

	@RequestMapping("/listActiveUsers")
	public ModelAndView listActiveUsers(ModelAndView model, UserAccount user) {
		List<Map<String, String>> list = SecurityUtils
				.listActiveUsers(sessionRegistry);
		model.addObject(R.PAGE, ResponseUtil.listToPage(list));
		model.setViewName("user/activeUsers");
		return model;
	}

	@RequestMapping("/kickUser")
	@ResponseBody
	public Object kickUser(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "ids", required = false) String sessionIds) {
		if(StringUtils.isNotEmpty(sessionIds)){
			String[] ids = sessionIds.split(",");
			for(String sessionId:ids){
				SecurityUtils.kickUser(sessionRegistry, sessionId);
			}
		}
		return ResponseUtil.success();

	}

}
