package com.topie.ssocenter.freamwork.authorization.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.topie.ssocenter.common.utils.RequestUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.service.SecurityService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/8 说明：
 */
public class OrangeSideSavedRequestAwareAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    private static final String LOGIN = "登录";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();
    private boolean useReferer = false;
    private SecurityService securityService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        try {
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if (savedRequest == null) {
                handle(request, response, authentication);
                return;
            }
            String targetUrlParameter = getTargetUrlParameter();
            if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils
                    .hasText(request.getParameter(targetUrlParameter)))) {
                requestCache.removeRequest(request, response);
                handle(request, response, authentication);
                return;
            }
            // Use the DefaultSavedRequest URL
            String targetUrl = savedRequest.getRedirectUrl();
            logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
            decideRedirect(request, response, targetUrl);
        } finally {
            clearAttributes(request);
            logLoginSuccess(authentication, request);
        }
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    /**
     * Invokes the configured {@code RedirectStrategy} with the URL returned by the {@code
     * determineTargetUrl} method. <p> The redirect will not be performed if the response has
     * already been committed.
     */
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response,
                          Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        decideRedirect(request, response, targetUrl);
        clearAttributes(request);
    }

    private void logLoginSuccess(Authentication authentication, HttpServletRequest request) {
        String username = "";
        if (authentication.getPrincipal() instanceof OrangeSideSecurityUser) {
            username = ((OrangeSideSecurityUser) authentication.getPrincipal()).getUsername();
        } else {
            username = authentication.getPrincipal().toString();
        }
        logger.info("登录系统成功;日志类型:{};用户:{};登录IP:{};", LOGIN, username,
                RequestUtil.getIpAddress(request));
    }

    private void decideRedirect(HttpServletRequest request, HttpServletResponse response,
                                String targetUrl) throws IOException {
        try {
            if (RequestUtil.isAjax(request)) {
                ResponseUtil.writeJson(response, 200, "login success!", targetUrl);
            } else {
                getRedirectStrategy().sendRedirect(request, response, targetUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            clearCaptcha(request);
        }
    }

    /**
     * Builds the target URL according to the logic defined in the main class Javadoc.
     */
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        if (isAlwaysUseDefaultTargetUrl()) {
            return getDefaultTargetUrl();
        }
        // Check for the parameter and use that if available
        String targetUrl = null;
        if (getTargetUrlParameter() != null) {
            targetUrl = request.getParameter(getTargetUrlParameter());

            if (StringUtils.hasText(targetUrl)) {
                logger.debug("Found targetUrlParameter in request: " + targetUrl);

                return targetUrl;
            }
        }
        if (useReferer && !StringUtils.hasLength(targetUrl)) {
            targetUrl = request.getHeader("Referer");
            logger.debug("Using Referer header: " + targetUrl);
        }
        String defaultAction = "";
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            defaultAction =
                    securityService.getDefaultAction(grantedAuthority.getAuthority());
            if (StringUtils.hasText(defaultAction)) {
                targetUrl = defaultAction;
                logger.debug("Using role defaultAction: " + targetUrl);
                break;
            }
        }
        if (!StringUtils.hasText(targetUrl)) {
            targetUrl = getDefaultTargetUrl();
            logger.debug("Using default Url: " + targetUrl);
        }
        return targetUrl;
    }

    @Override
    public void setUseReferer(boolean useReferer) {
        this.useReferer = useReferer;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    protected final void clearCaptcha(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(OrangeSideSecurityConstant.CAPTCHA_SESSION_KEY);
    }

    protected void clearAttributes(HttpServletRequest request) {
        clearAuthenticationAttributes(request);
        clearCaptcha(request);
    }
}
