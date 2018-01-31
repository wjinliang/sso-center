package com.topie.ssocenter.freamwork.authorization.tld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.AppUtil;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Notice;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.SynOrg;
import com.topie.ssocenter.freamwork.authorization.model.SynUser;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.NoticeService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.SynService;
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
		Org o = getOrg(orgId);
		return o;
	}
	private static Org getOrg(Long orgId) {
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
	/**
	 * 获取当前用户可以登录的权限Apps, 没有 权限限制
	 * @return
	 */
	public static List<ApplicationInfo> getCurrentUserApps() {
		ApplicationInfoService appService = (ApplicationInfoService) AppUtil
				.getBean("applicationInfoServiceImpl");
		PageInfo<ApplicationInfo> page = appService.selectCurrentUserSynApps();
		return page.getList();
	}
	/**
	 * 获取当前用户可以同步得权限Apps,  权限限制（操作等级，用户等级，当前操作的用户权限，当前被操作的用户权限）
	 * @param type
	 * @param orgId
	 * @param userId
	 * @return
	 */
	public static List<Map> getCurrentUserApps(String type,Long orgId ,String userId) {
		ApplicationInfoService appService = (ApplicationInfoService) AppUtil
				.getBean("applicationInfoServiceImpl");
		PageInfo<ApplicationInfo> page = appService.selectCurrentUserSynApps();
		List<ApplicationInfo> list = page.getList();
		List<Map> result = new ArrayList<Map>();
		if(type!=null && type.equals("org")){
			List<SynOrg> syns = null;
			if(orgId!=null){
				SynService synService = (SynService) AppUtil
						.getBean("synServiceImpl");
				syns = synService.selectOrgSynInfo(orgId);
			}
			for(ApplicationInfo a:list){
				if(a.getIsOrgSyn()){
					Map m = getMap(a);
					m.put("checked", false);
					if(syns!=null){
						for(SynOrg s:syns){
							if(s.getAppId().equals(a.getId())){
								m.put("checked", true);
							}
						}
					}
					result.add(m);
				}
			}
		}else
		if(type!=null && type.equals("user")){
			List<SynUser> syns = null;
			int userLevel = 10;
			if(orgId!=null){
				try{
				SynService synService = (SynService) AppUtil
						.getBean("synServiceImpl");
				if(StringUtils.isNotEmpty(userId)){
					syns = synService.selectUserSynInfo(userId);
				}
				userLevel = getDivision(getOrg(orgId).getDivisionId()).getLevel();
				}catch(Exception e){}
			}
			if(userLevel==10){//如果
				return result;
			}
			Division userDivision = getCurrentUserDivision();
			int currentUserLevel = userDivision.getLevel();
			for(ApplicationInfo a:list){
				if(a.getIsUserSyn()){
					Map m = getMap(a);
					m.put("checked", false);
					if(syns!=null){
						for(SynUser s:syns){
							if(s.getAppId().equals(a.getId())){
								m.put("checked", true);
							}
						}
					}
					boolean hidden = false;
					int op = Integer.valueOf(a.getOpLevel());
					int user = Integer.parseInt(a.getUserLevel());
					if (userLevel > user) {//被操作的用户等级  到达app设置等级（非）
						hidden = true;
					}
					if (currentUserLevel > op) {//当前用户权限 拥有操作权限（非）
						hidden = true;
					}
					if(!hidden){
						result.add(m);
					}
				}
			}
		}
		else{
				//result = list;
			}
		return result;
	}

	private static Map getMap(ApplicationInfo a) {
		Map map = new HashMap();
		map.put("id", a.getId());
		map.put("appCode", a.getAppCode());
		map.put("appName", a.getAppName());
		map.put("status", a.getStatus());
		return map;
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
	
	public static PageInfo<Notice> getNews(Integer pageNum,Integer pageSize) {
		NoticeService noticeService = (NoticeService) AppUtil
				.getBean("noticeServiceImpl");
		PageInfo<Notice> page = noticeService.findCurrentUserNotice(pageNum, pageSize);
		return page;
	}

}
