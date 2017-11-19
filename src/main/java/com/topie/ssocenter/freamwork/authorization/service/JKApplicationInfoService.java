package com.topie.ssocenter.freamwork.authorization.service;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.JKApplicationInfo;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 */
public interface JKApplicationInfoService extends IService<JKApplicationInfo> {

	PageInfo<JKApplicationInfo> findApplicationInfoList(int pageNum,
			int pageSize, JKApplicationInfo jkapp);

}
