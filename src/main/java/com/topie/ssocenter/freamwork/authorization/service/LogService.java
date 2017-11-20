package com.topie.ssocenter.freamwork.authorization.service;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.Log;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 */
public interface LogService extends IService<Log> {
	String CAOZUO="1";
	String DENGLU="0";
	
	PageInfo<Log> findLogList(int pageNum,
			int pageSize, Log log);


}
