package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.DivisionSystem;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.DivisionSystemService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.utils.R;


@Controller
@RequestMapping("/divisionSystem")
public class DivisionSystemController {
	@Resource
	DivisionSystemService divisionSystemService;
	@Resource
	DivisionService divisionService;
	@Resource
	OrgService orgService;
	@Resource
	ApplicationInfoService applicationInfoService;
	/**
	 * 获取所有区划组织
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView model,
			@RequestParam(value = "thispage", required = false,defaultValue="1") Integer thispage,
			@RequestParam(value = "pagesize", required = false,defaultValue="20") Integer pagesize,
			DivisionSystem divisionSystem,
			HttpServletRequest request) {
		setCurrentDivisionList(divisionSystem,model);

		PageInfo<ApplicationInfo> apps = applicationInfoService.findApplicationInfoList(1, 100, new ApplicationInfo());
		model.addObject("appPage", apps);
		PageInfo<DivisionSystem> page = divisionSystemService.findPage(thispage, pagesize, divisionSystem);
		model.addObject(R.PAGE, page);
		model.addObject(R.SEARCHMODEL, divisionSystem);
		model.setViewName("/divisionSystem/list");
		return model;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setCurrentDivisionList(DivisionSystem org, ModelAndView model) {
		//获取当前用户的区划
		String divisionid = org.getDivisionId();
		List<Division> division = this.orgService.selectCurrentDivisionTree(divisionid);
		List ml = new ArrayList();
		//String ids = "";
		for (Division divi : division) {
			Map m = new HashMap();
			//ids+=divi.getId()+",";
			m.put("id", divi.getId());
			m.put("name", divi.getName());
			m.put("code", divi.getCode());
			m.put("pId",divi.getParentId()==null?"":divi.getParentId());
			List<Division> sonList = divisionService.findByPid(divi.getId());
			if (sonList != null && sonList.size() > 0) {
				m.put("isParent", "true");
			}
			ml.add(m);
		}
		JSONArray arr = new JSONArray(ml);
		model.addObject("divisionStr", arr.toJSONString());
	}
	
	@RequestMapping("/save")
	public ModelAndView save(ModelAndView model,
			DivisionSystem divisionSystem) {
			if (divisionSystem.getId() != null && !divisionSystem.getId().equals("")) {//更新
				divisionSystemService.updateNotNull(divisionSystem);
				model.setViewName("redirect:list?divisionId="+divisionSystem.getDivisionId()+"&systemId="+divisionSystem.getSystemId());
			} else {//新增
				String id = UUIDUtil.getUUID();
				divisionSystem.setCreateTime(DmDateUtil.Current());
				divisionSystem.setId(id);
				divisionSystemService.save(divisionSystem);
				model.setViewName("redirect:list?divisionId="+divisionSystem.getDivisionId()+"&systemId="+divisionSystem.getSystemId());
			}
			return model;
	}
	@RequestMapping("/loadOne")
	@ResponseBody
	public Object loadOne(@RequestParam(value = "divisionSystemId", required = true) String divisionSystemId) {
		DivisionSystem o = divisionSystemService.selectByKey(divisionSystemId); 
			return ResponseUtil.success(o);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "divisionSystemId", required = true) String divisionSystemId) {
		this.divisionSystemService.delete(divisionSystemId);
		return ResponseUtil.success();
	}
}
