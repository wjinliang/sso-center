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

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSONArray;
import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;


@Controller
@RequestMapping("/division")
public class DivisionController {
	@Resource
	DivisionService divisionService;
//	@Resource
//	divisionAndUserService divisionAndUserService;
	/**
	 * 获取所有区划组织
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView model, HttpServletRequest request) {
			List ml = new ArrayList();
			List<Division> divisionList = new ArrayList<Division>();
				divisionList = this.divisionService.findSheng();
			for (Division division : divisionList) {
				Map m = new HashMap();
				m.put("id", division.getId());
				m.put("name", division.getName());
				m.put("pId",division.getParentId()==null?"":division.getParentId());
				List<Division> sonList = divisionService.findByPid(division.getId());
				if (sonList != null && sonList.size() > 0) {
					m.put("isParent", "true");
				}
				ml.add(m);
			}
			JSONArray arr = new JSONArray(ml);
			model.addObject("divisionStr", arr.toJSONString());
			model.setViewName("/division/divisionList");
			return model;
	}
	@RequestMapping({ "/loadSonDivision" })
	@ResponseBody
	public Object loadSonDivision(
			ModelAndView model,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = "divisionid", required = false) String divisionid) {
			List ml = new ArrayList();
				List<Division> divisionList = this.divisionService
						.findByPid(divisionid);
				for (Division d : divisionList) {
					Map u = new HashMap();
					String orgId = d.getId();
					String orgName = d.getName();
					String parentId = d.getParentId()==null?"":d.getParentId();

					u.put("id", orgId);
					u.put("pId", parentId);
					u.put("name", orgName);
					u.put("level", d.getLevel());
					List<Division> sonList =this.divisionService.findByPid(orgId);
					if (sonList != null && sonList.size() > 0) {
						u.put("isParent", "true");
					}
					ml.add(u);
				}
				return ml;
	}
	
	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/load")
	@ResponseBody
	public Object load(HttpServletResponse response) {
			List ml = new ArrayList();
			List<Division> olist = null;//commonDAO.findAll(Division.class, orders);
			for (Division division : olist) {
				Map m = new HashMap();
				m.put("id", division.getId());
				m.put("name", division.getName());
				if (division.getParentId()!= null) {
					m.put("pId", division.getParentId());
				} else {
					m.put("pId", 0);
				}
				ml.add(m);
			}
			return ml;
	}*/

	/*@RequestMapping("/form/{mode}")
	public ModelAndView form(HttpServletRequest request, ModelAndView model,
			@PathVariable String mode,
			@RequestParam(value = "divisionId", required = false) String divisionId) {
			Division o = new Division();
			if (mode != null && !mode.equals("new")) {
				if (divisionId != null) {
					o = divisionService.selectByKey(divisionId); 
					model.addObject("division", o);
					if(mode.equals("view")){
						model.setViewName("/division/view");
						return model;
					}
				}
			} else {
				o.setSeq(divisionService
						.selectNextSeqByPid(divisionId));
				Division division = this.divisionService.selectByKey( divisionId);
				if(division!=null && division.getLevel()!=null){
					o.setLevel(division.getLevel()+1);
				}else{
					o.setLevel(0);
				}
				model.addObject("division", o);
			}
			model.setViewName("/division/form");
			return model;
	}
*/
	@RequestMapping("/save")
	public ModelAndView save(ModelAndView model, Division division) {
			Division o = new Division();
			if (division.getId() != null && !division.getId().equals("")) {//更新
				divisionService.updateNotNull(division);
				model.setViewName("redirect:list");
			} else {//新增
				String id = UUIDUtil.getUUID();
				division.setCreatetime(DmDateUtil.Current());
				division.setId(id);
				o = this.divisionService.selectByKey(division.getParentId());
				if(o!=null)
					division.setLevel(o.getLevel()+1);
				divisionService.save(division);
				model.setViewName("redirect:list");
			}
			return model;
	}
	@RequestMapping("/loadOne")
	@ResponseBody
	public Object loadOne(@RequestParam(value = "divisionId", required = true) String divisionId) {
		Division o = divisionService.selectByKey(divisionId); 
			return ResponseUtil.success(o);
	}
	@RequestMapping("/checkCode")
	@ResponseBody
	public Object checkCode(Division d) {
		Example ex = new Example(Division.class);
		ex.createCriteria().andEqualTo("code", d.getCode());
			List<Division> list = divisionService.selectByExample(ex);
			for(Division tmp:list){
				if(tmp.getId().equals(d.getId())){
					continue;
				}
				return false;
			}
			return true;
	}

	@RequestMapping("/setseq")
	@ResponseBody
	public Object setseq(
			HttpServletResponse response,
			@RequestParam(value = "currentid", required = false) String currentid,
			@RequestParam(value = "targetid", required = false) String targetid,
			@RequestParam(value = "moveType", required = false) String moveType,
			@RequestParam(value = "moveMode", required = false) String moveMode)
			{
		
		this.divisionService.seqList( currentid, targetid,  moveType, moveMode);
		return ResponseUtil.success();
			}
			

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "divisionid", required = false) String divisionid) {
		this.divisionService.delete(divisionid);
		return ResponseUtil.success();
	}
}
