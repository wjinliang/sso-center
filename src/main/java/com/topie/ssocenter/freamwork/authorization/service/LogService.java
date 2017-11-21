package com.topie.ssocenter.freamwork.authorization.service;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.Log;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 */
public interface LogService extends IService<Log,Long> {
	/**
	 * 1
	 */
	String CAOZUO="1";
	/**
	 * 0
	 */
	String DENGLU="0";
	
	PageInfo<Log> findLogList(int pageNum,
			int pageSize, Log log);


}
