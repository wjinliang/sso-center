package com.topie.ssocenter.freamwork.authorization.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class OrangeSideCaptchaAuthenticationDetailsSource
        implements AuthenticationDetailsSource<HttpServletRequest, OrangeSideCaptchaAuthenticationDetails> {

    @Override
    public OrangeSideCaptchaAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new OrangeSideCaptchaAuthenticationDetails(context);
    }
}
