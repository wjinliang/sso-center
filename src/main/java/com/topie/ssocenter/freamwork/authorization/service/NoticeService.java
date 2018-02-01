package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.Notice;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 */
public interface NoticeService extends IService<Notice, String> {

	PageInfo<Notice> findList(int pageNum, int pageSize, Notice notice,
			String systemId);

	PageInfo<Notice> findCurrentUserNotice(Integer thispage, Integer pagesize, String type);

	int updateAll(Notice notice, String apps);
	int updateNotNull(Notice notice, String apps);

	int save(Notice notice, String apps);

	/**
	 * 
	 * @param noticeId
	 * @return map.put("noticeId",noticeId) map.put("appId",appId)
	 */
	List<Map> selectNoticeApp(String noticeId);

}
