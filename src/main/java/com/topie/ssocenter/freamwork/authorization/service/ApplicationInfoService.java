package com.topie.ssocenter.freamwork.authorization.service;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 */
public interface ApplicationInfoService extends IService<ApplicationInfo> {

	PageInfo<ApplicationInfo> findApplicationInfoList(int pageNum,
			int pageSize, ApplicationInfo role);

}
