package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.topie.ssocenter.freamwork.authorization.model.FileEntity;
import com.topie.ssocenter.freamwork.authorization.model.Notice;
import com.topie.ssocenter.freamwork.authorization.service.FileEntityService;
import com.topie.ssocenter.freamwork.authorization.service.NoticeService;
import com.topie.ssocenter.freamwork.authorization.utils.R;

@Controller
@RequestMapping({ "/notice" })
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private FileEntityService fileService;

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
	}

	@RequestMapping({ "/{type}s","/{type}s_{num}" })
	public ModelAndView list(
			ModelAndView model,
			@RequestParam(value = "thispage", required = false) Integer thispage,
			@RequestParam(value = "num", required = false)@PathVariable(value = "num") Integer num,
			@RequestParam(value = "type", required = false)@PathVariable(value = "type") String type,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {

		if (pagesize == null) {
			pagesize = Integer.valueOf(10);
		}
		if (thispage == null) {
			thispage = Integer.valueOf(0);
		}
		if(num!=null){
			thispage = num;
		}
		PageInfo<Notice> page = noticeService.findCurrentUserNotice(thispage,
				pagesize);
		model.addObject(R.PAGE, page);
		model.setViewName("/notice/list");
		return model;
	}

	/*@RequestMapping({ "/info/{nid}" })
	public ModelAndView info(ModelAndView model, @PathVariable String nid) {
		Notice notice = noticeService.selectByKey(nid);
		model.addObject("notice", notice);
		List<FileEntity> files = new ArrayList<FileEntity>();
		String fileIds = notice.getFileIds();
		if(fileIds!=null){
		for (String id : fileIds.split(",")) {
			FileEntity f = fileService.selectByKey(id);
			if (f != null)
				files.add(f);
		}
		}
		model.addObject("files", files);
		model.setViewName("/notice/admin/viewPage");
		return model;
	}*/
	@RequestMapping({ "/{type}_{id}" })
	public ModelAndView content(ModelAndView model, @PathVariable String id, @PathVariable String type) {
		Notice notice = noticeService.selectByKey(id);
		model.addObject("notice", notice);
		List<FileEntity> files = new ArrayList<FileEntity>();
		String fileIds = notice.getFileIds();
		if(fileIds!=null){
			for (String fid : fileIds.split(",")) {
				FileEntity f = fileService.selectByKey(fid);
				if (f != null)
					files.add(f);
			}
		}
		model.addObject("files", files);
		model.addObject("type",type);
		model.setViewName("/notice/content");
		return model;
	}

	@RequestMapping({ "/admin/form/{mode}" })
	public ModelAndView form(ModelAndView model, @PathVariable String mode,
			@RequestParam(value = "noticeId", required = false) String noticeId) {
		if ((mode != null) && (!mode.equals("new"))) {
			Notice notice = noticeService.selectByKey(noticeId);
			model.addObject("notice", notice);
			model.addObject("apps", noticeService.selectNoticeApp(noticeId));
			List<FileEntity> files = new ArrayList<FileEntity>();
			String fileIds = notice.getFileIds();
			if(fileIds!=null){
				for (String id : fileIds.split(",")) {
					FileEntity f = fileService.selectByKey(id);
					if (f != null)
						files.add(f);
				}
				model.addObject("files", files);
			}
		}
		model.setViewName("/notice/admin/viewPage");
		return model;
	}

	@RequestMapping({ "/admin/save" })
	public ModelAndView saveApp(ModelAndView model, Notice notice, String apps) {
		if (StringUtil.isNotEmpty(notice.getId())) {
			this.noticeService.updateNotNull(notice, apps);
			model.setViewName("redirect:list");
			return model;
		}
		String uuid = UUIDUtil.getUUID();
		notice.setId(uuid);
		notice.setIsDelete(false);
		notice.setIsPublish(false);
		notice.setIsRevoke(false);
		notice.setCreateTime(new Date());
		this.noticeService.save(notice, apps);
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
	@RequestMapping({ "admin/publish" })
	@ResponseBody
	public Object publish(@RequestParam(value = "id", required = true) String id) {
		if (id != null) {
			Notice notice = this.noticeService.selectByKey(id);
			notice.setIsPublish(true);
			notice.setIsRevoke(false);
			notice.setPublishTime(new Date());
			this.noticeService.updateNotNull(notice);
			return ResponseUtil.success();
		}
		return ResponseUtil.error();
	}
	@RequestMapping({ "admin/revoke" })
	@ResponseBody
	public Object revoke(@RequestParam(value = "id", required = true) String id) {
		if (id != null) {
			Notice notice = this.noticeService.selectByKey(id);
			notice.setIsRevoke(true);
			//notice.setPublishTime(new Date());
			this.noticeService.updateNotNull(notice);
			return ResponseUtil.success();
		}
		return ResponseUtil.error();
	}

}
