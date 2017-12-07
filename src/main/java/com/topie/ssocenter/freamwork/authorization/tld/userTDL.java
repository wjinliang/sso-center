package com.topie.ssocenter.freamwork.authorization.tld;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.AppUtil;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

public class userTDL {

	public static String getRemoteIpAddr(String userId) {
		if (userId != null && !userId.equals("")) {
			UserAccount u = getUser(userId);
			if (u == null)
				return "-";
			return u.getRemoteipaddr();
		} else {
			return "-";
		}
	}

	public static String getLastLoginTime(String userId) {
		if (userId != null && !userId.equals("")) {
			UserAccount u = getUser(userId);
			if (u == null)
				return "-";
			return u.getLastlogintime();
		} else {
			return "-";
		}
	}

	public static String getCurretUserDivisionId() {
		try {
			Org o = getCurrentUserOrg();
			return o.getDivisionId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-";
	}

	private static Org getCurrentUserOrg() {
		Long orgId = SecurityUtils.getCurrentSecurityUser().getOrgId();
		OrgService orgService = (OrgService) AppUtil
				.getBean("orgServiceImpl");
		Org o = orgService.selectByKey(orgId);
		return o;
	}

	public static String getCurretUserName() {
		return SecurityUtils.getCurrentUserName();
	}

	public static String getCurrentUserOrgName() {
		try {
			Org o = getCurrentUserOrg();
			return o.getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-";
	}

	public static List<ApplicationInfo> getCurrentUserApps(String type) {
		ApplicationInfoService appService = (ApplicationInfoService) AppUtil
				.getBean("applicationInfoServiceImpl");
		PageInfo<ApplicationInfo> page = appService.selectCurrentUserSynApps();
		List<ApplicationInfo> list = page.getList();
		List<ApplicationInfo> result = new ArrayList<ApplicationInfo>();
		if(type!=null && type.equals("org")){
			for(ApplicationInfo a:list){
				if(a.getIsOrgSyn()){
					result.add(a);
				}
			}
		}else
		if(type!=null && type.equals("user")){
			for(ApplicationInfo a:list){
				if(a.getIsUserSyn()){
					result.add(a);
				}
			}
		}
		else{
				result = list;
			}
/*		Division userDivision = getCurrentUserDivision();
		int currentUserLevel = userDivision.getLevel();
		int userLevel = getDivision(divisionId).getLevel();
		for (ApplicationInfo a : list) {
			boolean hidden = false;
			int op = Integer.valueOf(a.getOpLevel());
			int user = Integer.parseInt(a.getUserLevel());
			if (userLevel > user) {
				hidden = true;
			}
			if (currentUserLevel > op) {
				hidden = true;
			}
		}
*/		return result;
	}

	private static Division getCurrentUserDivision() {
		Org o = getCurrentUserOrg();
		String divisionId = o.getDivisionId();
		DivisionService ds =  (DivisionService) AppUtil
				.getBean("divisionServiceImpl");
		return ds.selectByKey(divisionId);
	}
	private static Division getDivision(String divisionId) {
		DivisionService ds =  (DivisionService) AppUtil
				.getBean("divisionServiceImpl");
		return ds.selectByKey(divisionId);
	}

	private static UserAccount getUser(String userId) {
		UserAccountService userService = (UserAccountService) AppUtil
				.getBean("userService");
		UserAccount user = userService.selectByKey(userId);
		return user;
	}

}
