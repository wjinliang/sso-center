package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.service.TjfxService;
import com.topie.ssocenter.freamwork.authorization.utils.R;

@Controller
public class TjController {

	@Autowired
	private TjfxService tjfxService;
	
	@RequestMapping("/tjfx/listApp")
	public ModelAndView tjfx(ModelAndView model,ApplicationInfo
			app){
		model.addObject(R.SEARCHMODEL, app);
		List<Map> list = tjfxService.getUserOrgCountForApp(app);
		model.addObject(R.PAGE, list);
		model.setViewName("/tjfx/listApp");
		return model;
	}
	/**
	 * 各个区划 用户统计
	 * @param model
	 * @param app
	 * @return
	 */
	@RequestMapping("/tjyh/qhyh")
	public ModelAndView qhyh(ModelAndView model,ApplicationInfo
			app){
		model.addObject(R.SEARCHMODEL, app);
		List<Map> list = tjfxService.getUserCountForDivision(app);
		model.addObject(R.PAGE, list);
		model.setViewName("/tjfx/qhyhsl");
		return model;
	}
	
	/**
	 * 各个区划 用户登录次数统计
	 * @param model
	 * @param app
	 * @return
	 */
	@RequestMapping("/tjyh/qhyhdl")
	public ModelAndView qhyhdl(ModelAndView model,ApplicationInfo
			app){
		model.addObject(R.SEARCHMODEL, app);
		List<Map> list = tjfxService.getUserLonginCountForDivision(app);
		model.addObject(R.PAGE, list);
		model.setViewName("/tjfx/qhuserlogin");
		return model;
	}
	
	/**
	 * 各个区划 用户删除次数统计
	 * @param model
	 * @param app
	 * @return
	 */
	@RequestMapping("/tjyh/qhscyh")
	public ModelAndView qhscyh(ModelAndView model,ApplicationInfo
			app){
		model.addObject(R.SEARCHMODEL, app);
		List<Map> list = tjfxService.getUserDelCountForDivision(app);
		model.addObject(R.PAGE, list);
		model.setViewName("/tjfx/qhuserdl");
		return model;
	}
	
}
