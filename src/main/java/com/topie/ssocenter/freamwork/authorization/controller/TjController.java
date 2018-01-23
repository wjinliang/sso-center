package com.topie.ssocenter.freamwork.authorization.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.ExcelExportUtils;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.TjfxService;
import com.topie.ssocenter.freamwork.authorization.utils.R;

@Controller
public class TjController {

	@Autowired
	private TjfxService tjfxService;
	@Autowired
	private ApplicationInfoService appService;
	
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
		PageInfo<ApplicationInfo> page = appService.findApplicationInfoList(1, 100, new ApplicationInfo());
		model.addObject("appPage", page);
		if(StringUtils.isEmpty(app.getAppName())){
			app.setAppName(page.getList().get(0).getAppName());
		}
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
		PageInfo<ApplicationInfo> page = appService.findApplicationInfoList(1, 100, new ApplicationInfo());
		model.addObject("appPage", page);
		if(StringUtils.isEmpty(app.getAppName())){
			app.setAppName(page.getList().get(0).getAppName());
		}
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
		PageInfo<ApplicationInfo> page = appService.findApplicationInfoList(1, 100, new ApplicationInfo());
		model.addObject("appPage", page);
		if(StringUtils.isEmpty(app.getAppName())){
			app.setAppName(page.getList().get(0).getAppName());
		}
		model.addObject(R.SEARCHMODEL, app);
		List<Map> list = tjfxService.getUserDelCountForDivision(app);
		model.addObject(R.PAGE, list);
		model.setViewName("/tjfx/qhuserdl");
		return model;
	}
	@RequestMapping("/tjyh/download")
	public void download(HttpServletRequest request,HttpServletResponse response,String type,ApplicationInfo
			app){
		List<Map> list = new ArrayList<Map>();
		String name ="";
		String systemId= app.getAppName();
		if(type.equals("1")){
			list = this.tjfxService.getUserCountForDivision(app);
			name = "各个区划用户数量统计";
		}
		if(type.equals("2")){
			list = this.tjfxService.getUserLonginCountForDivision(app);
			name = "各个区划用户登录次数统计";
		}
		if(type.equals("3")){
			list = this.tjfxService.getUserDelCountForDivision(app);
			name = "各个区划用户删除数量统计";
		}
		String fileName = systemId+name;
		OutputStream fOut = null;
		try {
		response.setContentType("application/vnd.ms-excel;charset=ISO8859_1"); 
		response.setHeader("content-disposition", "attachment;filename="+new String(fileName.getBytes(), "iso-8859-1")+".xls");  
			fOut = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//List<UserAccount> list = this.userAccountService.listUserAccount(orgId, orgId, 0, 1000);
		String[] fields = {"divisionName","shengcount","shicount","xiancount","xiangcount","cuncount"};
		String[] names = {"区划","省级","市级","县级","乡镇级","村级"};
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
