package com.topie.ssocenter.freamwork.authorization.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;

/**
 * Created by cgj on 2015/10/26.
 */
public class SecurityUtils {
    public static String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof OrangeSideSecurityUser)
            userName = ((OrangeSideSecurityUser) principal).getUsername();
        return userName;
    }

    public static OrangeSideSecurityUser getCurrentSecurityUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof OrangeSideSecurityUser)
            return (OrangeSideSecurityUser) principal;
        return null;
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
    
}
