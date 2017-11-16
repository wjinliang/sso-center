package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.utils.R;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.authorization.utils.SimpleCrypto;

@Controller
@RequestMapping("/syn")
public class SynIndexController {

	@Autowired
	private ApplicationInfoService appService;
	@Value("${liantongIP}")
	String liantongIP;
	@Value("${yidongIP}")
	String yidongIP;
	
	@RequestMapping("/listSynApp")
	public ModelAndView listSynApps(ModelAndView model){
		PageInfo<ApplicationInfo> list = this.appService.selectCurrentUserSynApps();
		model.addObject(R.PAGE, list);
		model.setViewName("/syn/index");
		return model;
	}
	
	@RequestMapping("/ssoServiceBySession")
	public ModelAndView ssoServiceBySession(ModelAndView model,String xtbs,HttpServletRequest request) throws Exception{
		PageInfo<ApplicationInfo> page = this.appService.selectCurrentUserSynApps();
		ApplicationInfo app= null;
		for(ApplicationInfo a:page.getList()){
			if(a.getAppCode().equals(xtbs)){
				app = a;
			}
		}
		if(app==null){
			model.addObject("msg","没有找到该系统");
			model.setViewName("/error/500");
			return model;
		}
		String uuid = SecurityUtils.getCurrentSecurityUser().getId();
		String masterPassword = "zcpt@123456";
			uuid = SimpleCrypto.encrypt(masterPassword, uuid);
			String appPath = getAppPath(request,app);
			if (appPath != null && !appPath.equals("")) {
				if (appPath.indexOf("?") >= 0) {
					appPath = appPath + "&AUTH_TICKET=" + uuid;
				} else {
					appPath = appPath + "?AUTH_TICKET=" + uuid;
				}
				Enumeration<String> parameterNames = request
						.getParameterNames();
				while (parameterNames.hasMoreElements()) {
					String string = (String) parameterNames.nextElement();
					if (string != null && !string.equals("")
							&& !string.equals("xtbs")) {
						String parameter = request.getParameter(string);
						appPath = appPath + "&" + string + "=" + parameter;
					}
				}
				//System.out.println(appPath);
				model.setViewName("redirect:"+appPath);
			} else {
				model.addObject("msg","该系统单点地址为空，请联系管理员！");
				model.setViewName("/error/500");
				
			}
		return model;
	}
	/**
	 * 通过当前访问得IP 获取要访问的应用网络类型
	 * @param request
	 * @param app
	 * @return
	 */
	private String getAppPath(HttpServletRequest request, ApplicationInfo app) {
		String path1 =  app.getAppPath1();
		if(path1==null || path1.equals("")){
			return app.getAppPath();
		}
		String ip = request.getServerName();
		if(ip!=null && ip.equalsIgnoreCase(liantongIP)){
			return app.getAppPath();
		}
		if(ip!=null && ip.equalsIgnoreCase(yidongIP)){
			return app.getAppPath1();
		}
		return app.getAppPath();
	}

}
