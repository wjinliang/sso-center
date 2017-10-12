package com.topie.ssocenter.freamwork.authorization.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.exception.AuBzConstant;
import com.topie.ssocenter.freamwork.authorization.exception.AuthBusinessException;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityConstant;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.tools.patchca.utils.ImageUtils;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/1 说明：
 */
@Controller
@RequestMapping("/security")
public class SecurityController {
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
    @Autowired
    SessionRegistry sessionRegistry;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/menu")
    @ResponseBody
    public Object getCurrentUserMenu(){
    	String currentUserName = SecurityUtils.getCurrentUserName();
        if (StringUtils.isEmpty(currentUserName)) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }
        OrangeSideSecurityUser userDetails = (OrangeSideSecurityUser) SecurityContextHolder.getContext()
        	    .getAuthentication()
        	    .getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for(GrantedAuthority authen :authorities){
        	System.out.println(authen.getAuthority().toString());
        }
        return ResponseUtil.success(authorities);
    }

    @RequestMapping("/web/onlineUser")
    public
    @ResponseBody
    Object onlineUser() {
        List<SessionInformation> sessions = new ArrayList<SessionInformation>();
        for (Object o : sessionRegistry.getAllPrincipals()) {
            for (SessionInformation sessionInformation : sessionRegistry.getAllSessions(o, false)) {
                sessions.add(sessionInformation);
            }
        }
        return sessions;
    }

    @RequestMapping(value = "/web/loginStateScript", produces = "text/html;charset=UTF-8")
    public
    @ResponseBody
    String loginStateScript() {
        StringBuffer stringBuffer = new StringBuffer();
        String userName = SecurityUtils.getCurrentUserName();
        if (userName != null) {
            stringBuffer.append("var os_siLogin = true;");
            stringBuffer.append("var os_userName = " + userName + ";");
        } else {
            stringBuffer.append("var os_siLogin = false;");
        }
        return stringBuffer.toString();
    }

    @RequestMapping(value = "/web/loginStateJson")
    public
    @ResponseBody
    Object loginStateJson() {
        Map map = new HashMap();
        String userName = SecurityUtils.getCurrentUserName();
        if (userName != null) {
            map.put("os_isLogin", true);
            map.put("os_userName", userName);
            OrangeSideSecurityUser user = SecurityUtils.getCurrentSecurityUser();
            map.put("os_user", user);
        } else {
            map.put("os_isLogin", false);
        }
        return map;
    }

    @RequestMapping(value = "/web/captcha", produces = "text/html;charset=UTF-8")
    public void captcha(HttpServletResponse response, HttpSession session) throws IOException {
        String captcha = ImageUtils.getPatchcaString(response);
        logger.debug(session.getId() + "-验证码:[{}]", captcha);
        session.setAttribute(OrangeSideSecurityConstant.CAPTCHA_SESSION_KEY,
                SecurityUtils.encodeString(captcha));
    }
}
