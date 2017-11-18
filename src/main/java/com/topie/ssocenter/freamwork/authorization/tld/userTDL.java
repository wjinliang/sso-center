package com.topie.ssocenter.freamwork.authorization.tld;

import com.topie.ssocenter.common.utils.AppUtil;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;

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

	private static UserAccount getUser(String userId) {
		UserAccountService userService = (UserAccountService)AppUtil.getBean("userService");
		UserAccount user = userService.selectByKey(userId);
		return user;
	}

}
