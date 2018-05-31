package com.topie.ssocenter.freamwork.authorization.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.topie.ssocenter.common.utils.AppUtil;
import com.topie.ssocenter.common.utils.RequestUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/9 说明：
 */
public class OrangeSideSimpleUrlAuthenticationFailureHandler
        implements AuthenticationFailureHandler {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String defaultFailureUrl;
    private boolean forwardToDestination = false;
    private boolean allowSessionCreation = true;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private static final String LOGIN = "登录";
    private Integer maxPasswordErrorCount=5;

    public OrangeSideSimpleUrlAuthenticationFailureHandler() {
    }

    public OrangeSideSimpleUrlAuthenticationFailureHandler(String defaultFailureUrl) {
        setDefaultFailureUrl(defaultFailureUrl);
    }
    

    public Integer getMaxPasswordErrorCount() {
		return maxPasswordErrorCount;
	}

	public void setMaxPasswordErrorCount(Integer maxPasswordErrorCount) {
		this.maxPasswordErrorCount = maxPasswordErrorCount;
	}

	/**
     * Performs the redirect or forward to the {@code defaultFailureUrl} if set, otherwise returns a
     * 401 error code.
     * <p/>
     * If redirecting or forwarding, {@code saveException} will be called to cache the exception for
     * use in the target view.
     */
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        try {
        	exception = checkTryCount(request,exception);
            if (RequestUtil.isAjax(request)) {
                writeJson(response, exception);
                saveException(request, exception);
                return;
            }
            if (defaultFailureUrl == null) {
                logger.debug("No failure URL set, sending 401 Unauthorized error");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Authentication Failed: " + exception.getMessage());
            } else {
                saveException(request, exception);

                if (forwardToDestination) {
                    logger.debug("Forwarding to " + defaultFailureUrl);

                    request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
                } else {
                    logger.debug("Redirecting to " + defaultFailureUrl);
                    redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
                }
            }
        } finally {
            clearCaptcha(request);
            logger
                    .info("登录系统失败;原因:" + exception.getMessage() + ";日志类型:{};用户:{};登录IP:{};", LOGIN, "-",
                            RequestUtil.getIpAddress(request));
        }

    }

    private AuthenticationException checkTryCount(HttpServletRequest request, AuthenticationException exception) {
    	String errMsg = exception.getMessage();
    	if(!errMsg.equals("密码不正确"))return exception;
		String username = (String)request.getParameter(OrangeSideUsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
		HttpSession session = request.getSession();
		Object o = session.getAttribute(OrangeSideSecurityConstant.USER_TRY_COUNT_PREFIX+username);
		if(o==null){
			session.setAttribute(OrangeSideSecurityConstant.USER_TRY_COUNT_PREFIX+username, 1);
		}else{
			int error = Integer.valueOf(o.toString());
			session.setAttribute(OrangeSideSecurityConstant.USER_TRY_COUNT_PREFIX+username, error+1);
			if(maxPasswordErrorCount-error<=3){
				exception = new  DisabledException("密码错误，再输入"+(maxPasswordErrorCount-error)+"次错误，账号将被锁定");
			}
			if(error>=maxPasswordErrorCount){
				exception = new  DisabledException("登录错误次数超过"+maxPasswordErrorCount+"次，账号已锁定");
				session.removeAttribute(OrangeSideSecurityConstant.USER_TRY_COUNT_PREFIX+username);
				UserAccountService userService = (UserAccountService) AppUtil.getBean("userService");
				UserAccount user = userService.findUserAccountByLoginName(username);
				if(user==null) return new  DisabledException("用户不存在");
				user.setLocked(true);
				userService.updateNotNull(user);
				
			}
		}
		return exception;
	}

	private void writeJson(HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("message", exception.getMessage());
        if (defaultFailureUrl != null)
            map.put("targetUrl", defaultFailureUrl);
        ResponseUtil.writeJson(response, map);
    }

    /**
     * Caches the {@code AuthenticationException} for use in view rendering.
     * <p/>
     * If {@code forwardToDestination} is set to true, request scope will be used, otherwise it will
     * attempt to store the exception in the session. If there is no session and {@code
     * allowSessionCreation} is {@code true} a session will be created. Otherwise the exception will
     * not be stored.
     */
    protected final void saveException(HttpServletRequest request,
                                       AuthenticationException exception) {
        if (forwardToDestination) {
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        } else {
            HttpSession session = request.getSession(false);

            if (session != null || allowSessionCreation) {
                request.getSession()
                        .setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            }
        }
    }

    /**
     * The URL which will be used as the failure destination.
     *
     * @param defaultFailureUrl the failure URL, for example "/loginFailed.jsp".
     */
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl),
                "'" + defaultFailureUrl + "' is not a valid redirect URL");
        this.defaultFailureUrl = defaultFailureUrl;
    }

    protected boolean isUseForward() {
        return forwardToDestination;
    }

    /**
     * If set to <tt>true</tt>, performs a forward to the failure destination URL instead of a
     * redirect. Defaults to <tt>false</tt>.
     */
    public void setUseForward(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    /**
     * Allows overriding of the behaviour when redirecting to a target URL.
     */
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    protected boolean isAllowSessionCreation() {
        return allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }

    protected void clearCaptcha(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(OrangeSideSecurityConstant.CAPTCHA_SESSION_KEY);
    }
}
