package com.topie.ssocenter.freamwork.authorization.service;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.SynLog;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 */
public interface SynLogService extends IService<SynLog,String> {
	
	PageInfo<SynLog> findLogList(int pageNum,
			int pageSize, SynLog synlog);


}
