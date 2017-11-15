package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.dao.ApplicationInfoMapper;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 */
@Service
public class ApplicationInfoServiceImpl extends BaseService<ApplicationInfo> implements ApplicationInfoService {
	
	@Autowired
	private ApplicationInfoMapper appService;
	
	@Override
	public PageInfo<ApplicationInfo> findApplicationInfoList(int pageNum, int pageSize,
			ApplicationInfo role) {
		PageHelper.startPage(pageNum, pageSize);
		List<ApplicationInfo> list = getMapper().select(role);
		return new PageInfo(list);
	}

	@Override
	public PageInfo<ApplicationInfo> selectCurrentUserSynApps() {
			OrangeSideSecurityUser currentUserAccount = SecurityUtils.getCurrentSecurityUser();
			String mergeUuid = currentUserAccount.getMergeUuid();
			PageHelper.startPage(1,100);
			List<ApplicationInfo> list ;
			if (StringUtils.isEmpty(mergeUuid)) {
				list = appService.selectSynAppsByUserId(currentUserAccount.getId());
			} else {
				list = appService.selectSynAppsByMergeUuid(mergeUuid);
			}
		return new PageInfo<ApplicationInfo>(list);
	}
}
