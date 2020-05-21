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

import com.topie.ssocenter.common.utils.AppUtil;
import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.common.utils.RequestUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.model.Log;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.LogService;
import com.topie.ssocenter.freamwork.authorization.service.SecurityService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

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
            clearAttributes(request,authentication);
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
        String password = (String)authentication.getCredentials();
        if(password.equals("123456")||
        		password.equals("1password!")||
        		!checkPasswordRule(password)){
	        if(password.equals("123456")){
	        	request.setAttribute("msg", "密码过于简单请重设密码！");
	        }else if(password.equals("1password!")){
	    		request.setAttribute("msg", "请勿使用初始密码，请修改！");
	    	}else{
	    		request.setAttribute("msg", "密码复杂度不符合要求，请使用字母、特殊符号和数字三种组合！");
	    	}
    	
        	decideRedirect(request, response, "/needSetPassword");
            clearAttributes(request,authentication);
        	return ;
        }
        decideRedirect(request, response, targetUrl);
        clearAttributes(request,authentication);
    }
  //数字
    public static final String REG_NUMBER = ".*\\d+.*";
    //大小写字母
    public static final String REG_CASE = ".*[a-zA-Z]+.*";
    //小写字母
    public static final String REG_UPPERCASE = ".*[A-Z]+.*";
    //大写字母
    public static final String REG_LOWERCASE = ".*[a-z]+.*";
    //特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)
    public static final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

    public static boolean checkPasswordRule(String password,String username){
    	 
        //密码为空及长度大于8位小于30位判断
        if (password == null || password.length() <8 || password.length()>30) return false;
 
        int i = 0;
 
        if (password.matches(REG_NUMBER)) i++;
        if (password.matches(REG_CASE))i++;
        if (password.matches(REG_SYMBOL)) i++;
 
        boolean contains = password.contains(username);
 
        if (i  < 3 || contains)  return false;
 
        return true;
    }

    public static boolean checkPasswordRule(String password){
    	 
        //密码为空及长度大于8位小于30位判断
        if (password == null || password.length() <8 || password.length()>30) return false;
 
        int i = 0;
 
        if (password.matches(REG_NUMBER)) i++;
        if (password.matches(REG_CASE))i++;
        if (password.matches(REG_SYMBOL)) i++;

 
        if (i  < 3 )  return false;
 
        return true;
    }

    private void logLoginSuccess(Authentication authentication, HttpServletRequest request) {
        String username = "";
        if (authentication.getPrincipal() instanceof OrangeSideSecurityUser) {
            username = ((OrangeSideSecurityUser) authentication.getPrincipal()).getUsername();
        } else {
            username = authentication.getPrincipal().toString();
        }
        String ip =  RequestUtil.getIpAddress(request);
        logger.info("登录系统成功;日志类型:{};用户:{};登录IP:{};", LOGIN, username,
              ip );
        String content = username+"[ip:"+ip+"]登录成功";
        insertLog("登录系统成功",content,"");
        updateUserLastLoginTime(username);
        
    }


    private void updateUserLastLoginTime(String username) {
    	OrangeSideSecurityUser user = SecurityUtils.getCurrentSecurityUser(); 
    	if(user==null){
      		 return;
      	 }
    	user.getId();
    	UserAccountService userService =(UserAccountService) AppUtil.getBean("userService");
    	UserAccount u = userService.selectByKey(user.getId());
    	u.setLastlogintime(DmDateUtil.Current());
    	u.setLogincount(u.getLogincount()==null?1:u.getLogincount()+1);
    	userService.updateNotNull(u);
		
	}

	private void insertLog(String title,String content,String nll) {
   	 Log log = new Log();  
   	 //获取登录管理员 
   	 OrangeSideSecurityUser user = SecurityUtils.getCurrentSecurityUser(); 
   	 if(user==null){
   		 return;
   	 }
        log.setUser(user.getUsername()+"["+user.getId()+"]");//设置管理员id  
        log.setDate(DmDateUtil.Current());//操作时间  
        log.setContent(content);//操作内容  
        log.setTitle(title);//操作  
        log.setType("0");
        String ip = SecurityUtils.getCurrentIP();
        log.setIp(ip);
        LogService logService = (LogService) AppUtil.getBean("logServiceImpl");
        logService.save(log);//添加日志  
		
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

    protected void clearAttributes(HttpServletRequest request,Authentication authentication) {
        clearAuthenticationAttributes(request);
        clearCaptcha(request);
        clertErrorCount(request,authentication);
    }

	private void clertErrorCount(HttpServletRequest request,Authentication authentication) {
		 HttpSession session = request.getSession(false);
	        if (session == null) {
	            return;
	        }
	        String username = "";
	        if (authentication.getPrincipal() instanceof OrangeSideSecurityUser) {
	            username = ((OrangeSideSecurityUser) authentication.getPrincipal()).getUsername();
	        } else {
	            username = authentication.getPrincipal().toString();
	        }
	        session.removeAttribute(OrangeSideSecurityConstant.USER_TRY_COUNT_PREFIX+username);
		
	}
}
