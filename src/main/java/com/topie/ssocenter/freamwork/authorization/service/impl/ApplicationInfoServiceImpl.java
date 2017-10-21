package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 */
@Service
public class ApplicationInfoServiceImpl extends BaseService<ApplicationInfo> implements ApplicationInfoService {

	@Override
	public PageInfo<ApplicationInfo> findApplicationInfoList(int pageNum, int pageSize,
			ApplicationInfo role) {
		PageHelper.startPage(pageNum, pageSize);
		List<ApplicationInfo> list = getMapper().select(role);
		return new PageInfo(list);
	}
}
