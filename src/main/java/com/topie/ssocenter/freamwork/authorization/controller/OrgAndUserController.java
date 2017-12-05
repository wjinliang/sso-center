package com.topie.ssocenter.freamwork.authorization.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.common.utils.ExcelExportUtils;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.exception.AuthBusinessException;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.SynLog;
import com.topie.ssocenter.freamwork.authorization.model.SynOrg;
import com.topie.ssocenter.freamwork.authorization.model.SynUser;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.SynService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.service.UserRoleService;
import com.topie.ssocenter.freamwork.authorization.utils.R;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.authorization.utils.SimpleCrypto;


@Controller
@RequestMapping({ "/orgAndUser" })
public class OrgAndUserController {
	
	static final Logger logger = LoggerFactory.getLogger(OrgAndUserController.class);
	
	@Autowired
	OrgService orgService;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	DivisionService divisionService;
	@Autowired
	SynService synService;
	@Autowired
	ApplicationInfoService appService;
	
	@Value("${adminRole.id}")
	String adminId;
	@Value("${normalRole.id}")
	String normalRoleId;

	@RequestMapping({ "/listOrgs" })
	public ModelAndView list(
			ModelAndView model,
			@RequestParam(value = "thispage", required = false) Integer thispage,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			Org org) {
			if (pagesize == null) {
				pagesize = Integer.valueOf(10);
			}
			if (thispage == null) {
				thispage = Integer.valueOf(0);
			}
			model.addObject(R.SEARCHMODEL, org);
			setCurrentDivisionList(org,model);
			//获取当前用户所在区划 加载出当前区划下的机构
			PageInfo<Org> page = this.orgService.selectCurrentDivisionOrgPage(thispage,pagesize,org);
			model.addObject(R.PAGE, page);
			model.setViewName("/org/orgList");
			return model;
	}

	@RequestMapping("/form/{mode}")
	public ModelAndView form(
			HttpServletRequest request,
			ModelAndView model,
			@PathVariable String mode,
			Org org) {
			Division division = this.divisionService.selectByKey(org.getDivisionId());
			model.addObject("mode", mode);
			if(division==null){
				model.addObject("msg", "请选择区划");
				model.setViewName("error/500");
				return model;
			}
			model.addObject("division", division);
			if (mode != null && !mode.equals("new")) {//编辑
				org = this.orgService.selectByKey(org.getId());
				model.addObject("org", org);
				model.setViewName("/org/edit");
				return model;
			} 
			Long seq = getSeq(division);
			org.setSeq(seq);
			org.setCode(getOrgCode(division));
			model.addObject("org", org);
			model.setViewName("/org/edit");
			return model;
	}

	private Long getSeq(Division division) {
		String divisionCode = division.getCode().toString();
		if(divisionCode.length()>6){
			divisionCode = divisionCode.substring(0,6);
		}
		Long code = this.orgService.selectMaxSeq(divisionCode);//根据区划获取最大的code
		Long orgLsh = 1L;
		if (code != null && !code.equals("")) {
			orgLsh = code+1;
		}
		return orgLsh;
	}

	private String  getOrgCode(Division division){
		String divisionCode = division.getCode().toString();
		if(divisionCode.length()>6){
			divisionCode = divisionCode.substring(0,6);
		}
		String code = this.orgService.selectMaxCode(divisionCode);//根据区划获取最大的code
		String orgLsh = divisionCode + "001";
		if (code != null && !code.equals("")) {
			Long valueOf = Long.valueOf(code);
			orgLsh = String.valueOf(valueOf + 1);
		}
		return orgLsh;
	}

