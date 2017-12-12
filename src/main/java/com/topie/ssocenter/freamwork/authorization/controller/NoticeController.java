package com.topie.ssocenter.freamwork.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.model.Notice;
import com.topie.ssocenter.freamwork.authorization.service.NoticeService;
import com.topie.ssocenter.freamwork.authorization.utils.R;

@Controller
@RequestMapping({ "/notice" })
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	@RequestMapping({ "/admin/list" })
	public ModelAndView alist(
			ModelAndView model,
			@RequestParam(value = "thispage", required = false) Integer thispage,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "systemId", required = false) String systemId,
			Notice notice) {

		if (pagesize == null) {
			pagesize = Integer.valueOf(10);
		}
		if (thispage == null) {
			thispage = Integer.valueOf(0);
		}
		PageInfo<Notice> page = noticeService.findList(thispage, pagesize,
				notice, systemId);
		model.addObject(R.PAGE, page);
		model.addObject(R.SEARCHMODEL, notice);
		model.addObject("systemId", systemId);
		model.setViewName("/notice/admin/list");
		return model;
	}@RequestMapping({ "/list" })
	public ModelAndView list(
			ModelAndView model,
			@RequestParam(value = "thispage", required = false) Integer thispage,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {

		if (pagesize == null) {
			pagesize = Integer.valueOf(10);
		}
		if (thispage == null) {
			thispage = Integer.valueOf(0);
		}
		PageInfo<Notice> page = noticeService.findCurrentUserNotice(thispage, pagesize);
		model.addObject(R.PAGE, page);
		model.setViewName("/notice/list");
		return model;
	}

	@RequestMapping({ "/admin/form/{mode}" })
	public ModelAndView form(ModelAndView model, @PathVariable String mode,
			@RequestParam(value = "noticeId", required = false) String noticeId) {
		if ((mode != null) && (!mode.equals("new"))) {
			Notice appInfo = noticeService.selectByKey(noticeId);
			model.addObject("notice", appInfo);
			model.addObject("apps", noticeService.selectNoticeApp(noticeId));
		}
		model.setViewName("/notice/admin/viewPage");
		return model;
	}

	@RequestMapping({ "/admin/save" })
	public ModelAndView saveApp(ModelAndView model, Notice notice, String apps) {
		if (StringUtil.isNotEmpty(notice.getId())) {
			this.noticeService.updateAll(notice,apps);
			model.setViewName("redirect:list");
			return model;
		}
		String uuid = UUIDUtil.getUUID();
		notice.setId(uuid);
		this.noticeService.save(notice,apps);
		model.setViewName("redirect:list");
		return model;
	}

	@RequestMapping({ "admin/delete" })
	@ResponseBody
	public Object delete(@RequestParam(value = "id", required = true) String id) {
		if (id != null) {
			String[] rid = id.split(",");
			for (String str : rid) {
				this.noticeService.delete(str);
			}
		}
		return ResponseUtil.success();
	}

	@RequestMapping({ "admin/stopApp" })
	@ResponseBody
	public Object stopApp(
			@RequestParam(value = "appid", required = true) String appid) {
		if (appid != null) {
			Notice findOne = this.noticeService.selectByKey(appid);
			this.noticeService.updateNotNull(findOne);
		}
		return ResponseUtil.success();
	}
}
