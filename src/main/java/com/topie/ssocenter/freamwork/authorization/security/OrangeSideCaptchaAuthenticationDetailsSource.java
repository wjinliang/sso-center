package com.topie.ssocenter.freamwork.authorization.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;

public class OrangeSideCaptchaAuthenticationDetailsSource
        implements AuthenticationDetailsSource<HttpServletRequest, OrangeSideCaptchaAuthenticationDetails> {

    @Override
    public OrangeSideCaptchaAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new OrangeSideCaptchaAuthenticationDetails(context);
    }
}