	@RequestMapping("/save")
	public ModelAndView save(
			ModelAndView model,
			Org org,@RequestParam(value="synApps",required=false) String synAppIds) {
		Long orgId = org.getId();
		if(orgId==null){//新增
			org.setId(System.currentTimeMillis());
			//验证code
			String code = org.getCode();
			String name = SecurityUtils.getCurrentUserName();
			String createTime = DmDateUtil.Current();
			org.setCreateDate(createTime);
			org.setCreateUser(name);
			Example ex = new Example(Org.class);
			ex.createCriteria().andEqualTo("code",code);
			List<Org> list = this.orgService.selectByExample(ex);
			if(list.size()>0){//编码重复  重新生成
				Division division = divisionService.selectByKey(org.getDivisionId());
				org.setCode(getOrgCode(division));
			}//验证code end
			orgService.save(org);
			List<Map> resultList = doTongBu("41",org,synAppIds,model);
			
			return model;
		}
		//更新
		this.orgService.updateNotNull(org);
		doTongBu("42",org,synAppIds,model);
		model.setViewName("redirect:list?divisionId="+org.getDivisionId()
				+"&parentId="+org.getId());
		return model;
	}
	private List<Map> doTongBu(String optype, Org org, String synAppIds,
			ModelAndView model) {
		List<Map> list = new ArrayList<Map>();
		if(synAppIds==null ||synAppIds.equals("")){
			return list;
		}
		if(optype=="41"){//新增
			for(String appId:synAppIds.split(",")){
				Map u = synOneOrg(org,appId,"41","新增");
				list.add(u);
			}
		}
		if(optype=="42"){//更新
			List<SynOrg> listInfo = synService.selectOrgSynInfo(org.getId());//已经同步过的APP
			for(String appId:synAppIds.split(",")){
				if(!getIsOpen(appId)){//没有同步权限
					continue;
				}
				boolean isSyn = false;//是否已经同步过了
				for(SynOrg synOrg:listInfo){
					if(synOrg.getAppId().equals(appId)){
						isSyn = true;
						break;
					}
				}
				if(isSyn){//如果同步过-》更新
					Map u = synOneOrg(org,appId,"42","更新");
					list.add(u);
				}else{//如果没有通不过-》新增
					Map u = synOneOrg(org,appId,"41","新增");
					list.add(u);
				}
			}
			
			for(SynOrg synOrg:listInfo){
				if(!getIsOpen(synOrg.getAppId())){//没有同步权限
					continue;
				}
				boolean isSyn = true;//是否已经同步过了
				String app="";
				for(String appId:synAppIds.split(",")){
					app = appId;
					if(synOrg.getAppId().equals(appId)){
						isSyn = false;
						break;
					}
				}
				if(isSyn){//以前同步过现在去掉同步 -》删除
					Map u = synOneOrg(org,app,"43","删除");
					list.add(u);
				}
			}
		}
		if(optype=="43"){//删除
			for(String appId:synAppIds.split(",")){
				Map u = synOneOrg(org,appId,"43","删除");
				list.add(u);
			}
		}
		model.addObject("resultList", list);//同步结果
		int redirect=0;
		String appCode="";
		for(Map map:list){
			if("42".equals(map.get("opType").toString())){//如果新增  则设置权限
				if(map.get("isAuthorize")!=null && (Boolean)map.get("isAuthorize")){//如果要授权
					redirect++;
					appCode = map.get("appCode").toString();
				};
			}
		}
		if(redirect==0){//返回到列表页面
			model.setViewName("redirect:form/edit?divisionId="+org.getDivisionId()
					+"&id="+org.getId()+"&parentId="+org.getParentId());
		}
		if(redirect==1){//TODO 一个app需要授权  直接打开授权页面
			model.setViewName("redirect:"
					+ "../synuseraccount/ssoServiceBySession?xtbs="+appCode
					+"&TYPE="+
					R.ORG_AUTHORIZE+"&ID="+org.getId());
		}
		if(redirect > 1){//TODO 多个app 需要授权   手动选择授权
			model.setViewName("redirect:form/authorize?divisionId="+org.getDivisionId()
					+"&id="+org.getId()+"&parentId="+org.getParentId());
		}
		return list;
	}

