package com.topie.ssocenter.freamwork.authorization.tld;

import com.topie.ssocenter.common.utils.AppUtil;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

public class userTDL {
	
  public static String getRemoteIpAddr(String userId) {
		if (userId != null && !userId.equals("")) {
			UserAccount u = getUser(userId);
			if(u==null) return "-";
			return u.getRemoteipaddr();
		} else {
			return "-";
		}
	}

	public static String getLastLoginTime(String userId) {
		if (userId != null && !userId.equals("")) {
			UserAccount u = getUser(userId);
			if(u==null) return "-";
			return u.getLastlogintime();
		} else {
			return "-";
		}
	}
	
	public static String getCurretUserDivisionId() {
		try{
			Long orgId = SecurityUtils.getCurrentSecurityUser().getOrgId();
			OrgService orgService = (OrgService)AppUtil.getBean("orgServiceImpl");
			Org o = orgService.selectByKey(orgId);
			return o.getDivisionId();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "-";
	}
	public static String getCurretUserName() {
		return SecurityUtils.getCurrentUserName();
	}
	public static String getCurrentUserOrgName() {
		try{
			Long orgId = SecurityUtils.getCurrentSecurityUser().getOrgId();
			OrgService orgService = (OrgService)AppUtil.getBean("orgServiceImpl");
			Org o = orgService.selectByKey(orgId);
			return o.getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "-";
	}

	private static UserAccount getUser(String userId) {
		UserAccountService userService = (UserAccountService)AppUtil.getBean("userService");
		UserAccount user = userService.selectByKey(userId);
		return user;
	}

}
