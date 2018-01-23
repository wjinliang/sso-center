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
import com.topie.ssocenter.freamwork.authorization.model.SynOrg;
import com.topie.ssocenter.freamwork.authorization.model.SynUser;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
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
	private OrgService orgService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private DivisionService divisionService;
	@Autowired
	private SynService synService;
	@Autowired
	private ApplicationInfoService appService;
	
	@Value("${adminRole.id}")
	private String adminId;
	@Value("${normalRole.id}")
	private String normalRoleId;
	@Value("${encrypt.seed}")
	private String seed;

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
		model.addObject("backUrl", "listOrgs?divisionId="+org.getDivisionId());
		model.setViewName("redirect:listOrgs?divisionId="+org.getDivisionId());
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
			model.addObject("org", org);
			model.addObject("id", org.getId());
			return model;
		}
		//更新
		this.orgService.updateNotNull(org);
		doTongBu("42",org,synAppIds,model);
		model.addObject("org", org);
		model.addObject("id", org.getId());
		return model;
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
	
	@SuppressWarnings("rawtypes")
	private List<Map> doTongBu(String optype, Org org, String synAppIds,
			ModelAndView model) {
		List<Map> list = new ArrayList<Map>();
		if(optype=="41"){//新增
			if(!StringUtils.isEmpty(synAppIds)){
				for(String appId:synAppIds.split(",")){
					Map u = orgService.synOneOrg(org,appId,"41","新增");
					list.add(u);
				}
			}
		}
		if(optype=="42"){//更新
			List<SynOrg> listInfo = synService.selectOrgSynInfo(org.getId());//已经同步过的APP
			if(!StringUtils.isEmpty(synAppIds)){
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
						Map u = orgService.synOneOrg(org,appId,"42","更新");
						list.add(u);
					}else{//如果没有通不过-》新增
						Map u = orgService.synOneOrg(org,appId,"41","新增");
						list.add(u);
					}
				}
			}
			
			for(SynOrg synOrg:listInfo){
				if(!getIsOpen(synOrg.getAppId())){//没有同步权限
					continue;
				}
				boolean isSyn = true;//是否已经同步过了
				String app="";
				if(!StringUtils.isEmpty(synAppIds)){
					for(String appId:synAppIds.split(",")){
						app = appId;
						if(synOrg.getAppId().equals(appId)){
							isSyn = false;
							break;
						}
					}
				}
				if(isSyn){//以前同步过现在去掉同步 -》删除
					Map u = orgService.synOneOrg(org,app,"43","删除");
					list.add(u);
				}
			}
		}
		if(optype=="43"){//删除
			if(!StringUtils.isEmpty(synAppIds)){
				for(String appId:synAppIds.split(",")){
					Map u = orgService.synOneOrg(org,appId,"43","删除");
					list.add(u);
				}
			}
		}
		model.addObject("resultList", list);//同步结果
		if(list.size()==0){
			model.setViewName("redirect:listOrgs?divisionId="+org.getDivisionId());
			return list;
		}
		int redirect=0;
		String appCode="";
		for(Map map:list){
			if("41".equals(map.get("opTypeCode").toString())){//如果新增  则设置权限
				if(map.get("isAuthorize")!=null && (Boolean)map.get("isAuthorize")){//如果要授权
					redirect++;
					appCode = map.get("appCode").toString();
				};
			}
		}
		if(redirect==0){//返回到列表页面
			/*model.setViewName("redirect:form/edit?divisionId="+org.getDivisionId());*/
			model.setViewName("redirect:listOrgs?divisionId="+org.getDivisionId());
		}
		if(redirect==1){
			model.setViewName("redirect:"
					+ "../syn/ssoServiceBySession?xtbs="+appCode
					+"&TYPE="+
					R.ORG_AUTHORIZE+"&ID="+org.getId());
		}
		if(redirect > 1){
			model.setViewName("/org/synResult");
		}
		return list;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
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
		model.addObject("backUrl", "../listUsers?orgId="+user.getOrgId());
		model.setViewName("redirect:/orgAndUser/listUsers?orgId="+user.getOrgId());
		if(StringUtil.isNotEmpty(user.getCode())){//更新
			this.userAccountService.updateNotNull(user);
			if(isAdmin!=null && isAdmin.equals("true")){
				try{
				this.userRoleService.insertUserAccountRole(user.getCode(),this.adminId);
				}catch(Exception e){//之前已存在  报主键重复错误
					
				}
			}else{
				this.userRoleService.deleteUserAccountRole(user.getCode(),this.adminId);
			}
			List<Map> list = doTongBu("12", user, synAppIds, model);
			this.updateUserSystem(user);//更新用户所属系统
			model.addObject("id", user.getCode());
			return model;
		}
		//新增
		user.setCode(UUIDUtil.getUUID());
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		String password = user.getPassword();
		user.setPassword(sha.encodePassword(password, null));
		String encryptPassword = SimpleCrypto.encrypt(seed,
				password);
		user.setSynpassword(encryptPassword);
		user.setLocked(false);
		user.setAccountExpired(true);
		user.setPasswordExpired(true);
		user.setCreateDate(DmDateUtil.Current());
		user.setCreateUser(SecurityUtils.getCurrentUserName());
		this.userAccountService.save(user);
		// 设置角色
		this.userRoleService.insertUserAccountRole(user.getCode(),this.normalRoleId);
		if(isAdmin.equals("true")){
			this.userRoleService.insertUserAccountRole(user.getCode(),this.adminId);
		}
		List<Map> list = doTongBu("11", user, synAppIds, model);
		updateUserSystem(user);//更新用户所属系统
		model.addObject("id", user.getCode());
		return model;
	}
	/**
	 * 更新用户所属系统
	 * @param user
	 */
	private void updateUserSystem(UserAccount user) {
		List<SynUser> listInfo = synService.selectUserSynInfo(user.getCode());//已经同步过的APP
		String sysStr = "";
		for(SynUser syn:listInfo){
			ApplicationInfo app = this.appService.selectByKey(syn.getAppId());
			if(app!=null){
				sysStr+=","+app.getAppName();
			}
		}
		if(!sysStr.equals("")){
			sysStr = sysStr.substring(1);
		}
		user.setSystemId(sysStr);
		this.userAccountService.updateNotNull(user);
		
	}

	private List<Map> doTongBu(String optype, UserAccount user, String synAppIds,
			ModelAndView model) {
		List<Map> list = new ArrayList<Map>();
		if(optype=="11"){//新增
			if(!StringUtils.isEmpty(synAppIds)){
				for(String appId:synAppIds.split(",")){
					Map u = userAccountService.synOneUser(user,appId,"11","新增");
					list.add(u);
				}
			}
		}
		if(optype=="12"){//更新
			List<SynUser> listInfo = synService.selectUserSynInfo(user.getCode());//已经同步过的APP
			if(!StringUtils.isEmpty(synAppIds)){
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
						Map u = userAccountService.synOneUser(user,appId,"12","更新");
						list.add(u);
					}else{//如果没有通不过-》新增
						Map u = userAccountService.synOneUser(user,appId,"11","新增");
						list.add(u);
					}
				}
			}
			
			for(SynUser synUser:listInfo){
				if(!getIsOpen(synUser.getAppId())){//没有同步权限
					continue;
				}
				boolean isSyn = true;//是否已经同步过了
				String app="";
				if(!StringUtils.isEmpty(synAppIds)){
					for(String appId:synAppIds.split(",")){
						app = appId;
						if(synUser.getAppId().equals(appId)){
							isSyn = false;
							break;
						}
					}
				}
				if(isSyn){//以前同步过现在去掉同步 -》删除
					Map u = userAccountService.synOneUser(user,app,"13","删除");
					list.add(u);
				}
			}
		}
		if(optype=="13"){//删除
			if(!StringUtils.isEmpty(synAppIds)){
				for(String appId:synAppIds.split(",")){
					Map u = userAccountService.synOneUser(user,appId,"13","删除");
					list.add(u);
				}
			}
		}
		model.addObject("resultList", list);//同步结果
		if(list.size()==0){//不需要同步
			model.setViewName("redirect:/orgAndUser/listUsers?orgId="+user.getOrgId());
			return list;
		}
		int redirect=0;
		String appCode="";
		for(Map map:list){
			if("11".equals(map.get("opTypeCode").toString())){//如果新增  则设置权限
				if(map.get("isAuthorize")!=null && (Boolean)map.get("isAuthorize")){//如果要授权
					redirect++;
					appCode = map.get("appCode").toString();
				};
			}
		}
		if(redirect==0){//返回到列表页面
			model.setViewName("redirect:/orgAndUser/listUsers?orgId="+user.getOrgId());
		}
		if(redirect==1){
			model.setViewName("redirect:"
					+ "/syn/ssoServiceBySession?xtbs="+appCode
					+"&TYPE="+
					R.ORG_AUTHORIZE+"&ID="+user.getCode());
		}
		if(redirect > 1){
			model.setViewName("/user/synResult");
		}
		return list;
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
			Org org,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {
			if (pagesize == null) {
				pagesize = Integer.valueOf(10);
			}
			if (thispage == null) {
				thispage = Integer.valueOf(0);
			}
			PageInfo<UserAccount> userList = this.userAccountService.listMergeUsers(
					user, thispage, pagesize);
			model.addObject(R.SEARCHMODEL, user);
			model.addObject(R.SEARCHMODEL+"_org",org);
			model.addObject(R.PAGE, userList);
			model.setViewName("/user/listMergeUsers");
			return model;
	}

	@RequestMapping({ "/gotoMergeUser" })
	public ModelAndView gotoMergeUser(
			ModelAndView model,
			@RequestParam(value = "thispage", required = false, defaultValue = "0") Integer thispage,
			UserAccount user,
			@RequestParam(value = "pagesize", required = false, defaultValue = "6") Integer pagesize){	
			PageInfo<UserAccount> page = userAccountService.listNotMergeUsers(thispage, pagesize, user);
			model.addObject(R.SEARCHMODEL, user);
			model.addObject(R.PAGE, page);
			model.setViewName("/user/listNotMergeUsers");
			return model;
	}

	@RequestMapping({ "/mergeUser" })
	@ResponseBody
	public Object mergeUser(ModelAndView model,
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "userIds", required = true) String userIds) {
			UserAccount user = this.userAccountService.selectByKey(userId);
			String[] userIdArray = userIds.trim().split(",");
			String uuid = null;
			if (user.getMergeUuid() == null || user.getMergeUuid().equals("")) {
				uuid = UUIDUtil.getUUID();
				user.setMergeUuid(uuid);
			} else {
				uuid = user.getMergeUuid();
			}
			for (int i = 0; i < userIdArray.length; i++) {
				String infoCode = userIdArray[i];
				UserAccount mergeUser =  this.userAccountService.selectByKey(infoCode);
				mergeUser.setMergeUuid(uuid);
				this.userAccountService.updateAll(mergeUser);
			}
			this.userAccountService.updateAll(user);
			return ResponseUtil.success(); 
	}

	@RequestMapping({ "/deleteMerge" })
	@ResponseBody
	public Object deleteMerge(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userid", required = false) String userid)
			{
		
			if (userid != null) {
				String[] rid = userid.split(",");
				for (String str : rid) {
					UserAccount findOne = this.userAccountService.selectByKey(str);
					findOne.setMergeUuid(null);
					this.userAccountService.updateAll(findOne);
				}
			}
			return ResponseUtil.success();
			
	}
	@RequestMapping("/resetPass")
	@ResponseBody
	public Object resetPass(String userId,String newp){
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		UserAccount user = this.userAccountService.selectByKey(userId);
		sha.setEncodeHashAsBase64(false);
		String password = newp;
		user.setPassword(sha.encodePassword(password, null));
		String encryptPassword="";
		try {
			encryptPassword = SimpleCrypto.encrypt(seed,
					password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setSynpassword(encryptPassword);
		this.userAccountService.updateAll(user);
		return ResponseUtil.success();
	}
	
	@RequestMapping("/user/getRole")
	@ResponseBody
	public Object rolePage(ModelAndView model,String userId){
		//获取用户的权限
		List<String> list = userRoleService.selectRolesByUserId(userId);
//		model.addObject("roles", list);
//		model.setViewName("/user/rolePage");
//		return model;
		return list;
	}
	@RequestMapping("/user/setRole")
	@ResponseBody
	public Object setRole(String userId,String isAdmin){
		
		// 设置角色
		if(isAdmin.equals("true")){
			try{
			this.userRoleService.insertUserAccountRole(userId,this.adminId);
			}catch(Exception e){}
		}else{
			this.userRoleService.deleteUserAccountRole(userId,this.adminId);
		}
		return ResponseUtil.success();
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
