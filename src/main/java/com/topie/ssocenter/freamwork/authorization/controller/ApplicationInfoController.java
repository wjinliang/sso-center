package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.utils.R;

@Controller
@RequestMapping({ "/app" })
public class ApplicationInfoController {

	 @Autowired
	 private ApplicationInfoService appService;
	 /**
	  * 系统注册
	  * @param model
	  * @param thispage
	  * @param pagesize
	  * @return
	  */
		@RequestMapping({ "/listApp" })
		public ModelAndView listApp(
				ModelAndView model,
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
				model.setViewName("/app/appList");
				return model;
		}

		/*@RequestMapping({ "/listSynsApp" })
		public void listSynsApp(
				ModelAndView model,
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
				model.setViewName("/app/userAppList");
		}*/

		@RequestMapping({ "/form/{mode}" })
		public ModelAndView gotoCreateAppPage(ModelAndView model,
				@PathVariable String mode,
				@RequestParam(value = "appid", required = false) String appid) {
			if ((mode != null) && (!mode.equals("new"))) {
				ApplicationInfo appInfo = appService.selectByKey(appid);
				model.addObject("appInfo", appInfo);
				model.setViewName("/app/viewAppPage");
			} else {
				model.setViewName("/app/createAppPage");
			}
			return model;
		}

		@RequestMapping({ "/saveApp" })
		public ModelAndView saveApp(ModelAndView model, ApplicationInfo app) {
			String uuid = UUIDUtil.getUUID();
			app.setId(uuid);
			this.appService.save(app);
			model.setViewName("redirect:listApp");
			return model;
		}

		@RequestMapping({ "/updateApp" })
		public ModelAndView updateApp(ModelAndView model, ApplicationInfo app) {
			//ApplicationInfo one = this.appService.selectByKey(app.getId());
			this.appService.updateNotNull(app);
			model.setViewName("redirect:listApp");
			return model;
		}

		@RequestMapping({ "/checkunique" })
		@ResponseBody
		public Object checkunique(
				@RequestParam(value = "param", required = true) String param,
				@RequestParam(value = "name", required = true) String name,
				@RequestParam(value = "id", required = false) String id) {
			
				Example example =new Example(ApplicationInfo.class);
				example.createCriteria().andEqualTo(name,param);
				List<ApplicationInfo> list = this.appService.selectByExample(example);
				if (id != null && !id.equals("")) {//更新
					if (list.size() > 1) {
						return false;
					} else if (list.size() == 1) {
						ApplicationInfo applicationInfo = list.get(0);
						if (applicationInfo.getId().equals(id)) {//是自己
							return true;
						} else {
							return false;
						}
					} else {
						return true;
					}
				} else {//新增
					if (list.size() > 0) {
						return false;
					} else {
						return true;
					}
				}
		}

		@RequestMapping({ "/delete" })
		@ResponseBody
		public Object delete(
				@RequestParam(value = "appid", required = false) String appid)
				{
				if (appid != null) {
					String[] rid = appid.split(",");
					for (String str : rid) {
						this.appService.delete(str);
					}
				}
				return ResponseUtil.success();
		}

		@RequestMapping({ "/startApp" })
		@ResponseBody
		public Object startApp(
				@RequestParam(value = "appid", required = true) String appid)
				{
				if (appid != null) {
					ApplicationInfo findOne = this.appService.selectByKey(appid);
					findOne.setStatus("1");
					this.appService.updateNotNull(findOne);
				}
				return ResponseUtil.success();
		}

		@RequestMapping({ "/stopApp" })
		@ResponseBody
		public Object stopApp(
				@RequestParam(value = "appid", required = true) String appid)
				{
				if (appid != null) {
					ApplicationInfo findOne = this.appService.selectByKey(appid);
					findOne.setStatus("0");
					this.appService.updateNotNull(findOne);
				}
				return ResponseUtil.success();
		}

		@RequestMapping({ "/appFile" })
		public ModelAndView appFile(
				ModelAndView model,
				@RequestParam(value = "thispage", required = false) Integer thispage,
				@RequestParam(value = "pagesize", required = false) Integer pagesize,
				ApplicationInfo app) {
				model.setViewName("/app/uploadfile");
				PageInfo<ApplicationInfo> list = this.appService.selectCurrentUserSynApps();
				model.addObject(R.PAGE, list);
				return model;
		}
		@RequestMapping({ "/bindFile" })
		@ResponseBody
		public Object bindFile(String appId,String fileId) {
			appService.insertAppFile(appId,fileId);
			return ResponseUtil.success();
		}
		
		/**
		 * 单点页面
		 * @param model
		 * @param thispage
		 * @param pagesize
		 * @return
		 */
		/*@RequestMapping({ "/listSynApp" })
		public ModelAndView listSynApp(
				ModelAndView model,
				@RequestParam(value = "thispage", required = false) Integer thispage,
				@RequestParam(value = "pagesize", required = false) Integer pagesize) {
			UserAccount currentUserAccount = s.getInstance()
					.getCurrentUserAccount();
			if (pagesize == null) {
				pagesize = Integer.valueOf(10);
			}
			if (thispage == null) {
				thispage = Integer.valueOf(0);
			}
			Long totalcount = this.appService.getSynUserApp(currentUserAccount);
			// System.out.println(totalcount);
			if ((thispage.intValue() * pagesize.intValue() >= totalcount
					.longValue()) && (totalcount.longValue() > 0L)) {
				thispage = Integer.valueOf(thispage.intValue() - 1);
			}
			List<ApplicationInfo> synAppList = this.appService.listSynUserApp(
					currentUserAccount, thispage, pagesize);
			model.addObject("synAppList", synAppList);
			model.setViewName("/back/index");
			return Model(model, thispage, pagesize, totalcount);
		}*/

}
