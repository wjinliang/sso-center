package com.topie.ssocenter.freamwork.authorization.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideCaptchaAuthenticationDetails;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;

/**
 * Created by cgj on 2015/10/26.
 */
public class SecurityUtils {
    public static String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof OrangeSideSecurityUser)
            userName = ((OrangeSideSecurityUser) principal).getDisplayName();
        return userName;
    }

    public static OrangeSideSecurityUser getCurrentSecurityUser() {
    	try{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof OrangeSideSecurityUser)
            return (OrangeSideSecurityUser) principal;
    	}catch(Exception ex){
    		//ex.printStackTrace();
    	}
        return null;
    }
    
    public static String getCurrentIP() {
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	 OrangeSideCaptchaAuthenticationDetails wauth = (OrangeSideCaptchaAuthenticationDetails) auth.getDetails();
         String ip = wauth.getIp();
         return ip;
    }

    public static String encodeString(String character) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(character);
    }

    public static boolean matchString(String character, String encodedCharacter) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(character, encodedCharacter);
    }
    
    public static List<Map<String, String>> listActiveUsers(
			SessionRegistry sessionRegistry) {
		Map<Object, Date> lastActivityDates = new HashMap<Object, Date>();
		List<Map<String, String>> mlist = new ArrayList<Map<String, String>>();
		for (Object principal : sessionRegistry.getAllPrincipals()) {
			OrangeSideSecurityUser user = (OrangeSideSecurityUser) principal;
			for (SessionInformation session : sessionRegistry.getAllSessions(
					principal, false)) {
				if (lastActivityDates.get(principal) == null) {
					lastActivityDates.put(user.getId(),
							session.getLastRequest());
					Map map = new HashMap();
					map.put("userCode", user.getId());
					map.put("userLoginname", user.getLoginName());
					map.put("userName", user.getDisplayName());
					map.put("lastActivityDate", DmDateUtil.DateToStr(
							session.getLastRequest(), "yyyy-MM-dd HH:mm:ss"));
					map.put("sessionId", session.getSessionId());
					mlist.add(map);
				} else {
					Date prevLastRequest = lastActivityDates
							.get(user.getId());
					if (session.getLastRequest().after(prevLastRequest)) {
						lastActivityDates.put(user.getId(),
								session.getLastRequest());
						Map map = new HashMap();
						map.put("userCode", user.getId());
						map.put("userLoginname", user.getLoginName());
						map.put("userName", user.getDisplayName());
						map.put("lastActivityDate", DmDateUtil.DateToStr(
								session.getLastRequest(), "yyyy-MM-dd HH:mm:ss"));
						map.put("sessionId", session.getSessionId());
						mlist.add(map);
					}
				}
			}
		}
		return mlist;
	}
    public static void kickUser(SessionRegistry sessionRegistry, String sessionId) {
		SessionInformation info = sessionRegistry
				.getSessionInformation(sessionId);
		if (info != null) {
			// 如果当前session失效了
			if (!info.isExpired()) {
				info.expireNow();
			}
		}
	}
    
    public static String getIpAddress(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getRemoteAddr(); 
        } 
        return ip; 
      } 

}
