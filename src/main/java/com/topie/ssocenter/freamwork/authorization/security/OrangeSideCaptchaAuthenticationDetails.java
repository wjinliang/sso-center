package com.topie.ssocenter.freamwork.authorization.security;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

public class OrangeSideCaptchaAuthenticationDetails implements Serializable {

    private static final long serialVersionUID = 8047091036777813803L;

    private final String answer;
    private final String captcha;
    private final String ip;

    public OrangeSideCaptchaAuthenticationDetails(HttpServletRequest req) {
        this.answer = req.getParameter(OrangeSideSecurityConstant.CAPTCHA_REQUEST_KEY);
        this.captcha = (String) WebUtils
                .getSessionAttribute(req, OrangeSideSecurityConstant.CAPTCHA_SESSION_KEY);
        String ip = SecurityUtils.getIpAddress(req );
        this.ip=ip;

    }

    public String getAnswer() {
        return answer;
    }

    public String getCaptcha() {
        return captcha;
    }
    public String getIp() {
        return ip;
    }

}
