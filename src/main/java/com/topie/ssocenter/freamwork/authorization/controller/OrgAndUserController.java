package com.topie.ssocenter.freamwork.authorization.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.R;


@Controller
@RequestMapping({ "/orgAndUser" })
public class OrgAndUserController {
	@Autowired
	OrgService orgService;
	@Resource
	UserAccountService userAccountService;
	@Autowired
	DivisionService divisionService;
	
	@Value("${zhuisu.systemId}")
	String ZSsystemId;

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
			//获取当前用户的区划
			String divisionid = org.getDivisionId();
			List<Division> division = this.orgService.selectCurrentDivisionTree(divisionid);
			List ml = new ArrayList();
			String ids = "";
			for (Division divi : division) {
				Map m = new HashMap();
				ids+=divi.getId()+",";
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
			//获取当前用户所在区划 加载出当前区划下的机构
			PageInfo<Org> page = this.orgService.selectCurrentDivisionOrgPage(thispage,pagesize,org);
			model.addObject(R.PAGE, page);
			model.setViewName("/org/orgList");
			return model;
	}

	/*@RequestMapping("/form/{mode}")
	public ModelAndView form(
			HttpServletRequest request,
			ModelAndView model,
			@PathVariable String mode,
			Org org) {
		try {
			Org o = new Org();
			if (mode != null && !mode.equals("new")) {//编辑
				if (orgid != null) {
					o = orgService.findOne(Long.valueOf(orgid));
					model.addObject("org", o);
					if (flag.equals("1")) {
						if (o.getParent() != null) {
							//System.out.println(o.getParent().getId());
							model.addObject("parentid", o.getParent().getId());
						}
					}
					if (mode.equals("view")) {
						model.setViewName("/pages/admin/org/view");
						return Model(model);
					}
				}
			} else {
				if (orgid != null) {
					
					o.setSeq(commonDAO
							.count("select count(*) from Org o where o.parent.id="
									+ orgid) + 1);
					model.addObject("parentid", orgid);
				} else {
					Division d = UserAccountUtil.getInstance().getCurrentUserAccountOrgDivision();
					
						if(!d.getId().equals("1")){
							model.setViewName("403");
							model.addObject("msg","您没有这个权限,请联系上级管理员!");
							return Model(model);
						}
					o.setSeq(commonDAO
							.count("select count(*) from Org o where o.parent is null and o.division.id='"
									+ divisionid + "'") + 1);
				}
				o.setCode(getOrgCode(divisionid,null));
				o.setDivision(commonDAO.findOne(Division.class,divisionid));
				model.addObject("org", o);
			}
			if (flag.equals("0")) {
				String hql = " from Division d where d.id = '" + divisionid
						+ "' or d.parent.id = '" + divisionid
						+ "' order by d.code";
				List<Division> divisionList = this.commonDAO.find(hql);
				model.addObject("divisionList", divisionList);
			} else {
				String hql = " from Division d where d.id = '" + divisionid
						+ "'  or d.parent.id = '" + divisionid
						+ "' order by d.code";
				List<Division> divisionList = this.commonDAO.find(hql);
				model.addObject("divisionList", divisionList);
			}
			if (orgid != null && !orgid.equals("")) {
				if (o.getDivision() != null) {
					model.addObject("divisionid", o.getDivision().getId());
				}
			} else {
				model.addObject("divisionid", divisionid);
			}
			model.addObject("flag", flag);
			model.addObject("mode", mode);
			model.setViewName("/pages/admin/orgUser/form");
			return Model(model);
		} catch (Exception e) {
			e.printStackTrace();
			return Error(e);
		}
	}

	private String  getOrgCodebak(String divisionid ,String orgid){
		String orgSumHql = "select max(o.code) from Org o where o.division.id='"
				+ divisionid + "'";
		String code = this.commonDAO.max(orgSumHql);
		Division division = this.commonDAO.findOne(Division.class,
				divisionid);
		Org org = new Org();
		if (orgid != null && !orgid.equals("")) {
			org = this.commonDAO.findOne(Org.class, Long.valueOf(orgid));
		}
		// TODO null
		String orgLshCode = org.getCode();
		String orgLsh = division.getCode() + "001";
		if (orgLshCode != null && !orgLshCode.equals("")
				&& orgLshCode.startsWith(division.getCode())) {
			//System.out.println("-------");
			orgLsh = orgLshCode;
		} else {
			if (code != null && !code.equals("")) {
				Long valueOf = Long.valueOf(code);
				orgLsh = String.valueOf(valueOf + 1);
			}
		}
		return orgLsh;
	}
	
	private String  getOrgCode(String divisionid ,String orgid){
		Division division = this.commonDAO.findOne(Division.class,
				divisionid);
		String divisionCode = division.getCode();
		if(divisionCode.length()>6){
			divisionCode = divisionCode.substring(0,6);
		}
		String orgSumHql = "select max(o.code) from Org o where o.code like '"
				+ divisionCode + "%'";
		
		String code = this.commonDAO.max(orgSumHql);
		Org org = new Org();
		if (orgid != null && !orgid.equals("")) {
			org = this.commonDAO.findOne(Org.class, Long.valueOf(orgid));
		}
		// TODO null
		String orgLshCode = org.getCode();
		String orgLsh = divisionCode + "001";
		if (orgLshCode != null && !orgLshCode.equals("")
				&& orgLshCode.startsWith(division.getCode())) {
			//System.out.println("-------");
			orgLsh = orgLshCode;
		} else {
			if (code != null && !code.equals("")) {
				Long valueOf = Long.valueOf(code);
				orgLsh = String.valueOf(valueOf + 1);
			}
		}
		return orgLsh;
	}
	@RequestMapping({ "/chooseDivision" })
	public ModelAndView chooseDivision(ModelAndView model,
			@RequestParam(value = "orgid", required = false) String orgid) {
		try {
			List ml = new ArrayList();
			UserAccount currentUserAccount = UserAccountUtil.getInstance()
					.getCurrentUserAccount();
			Org org = currentUserAccount.getOrg();
			List<Division> divisionList = new ArrayList<Division>();
			if (org != null) {
				Division division = org.getDivision();
				if (division != null) {
					divisionList = this.orgAndUserService
							.getSonLevelDivisionList(division.getId());
					divisionList.add(division);
				} else {
					divisionList = this.orgAndUserService.findSheng();
				}
			} else {
				divisionList = this.orgAndUserService.findSheng();
			}
			for (Division division : divisionList) {
				Map m = new HashMap();
				m.put("id", division.getId());
				m.put("name", division.getName());
				if (division.getParent() != null)
					m.put("pId", division.getParent().getId());
				else {
					m.put("pId", "");
				}
				String hql = " from Division d where d.parent.id ='"
						+ division.getId() + "'";
				List<Division> sonList = this.commonDAO.find(hql);
				if (sonList != null && sonList.size() > 0) {
					m.put("isParent", "true");
				}
				if (division.equals(org.getDivision())) {
					m.put("open", Boolean.valueOf(true));
				}
				ml.add(m);
			}
			JSONArray json = JSONArray.fromObject(ml);
			model.addObject("orgStr", json.toString());
			model.addObject("orgid", orgid);
			model.setViewName("/pages/admin/orgUser/chooseDivision");
			return Model(model);
		} catch (Exception e) {
			e.printStackTrace();
			return Error(e);
		}
	}

	@RequestMapping("/save")
	public ModelAndView save(
			ModelAndView model,
			Org org) {
		
		return model;
	}


	@RequestMapping({ "/listUsers" })
	public ModelAndView listUsers(
			ModelAndView model,
			UserAccount user,
			@RequestParam(value = "thispage", required = false) Integer thispage,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {
		model.addObject(R.SEACHMODEL,user);
		
		return model;
	}

	@RequestMapping({ "/listMergeUsers" })
	public ModelAndView listMergeUsers(
			ModelAndView model,
			@RequestParam(value = "thispage", required = false) Integer thispage,
			@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {
			if (pagesize == null) {
				pagesize = Integer.valueOf(10);
			}
			if (thispage == null) {
				thispage = Integer.valueOf(0);
			}
			List<UserAccount> userList = this.orgAndUserService.listMergeUsers(
					userid, thispage, pagesize);
			model.addObject("userid", userid);
			model.addObject("userList", userList);
			model.setViewName("/pages/admin/useraccount/listMergeUsers");
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
			List<UserAccount> userList = this.orgAndUserService.listUsers(null,
					systemId, name, thispage, pagesize);
			List<Order> orders = new ArrayList<Order>();
			orders.add(new Order(Direction.ASC, "seq"));
			List<ApplicationInfo> appList = this.commonDAO
					.findAll(ApplicationInfo.class,orders);
			model.addObject("appList", appList);
			model.addObject("userList", userList);
			model.addObject("userid", userid);
			model.setViewName("/pages/admin/useraccount/user");
			return model;
	}

	@RequestMapping({ "/loadSonOrgAndUser" })
	public void loadSonOrgAndUser(ModelAndView model,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = "orgid", required = true) String orgid,
			@RequestParam(value = "userid", required = true) String userid) {
		try {
			httpServletResponse.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = httpServletResponse.getWriter();
			String orgHql = " from Org o";
			if (orgid != null && !orgid.equals("")) {
				orgHql += "  where o.parent.id =" + orgid;
			} else {
				orgHql += "  where o.parent.id is null";
			}
			List<Org> orgList = this.commonDAO.find(orgHql);
			List ml = new ArrayList();
			for (int i = 0; i < orgList.size(); i++) {
				Map m = new HashMap();
				Org org = orgList.get(i);
				m.put("id", org.getId());
				m.put("name", org.getName());
				if (org.getParent() != null) {
					m.put("pId", org.getParent().getId());
				} else {
					m.put("pId", "");
				}
				m.put("isParent", "true");
				ml.add(m);
			}
			UserAccount userAccount = this.commonDAO.findOne(UserAccount.class,
					userid);
			String userSql = " from UserAccount u where u.org.id =" + orgid;
			List<UserAccount> userList = this.commonDAO.find(userSql);
			for (int j = 0; j < userList.size(); j++) {
				Map u = new HashMap();
				UserAccount user = userList.get(j);
				String userId = user.getCode();
				String userName = user.getName();
				u.put("id", userId);
				u.put("pId", "");
				u.put("name", userName);
				if (userAccount.getMergeUuid() != null
						&& user.getMergeUuid() != null) {
					if (userAccount.getMergeUuid().equals(user.getMergeUuid())) {
						u.put("nocheck", "true");
					} else {
						u.put("nocheck", "false");
					}
				} else {
					u.put("nocheck", "false");
				}
				if (userAccount.getCode().equals(user.getCode())) {
					u.put("nocheck", "true");
				}
				ml.add(u);
			}
			JSONArray jsonArray = JSONArray.fromObject(ml);
			writer.write(jsonArray.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/mergeUser" })
	public ModelAndView mergeUser(ModelAndView model,
			@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "userIds", required = true) String userIds) {
		try {
			UserAccount currentUser = UserAccountUtil.getInstance()
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
			return NewRedirect(model, "/orgAndUser/listMergeUsers");
		} catch (Exception e) {
			e.printStackTrace();
			return Error(e);
		}
	}

	@RequestMapping({ "/deleteMerge" })
	public void deleteMerge(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userid", required = false) String userid)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
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
		}
	}

	@RequestMapping({ "/getOrgLsh" })
	public void getOrgLsh(
			ModelAndView model,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = "divisionid", required = true) String divisionid,
			@RequestParam(value = "orgid", required = true) String orgid) {
		try {
			httpServletResponse.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = httpServletResponse.getWriter();
			writer.write(getOrgCode(divisionid,orgid));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/excelExport")
	public void excelExport(HttpServletRequest request, HttpServletResponse response,String orgId,
			@RequestParam(value = "tj", required = false) String name,
			@RequestParam(value = "xt", required = false) String systemId)
	{
		String fileName = "";
		Org o = this.orgService.selectByKey(Long.valueOf(orgId));
		try {
			//fileName = java.net.URLEncoder.encode("报名统计表", "ISO8859_1");
			fileName=new String((o.getName()+"导出用户").getBytes(), "ISO8859_1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		response.setContentType("application/vnd.ms-excel;charset=ISO8859_1"); 
		response.setHeader("content-disposition", "attachment;filename="+fileName+".xls");  
		OutputStream fOut = null;
		try {
			fOut = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String whereSql = "";
		Map argMap = new HashMap();
		List<UserAccount> list = this.orgService.findUserAccount(
					orgId, systemId, name, 0, 1000);
		String[] fields = {"loginname","name","gender","mobile","bizPhoneNo","schoolAge","address","title","speciality","email","fano","userType","systemId"};
		String[] names = {"登录名","姓名","性别"			,"手机","固话","学历","通讯地址",			"职称","专业","邮箱","传真","用户类型","系统标识"};
		Workbook workbook;
		try {
			workbook = ExcelExportUtils.getInstance().inExcel(list, fields, names);
			workbook.write(fOut);  
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				fOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
}
