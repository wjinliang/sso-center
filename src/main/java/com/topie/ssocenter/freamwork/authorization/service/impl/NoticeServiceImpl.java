package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.dao.NoticeMapper;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.Notice;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.NoticeService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice, String>
		implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private ApplicationInfoService appService;

	@Override
	public PageInfo<Notice> findList(int pageNum, int pageSize, Notice notice,String systemId) {
		List<String > systemIds = null;
		if(StringUtils.isNotEmpty(systemId)){
			systemIds = new ArrayList<String>();
			systemIds.add(systemId);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Notice> list = noticeMapper.findByExample(notice , systemIds ,"create_time desc");
		return new PageInfo<Notice>(list);
	}

	@Override
	public PageInfo<Notice> findCurrentUserNotice(Integer pageNum,
			Integer pageSize) {
		List<String> systemIds = new ArrayList<String>();
		PageInfo<ApplicationInfo> page = appService.selectCurrentUserSynApps();
		List<ApplicationInfo> apps =  page.getList();
		systemIds.add("123");
		for(ApplicationInfo a: apps){
			systemIds.add(a.getId());
		};
		PageHelper.startPage(pageNum, pageSize);
		List<Notice> list = noticeMapper.findByExample(null , systemIds ,"publish_time desc");
		return new PageInfo<Notice>(list);
	}

	@Override
	public Notice selectByKey(String key) {
		return super.selectByKey(key);
	}

	@Override
	public int save(Notice entity) {
		return super.save(entity);
	}
	@Override
	public int save(Notice entity,String appIds) {
		String[] ids = appIds.split(",");
		int i = super.save(entity);
		for(String appId:ids){
			 this.noticeMapper.insertNoticeApps(entity.getId(),appId);
		}
		return i;
	}

	@Override
	public int delete(String key) {
		return super.delete(key);
	}

	@Override
	public int updateAll(Notice entity) {
		return super.updateAll(entity);
	}
	@Override
	public int updateAll(Notice entity,String appIds) {
		int i =super.updateAll(entity);
//		List<Map> record = this.noticeMapper.selectNoticeApp(entity.getId());
		this.noticeMapper.deleteNoticeApp(entity.getId());
		String[] ids = appIds.split(",");
		for(String appId:ids){
			 this.noticeMapper.insertNoticeApps(entity.getId(),appId);
		}
		return i;
	}

	@Override
	public int updateNotNull(Notice entity) {
		return super.updateNotNull(entity);
	}

	@Override
	public List<Notice> selectByExample(Example example) {
		return super.selectByExample(example);
	}

	@Override
	public List<Notice> selectAll() {
		return super.selectAll();
	}

	@Override
	public List<Map> selectNoticeApp(String noticeId) {
		List<Map> record = this.noticeMapper.selectNoticeApp(noticeId);
		return record;
	}

}
