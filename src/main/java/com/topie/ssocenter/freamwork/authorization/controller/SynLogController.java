package com.topie.ssocenter.freamwork.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.model.SynLog;
import com.topie.ssocenter.freamwork.authorization.service.SynLogService;
import com.topie.ssocenter.freamwork.authorization.utils.R;

@Controller
@RequestMapping({ "/syn" })
public class SynLogController {

	 @Autowired
	 private SynLogService synLogService;
	 /**
	  * 系统列表
	  * @param model
	  * @param thispage
	  * @param pagesize
	  * @return
	  */
		@RequestMapping({ "/listSynLog" })
		public ModelAndView listApp(
				ModelAndView model,
				@RequestParam(value = "thispage", required = false) Integer thispage,
				@RequestParam(value = "pagesize", required = false) Integer pagesize,
				SynLog synLog) {

				if (pagesize == null) {
					pagesize = Integer.valueOf(10);
				}
				if (thispage == null) {
					thispage = Integer.valueOf(0);
				}
				PageInfo<SynLog> page = synLogService.findLogList(thispage, pagesize, synLog);
				model.addObject(R.PAGE, page);
				model.addObject(R.SEARCHMODEL, synLog);
				model.setViewName("/synlog/list");
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
						this.synLogService.delete(str);
					}
				}
				return ResponseUtil.success();
		}

		
	
}
