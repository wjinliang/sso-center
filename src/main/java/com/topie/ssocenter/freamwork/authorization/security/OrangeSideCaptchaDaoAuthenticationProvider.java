package com.topie.ssocenter.freamwork.authorization.security;

import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class OrangeSideCaptchaDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, token);

        Object obj = token.getDetails();

        if (!(obj instanceof OrangeSideCaptchaAuthenticationDetails)) {
            throw new InsufficientAuthenticationException(
                    messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.captchaNotFound", null, null));
        }

        OrangeSideCaptchaAuthenticationDetails captchaDetails = (OrangeSideCaptchaAuthenticationDetails) obj;
        String captcha = captchaDetails.getCaptcha();
        if (captcha != null) {
            String expected = captcha;
            String actual = captchaDetails.getAnswer();
            if (!SecurityUtils.matchString(actual, expected)) {
                throw new BadCredentialsException(messageSource
                        .getMessage("AbstractUserDetailsAuthenticationProvider.captchaNotMatch", null, null));
            }
        }
    }

}
