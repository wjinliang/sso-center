package com.topie.ssocenter.freamwork.authorization.service;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.FileEntity;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 */
public interface ApplicationInfoService extends IService<ApplicationInfo,String> {

	PageInfo<ApplicationInfo> findApplicationInfoList(int pageNum,
			int pageSize, ApplicationInfo app);

	PageInfo<ApplicationInfo> selectCurrentUserSynApps();
	PageInfo<ApplicationInfo> selectUserSynApps(String userId);

	void insertAppFile(String appId, String fileId);

	FileEntity getAppFile(String appId);

	PageInfo<ApplicationInfo> selectOrgSynApps(Long id);

}
