package com.topie.ssocenter.freamwork.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.model.Log;
import com.topie.ssocenter.freamwork.authorization.service.LogService;
import com.topie.ssocenter.freamwork.authorization.utils.R;

@Controller
@RequestMapping({ "/log" })
public class LogController {

	 @Autowired
	 private LogService logService;
	 /**
	  * 系统列表
	  * @param model
	  * @param thispage
	  * @param pagesize
	  * @return
	  */
		@RequestMapping({ "/list" })
		public ModelAndView listApp(
				ModelAndView model,
				@RequestParam(value = "thispage", required = false) Integer thispage,
				@RequestParam(value = "pagesize", required = false) Integer pagesize,
				Log log) {

				if (pagesize == null) {
					pagesize = Integer.valueOf(10);
				}
				if (thispage == null) {
					thispage = Integer.valueOf(0);
				}
				PageInfo<Log> page = logService.findLogList(thispage, pagesize, log);
				model.addObject(R.PAGE, page);
				model.addObject(R.SEARCHMODEL, log);
				model.setViewName("/log/list");
				return model;
		}



		@RequestMapping({ "/delete" })
		@ResponseBody
		public Object delete(
				@RequestParam(value = "id", required = false) String id)
				{
				if (id != null) {
					String[] rid = id.split(",");
					for (String str : rid) {
						this.logService.delete(Long.valueOf(str));
					}
				}
				return ResponseUtil.success();
		}

		
	
}
