package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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
			ApplicationInfo app) {
		PageHelper.startPage(pageNum, pageSize);
		Example ex = new Example(ApplicationInfo.class);
		Criteria c = ex.createCriteria();
		if(!StringUtils.isEmpty(app.getAppName())){
			c.andLike("appName", "%"+app.getAppName()+"%");
		}
		if(!StringUtils.isEmpty(app.getStatus())){
			c.andEqualTo("status",app.getStatus());
		}
		ex.setOrderByClause("seq asc");
		List<ApplicationInfo> list = getMapper().selectByExample(ex);
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
