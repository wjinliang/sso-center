package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.Notice;

public interface NoticeMapper extends Mapper<Notice> {

	List<Notice> findByExample(@Param("model") Notice notice,
			@Param("systemIds") List<String> systemIds, @Param("seq") String seq);

	int insertNoticeApps(@Param("noticeId")String noticeId,@Param("appId") String appId);
	
	/**
	 * 
	 * @param noticeId
	 * @return 
	 * map.put("noticeId",noticeId)
	 * map.put("appId",appId)
	 */
	List<Map> selectNoticeApp(String noticeId);

	int deleteNoticeApp(String noticeId);
}