	private boolean getIsOpen(String appId) {
		List<ApplicationInfo> list = appService.selectCurrentUserSynApps().getList();//当前admin 可以同步的APP
		for(ApplicationInfo app:list){
			if(app.getId().equals(appId)){//当前用户有操作权限
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private Map synOneOrg(Org org, String appId,String type,String typeName) {
		ApplicationInfo app = appService.selectByKey(appId);
		
		Map u = new HashMap();
		u.put("opType", typeName);
		u.put("appName", app.getAppName());
		u.put("appId", app.getId());
		u.put("appCode", app.getAppCode());
		u.put("status", true);
//		u.put(, );
		if(app==null){
			String s = "同步"+typeName+"Org时 {appId="+appId+"}未找到对应的应用";
			logger.info(s);
			u.put("result", s);
			u.put("status", false);
			return u;
		}
		String result = "000";
		String today = DmDateUtil.Current();
		if(app.getIsOrgSyn()){//如果该系统要同步机构
			logger.info("开始同步Org："+app.getAppName()+"-"+org.getName());
			result = this.synService.synStart(appId, org.getId()
					.toString(), type);
			if (result != null && result.equals("000")) {
				result = "同步成功";
				SynOrg synOrg = new SynOrg();
				String uuid = UUIDUtil.getUUID();
				synOrg.setAppId(appId);
				synOrg.setId(uuid);
				synOrg.setOrgId(org.getId().toString());
				synOrg.setSynTime(today);
				this.synService.save(synOrg);
				u.put("isAuthorize",app.getIsOrgAuthorize());
			}else{
				u.put("status", false);
			}
		}else{//不同不到该系统
			result = "该系统设置为不同步机构";
		}
		/*
		 * 添加记录日志的操作
		 */
		SynLog synLog = new SynLog();
		synLog.setId(UUIDUtil.getUUID());
		synLog.setAppId(appId);
		synLog.setAppName(app.getAppName());
		synLog.setSynTime(today);
		synLog.setSynResult("组织(" + org.getName() + ")"+typeName+"操作："
				+ result);
		OrangeSideSecurityUser currentUser = SecurityUtils.getCurrentSecurityUser();
		synLog.setSynUserid(currentUser.getId());
		synLog.setSynUsername(currentUser.getDisplayName());
		this.synService.save(synLog);
		u.put("result", result);
		return u;
		
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object deleteOrg(String ids){
		if(StringUtils.isEmpty(ids)){
			return ResponseUtil.error("请选择要删除的项");
		}
		boolean result = true;
		String[] idArr = ids.split(",");
		for(int i =0 ;i<idArr.length;i++){
			Org org = orgService.selectByKey(Long.valueOf(idArr[i]));
			List<SynOrg> listInfo = synService.selectOrgSynInfo(org.getId());//已经同步过的APP
			String synAppIds = "";
			for(SynOrg synOrg:listInfo){
				synAppIds +=","+synOrg.getAppId();
			}
			if(synAppIds.equals("")){
				int count = this.orgService.delete(Long.valueOf(idArr[i]));
				return  ResponseUtil.success();
			}else{
				synAppIds = synAppIds.substring(1);
				List<Map> resultList = doTongBu("43", org, synAppIds, null);
				for(Map map:resultList){
					if(!(boolean)map.get("status")){//同步有失败
						result = false;
						break;
					}
				}
				if(result){
					int count = this.orgService.delete(Long.valueOf(idArr[i]));
					return ResponseUtil.success(resultList);
				}else{//同步有失败
					return ResponseUtil.error(resultList);
				}
			}
		}
		return ResponseUtil.success();
	}
	@RequestMapping({ "/listUsers" })
	public ModelAndView listUsers(
			ModelAndView model,
			UserAccount user,
			Org org,
			@RequestParam(value = "thispage", required = false) Integer thispage,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {
		if (pagesize == null) {
			pagesize = Integer.valueOf(10);
		}
		if (thispage == null) {
			thispage = Integer.valueOf(0);
		}
		model.addObject(R.SEARCHMODEL,user);
		//获取当前用户的区划
		org = setCurrentDivisionList(org,model);
		model.addObject(R.SEARCHMODEL+"Org",org);
		//获取当前机构用户列表
		PageInfo<UserAccount> page = this.orgService.selectCurrentOrgUserPage(thispage,pagesize,org,user);
		model.addObject(R.PAGE, page);
		model.setViewName("/user/userList");
		return model;
	}

	@RequestMapping({ "/user/{mode}" })
	public ModelAndView listUsers(
			ModelAndView model,
			@PathVariable String mode,
			UserAccount user,
			Org org) {
		model.addObject("mode",mode);
		model.addObject("org",org);
		String useraccountid = user.getCode();
		Long orgid = user.getOrgId();
		if (mode != null && !mode.equals("new")) {//编辑
			if (useraccountid != null) {
				user = userAccountService.selectByKey(useraccountid);
				//获取用户的权限 是否为管理员
				List<String> list = userRoleService.selectRolesByUserId(useraccountid);
				for(String m:list){
					if(m.equals(adminId)){
						model.addObject("isAdmin", "true");
						break;
					}
				}
			}
		} else {//新增
			if (orgid == null) {
				throw new AuthBusinessException("请选择结构！");
			}
			org = this.orgService.selectByKey(orgid);
			if (org == null) {
				throw new AuthBusinessException("请选择结构！");
			}
			String code = org.getCode();
			String loginName = this.orgService.selectNextUserLoginNameByOrgCode(code);
			user.setLoginname(loginName);
			user.setEnabled(true);
			
		}
//		UserAccount currentUserAccount = UserAccountUtil.getInstance()
//				.getCurrentUserAccount();
//		model.addObject("currentUser",currentUserAccount);
		model.addObject("userAccount", user);
		model.setViewName("/user/edit");
		return model;
	}
	
	@RequestMapping("user/save")
	public ModelAndView user_save(UserAccount user,
			@RequestParam(value="synApps",required=false) String synAppIds,
			ModelAndView model,String isAdmin) throws Exception{
		model.setViewName("listUsers?orgId="+user.getOrgId());
		if(StringUtil.isNotEmpty(user.getCode())){//更新
			this.userAccountService.updateNotNull(user);
			if(isAdmin.equals("true")){
				try{
				this.userRoleService.insertUserAccountRole(user.getCode(),this.adminId);
				}catch(Exception e){//之前已存在  报主键重复错误
					
				}
			}
			if(isAdmin.equals("false")){
				this.userRoleService.deleteUserAccountRole(user.getCode(),this.adminId);
			}
			return model;
		}
		//新增
		user.setCode(UUIDUtil.getUUID());
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		String password = user.getPassword();
		user.setPassword(sha.encodePassword(password, null));
		String encryptPassword = SimpleCrypto.encrypt("zcpt@123456",
				password);
		user.setSynpassword(encryptPassword);
		user.setLocked(false);
		user.setAccountExpired(true);
		user.setPasswordExpired(true);
		user.setCreateDate(DmDateUtil.Current());
		user.setCreateUser(SecurityUtils.getCurrentUserName());
		// 设置角色
		this.userRoleService.insertUserAccountRole(user.getCode(),this.normalRoleId);
		if(isAdmin.equals("true")){
			this.userRoleService.insertUserAccountRole(user.getCode(),this.adminId);
		}
		List<Map> list = doTongBu("11", user, synAppIds, model);
		return model;
	}
	
	private List<Map> doTongBu(String optype, UserAccount user, String synAppIds,
			ModelAndView model) {
		List<Map> list = new ArrayList<Map>();
		if(synAppIds!=null&& !synAppIds.equals("")){
			return list;
		}
		if(optype=="11"){//新增
			for(String appId:synAppIds.split(",")){
				Map u = synOneUser(user,appId,"11","新增");
				list.add(u);
			}
		}
		if(optype=="12"){//更新
			List<SynUser> listInfo = synService.selectUserSynInfo(user.getCode());//已经同步过的APP
			for(String appId:synAppIds.split(",")){
				if(!getIsOpen(appId)){//没有同步权限
					continue;
				}
				boolean isSyn = false;//是否已经同步过了
				for(SynUser synUser:listInfo){
					if(synUser.getAppId().equals(appId)){
						isSyn = true;
						break;
					}
				}
				if(isSyn){//如果同步过-》更新
					Map u = synOneUser(user,appId,"12","更新");
					list.add(u);
				}else{//如果没有通不过-》新增
					Map u = synOneUser(user,appId,"11","新增");
					list.add(u);
				}
			}
			
			for(SynUser synUser:listInfo){
				if(!getIsOpen(synUser.getAppId())){//没有同步权限
					continue;
				}
				boolean isSyn = true;//是否已经同步过了
				String app="";
				for(String appId:synAppIds.split(",")){
					app = appId;
					if(synUser.getAppId().equals(appId)){
						isSyn = false;
						break;
					}
				}
				if(isSyn){//以前同步过现在去掉同步 -》删除
					Map u = synOneUser(user,app,"13","删除");
					list.add(u);
				}
			}
		}
		if(optype=="13"){//删除
			for(String appId:synAppIds.split(",")){
				Map u = synOneUser(user,appId,"13","删除");
				list.add(u);
			}
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private Map synOneUser(UserAccount user, String appId,String type,String typeName) {
		ApplicationInfo app = appService.selectByKey(appId);
		Map u = new HashMap();
		u.put("opType", typeName);
		u.put("appName", app.getAppName());
		u.put("appId", app.getId());
		u.put("status", true);
		if(app==null){
			String s = "同步"+typeName+"User时 {appId="+appId+"}未找到对应的应用";
			logger.info(s);
			u.put("result", s);
			u.put("status", false);
			return u;
		}
		String today = DmDateUtil.Current();
		String result="000";
		if(app.getIsUserSyn()){
			result = this.synService.synStart(appId, user.getCode()
					, type);
			if (result != null && result.equals("000")) {
				result = "同步成功";
				SynUser synUser = new SynUser();
				String uuid = UUIDUtil.getUUID();
				synUser.setAppId(appId);
				synUser.setId(uuid);
				synUser.setUserId(user.getCode());
				synUser.setSynTime(today);
				this.synService.save(synUser);
				u.put("isAuthorize",app.getIsUserAuthorize());
			}else{
				u.put("status", false);
			}
		}else{//不同不到该系统
			result = "该系统设置为不同步用户";
		}
		u.put("result", result);
		/*
		 * 添加记录日志的操作
		 */
		SynLog synLog = new SynLog();
		synLog.setId(UUIDUtil.getUUID());
		synLog.setAppId(appId);
		synLog.setAppName(app.getAppName());
		synLog.setSynTime(today);
		synLog.setSynResult("用户(" + user.getName()+"["+user.getLoginname()+"]" + ")"+typeName+"操作："
				+ result);
		OrangeSideSecurityUser currentUser = SecurityUtils.getCurrentSecurityUser();
		synLog.setSynUserid(currentUser.getId());
		synLog.setSynUsername(currentUser.getDisplayName());
		this.synService.save(synLog);
		return u;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Org setCurrentDivisionList(Org org, ModelAndView model) {
		//获取当前用户的区划
		String divisionid = org.getDivisionId();
		if(divisionid==null){
			org = this.orgService.selectByKey(org.getId());
			if(org!=null){
				divisionid = org.getDivisionId();
			}
		}
		List<Division> division = this.orgService.selectCurrentDivisionTree(divisionid);
		List ml = new ArrayList();
		//String ids = "";
		for (Division divi : division) {
			Map m = new HashMap();
			//ids+=divi.getId()+",";
			m.put("id", divi.getId());
			m.put("name", divi.getName());
			m.put("pId",divi.getParentId()==null?"":divi.getParentId());
			List<Division> sonList = divisionService.findByPid(divi.getId());
			if (sonList != null && sonList.size() > 0) {
				m.put("isParent", "true");
			}
			ml.add(m);
		}
		JSONArray arr = new JSONArray(ml);
		model.addObject("divisionStr", arr.toJSONString());
		return org;
	}

	@RequestMapping({ "/listMergeUsers" })
	public ModelAndView listMergeUsers(
			ModelAndView model,
			@RequestParam(value = "thispage", required = false) Integer thispage,
			UserAccount user,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {
			if (pagesize == null) {
				pagesize = Integer.valueOf(10);
			}
			if (thispage == null) {
				thispage = Integer.valueOf(0);
			}
			PageInfo<UserAccount> userList = this.orgService.listMergeUsers(
					user, thispage, pagesize);
			model.addObject(R.SEARCHMODEL, user);
			model.addObject(R.PAGE, userList);
			model.setViewName("/org/listMergeUsers");
			return model;
	}

	
	@RequestMapping({ "/gotoMergeUser" })
	public ModelAndView gotoMergeUser(
			ModelAndView model,
			@RequestParam(value = "thispage", required = false, defaultValue = "0") Integer thispage,
			@RequestParam(value = "pagesize", required = false, defaultValue = "6") Integer pagesize,
			@RequestParam(value = "tj", required = false) String name,
			@RequestParam(value = "xt", required = false) String systemId,
			@RequestParam(value = "userid", required = true) String userid) {
		//TODO 用户关联列表
			/*List<UserAccount> userList = this.orgAndUserService.listUsers(null,
					systemId, name, thispage, pagesize);
			List<Order> orders = new ArrayList<Order>();
			orders.add(new Order(Direction.ASC, "seq"));
			List<ApplicationInfo> appList = this.commonDAO
					.findAll(ApplicationInfo.class,orders);
			model.addObject("appList", appList);
			model.addObject("userList", userList);
			model.addObject("userid", userid);
			model.setViewName("/pages/admin/useraccount/user");*/
			return model;
	}

	@RequestMapping({ "/mergeUser" })
	public ModelAndView mergeUser(ModelAndView model,
			@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "userIds", required = true) String userIds) {
		//TODO 用户关联
			/*UserAccount currentUser = UserAccountUtil.getInstance()
					.getCurrentUserAccount();
			UserAccount user = this.commonDAO
					.findOne(UserAccount.class, userid);
			String[] userIdArray = userIds.trim().split(",");
			String uuid = null;
			if (user.getMergeUuid() != null && !user.getMergeUuid().equals("")) {
				uuid = user.getMergeUuid();
			} else {
				uuid = UUIDUtil.getUUID();
				user.setMergeUuid(uuid);
			}
			for (int i = 0; i < userIdArray.length; i++) {
				String infoCode = userIdArray[i];
				UserAccount mergeUser = this.commonDAO.findOne(
						UserAccount.class, infoCode);
				mergeUser.setMergeUuid(uuid);
				this.userAccountService.updateUser(mergeUser);
			}
			this.userAccountService.updateUser(user);
			model.addObject("userid", userid);
			return NewRedirect(model, "/orgAndUser/listMergeUsers");*/
		return model;
	}

	@RequestMapping({ "/deleteMerge" })
	public void deleteMerge(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userid", required = false) String userid)
			throws Exception {
		
		//TODO 用户关联删除
		/*response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if (userid != null) {
				String[] rid = userid.split(",");
				for (String str : rid) {
					UserAccount findOne = this.commonDAO.findOne(
							UserAccount.class, str);
					findOne.setMergeUuid(null);
					this.userAccountService.updateUser(findOne);
				}
			}
			out.write("ok");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.write("error");
			out.flush();
			out.close();
		}*/
	}

	@RequestMapping("/excelExport")
	public void excelExport(HttpServletRequest request, HttpServletResponse response,Org org,UserAccount user)
	{
		String fileName = "";
		Org o = this.orgService.selectByKey(org.getId());
		try {
			//fileName = java.net.URLEncoder.encode("报名统计表", "ISO8859_1");
			fileName=new String((o.getName()+"导出用户").getBytes(), "ISO8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}  
		response.setContentType("application/vnd.ms-excel;charset=ISO8859_1"); 
		response.setHeader("content-disposition", "attachment;filename="+fileName+".xls");  
		OutputStream fOut = null;
		try {
			fOut = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<UserAccount> list = this.orgService.selectCurrentOrgUserPage(0, 1000, org, user).getList();
		String[] fields = {"loginname","name","gender","mobile","bizPhoneNo","schoolAge","address","title","speciality","email","fano","userType","systemId"};
		String[] names = {"登录名","姓名","性别"			,"手机","固话","学历","通讯地址",			"职称","专业","邮箱","传真","用户类型","系统标识"};
		Workbook workbook;
		try {
			workbook = ExcelExportUtils.getInstance().inExcel(list, fields, names);
			workbook.write(fOut);  
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
