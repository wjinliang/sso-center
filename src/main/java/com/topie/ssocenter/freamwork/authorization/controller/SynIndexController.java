package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.R;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.authorization.utils.SimpleCrypto;

@Controller
@RequestMapping("/syn")
public class SynIndexController {

	@Autowired
	private ApplicationInfoService appService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private UserAccountService userService;
	
	@Value("${liantongIP}")
	String liantongIP;
	@Value("${yidongIP}")
	String yidongIP;
	@Value("${encrypt.seed}")
	private String seed;
	
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
		OrangeSideSecurityUser user = SecurityUtils.getCurrentSecurityUser();
		String uuid = user.getId();
		if(user.getMergeUuid()!=null&&!user.getMergeUuid().equals("")){
			UserAccount u = userService.selectUserByXtbs(user.getMergeUuid(),app.getId());
			if(u!=null)
				uuid=u.getCode();
		}
		
			uuid = SimpleCrypto.encrypt(seed, uuid);
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
				System.out.println("访问地址："+appPath);
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
	
	@RequestMapping("listSynsApp")
	public ModelAndView listSynsApp(ModelAndView model,
		@RequestParam(value = "thispage", required = false) Integer thispage,
		@RequestParam(value = "pagesize", required = false) Integer pagesize,
		ApplicationInfo app) {

		if (pagesize == null) {
			pagesize = Integer.valueOf(10);
		}
		if (thispage == null) {
			thispage = Integer.valueOf(0);
		}
		PageInfo<ApplicationInfo> page = appService.findApplicationInfoList(thispage, pagesize, app);
		model.addObject(R.PAGE, page);
		model.addObject(R.SEARCHMODEL, app);
		model.setViewName("/syn/appList");
		return model;
	}
	
	@RequestMapping("listSynOrg")
	public ModelAndView listSynOrg(ModelAndView model,
		@RequestParam(value = "thispage", required = false) Integer thispage,
		@RequestParam(value = "pagesize", required = false) Integer pagesize,
		String appId,
		Org org) {

		if (pagesize == null) {
			pagesize = Integer.valueOf(10);
		}
		if (thispage == null) {
			thispage = Integer.valueOf(0);
		}
		PageInfo<Org> page = orgService.findSynOrgByAppId(thispage, pagesize, org ,appId);
		model.addObject(R.PAGE, page);
		model.addObject(R.SEARCHMODEL, org);
		model.addObject("appId", appId);
		model.setViewName("/syn/synOrg");
		return model;
	}
	@RequestMapping("listToSynOrg")
	public ModelAndView listToSynOrg(ModelAndView model,
		@RequestParam(value = "thispage", required = false) Integer thispage,
		@RequestParam(value = "pagesize", required = false) Integer pagesize,
		String appId,
		Org org) {

		if (pagesize == null) {
			pagesize = Integer.valueOf(10);
		}
		if (thispage == null) {
			thispage = Integer.valueOf(0);
		}
		PageInfo<Org> page = orgService.findToSynOrgByAppId(thispage, pagesize, org ,appId);
		model.addObject(R.PAGE, page);
		model.addObject(R.SEARCHMODEL, org);
		model.addObject("appId", appId);
		model.setViewName("/syn/toSynOrg");
		return model;
	}
	@RequestMapping("delSynOrg")
	@ResponseBody
	public Object delSynOrg(String appId,Long orgId){
		Org org = orgService.selectByKey(orgId);
		Map map = this.orgService.synOneOrg(org, appId ,"43","删除");
		if((boolean)map.get("status")){
			return ResponseUtil.success();
		}
		return ResponseUtil.error(map.get("result"));
	}
	@RequestMapping("synOrg")
	@ResponseBody
	public Object synOrg(String appId,Long orgId){
		Org org = orgService.selectByKey(orgId);
		Map map = this.orgService.synOneOrg(org, appId ,"41","新增");
		if((boolean)map.get("status")){
			return ResponseUtil.success();
		}
		return ResponseUtil.error(map.get("result"));
	}
	
	@RequestMapping("listSynUser")
	public ModelAndView listSynUser(ModelAndView model,
		@RequestParam(value = "thispage", required = false) Integer thispage,
		@RequestParam(value = "pagesize", required = false) Integer pagesize,
		String appId,
		UserAccount user) {

		if (pagesize == null) {
			pagesize = Integer.valueOf(10);
		}
		if (thispage == null) {
			thispage = Integer.valueOf(0);
		}
		PageInfo<UserAccount> page = userService.findSynUserByAppId(thispage, pagesize, user ,appId);
		model.addObject(R.PAGE, page);
		model.addObject(R.SEARCHMODEL, user);
		model.addObject("appId", appId);
		model.setViewName("/syn/synUser");
		return model;
	}
	@RequestMapping("listToSynUser")
	public ModelAndView listToSynUser(ModelAndView model,
		@RequestParam(value = "thispage", required = false) Integer thispage,
		@RequestParam(value = "pagesize", required = false) Integer pagesize,
		String appId,
		UserAccount user) {

		if (pagesize == null) {
			pagesize = Integer.valueOf(10);
		}
		if (thispage == null) {
			thispage = Integer.valueOf(0);
		}
		PageInfo<UserAccount> page = userService.findToSynUserByAppId(thispage, pagesize, user ,appId);
		model.addObject(R.PAGE, page);
		model.addObject(R.SEARCHMODEL, user);
		model.addObject("appId", appId);
		model.setViewName("/syn/toSynUser");
		return model;
	}
	@RequestMapping("delSynUser")
	@ResponseBody
	public Object delSynUser(String appId,String userId){
		UserAccount user = userService.selectByKey(userId);
		Map map = this.userService.synOneUser(user, appId ,"13","删除");
		if((boolean)map.get("status")){
			return ResponseUtil.success(map.get("result"));
		}
		return ResponseUtil.error(map.get("result"));
	}
	@RequestMapping("synUser")
	@ResponseBody
	public Object synUser(String appId,String userId){
		UserAccount user = userService.selectByKey(userId);
		Map map = this.userService.synOneUser(user, appId ,"11","新增");
		if((boolean)map.get("status")){
			return ResponseUtil.success(map.get("result"));
		}
		return ResponseUtil.error(map.get("result"));
	}
}
