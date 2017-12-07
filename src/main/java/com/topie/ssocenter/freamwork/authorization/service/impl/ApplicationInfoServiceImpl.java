package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.dao.ApplicationInfoMapper;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 */
@Service
public class ApplicationInfoServiceImpl extends
		BaseServiceImpl<ApplicationInfo, String> implements
		ApplicationInfoService {

	@Autowired
	private ApplicationInfoMapper appMapper;

	@Override
	public PageInfo<ApplicationInfo> findApplicationInfoList(int pageNum,
			int pageSize, ApplicationInfo app) {
		PageHelper.startPage(pageNum, pageSize);
		Example ex = new Example(ApplicationInfo.class);
		Criteria c = ex.createCriteria();
		if (!StringUtils.isEmpty(app.getAppName())) {
			c.andLike("appName", "%" + app.getAppName() + "%");
		}
		if (!StringUtils.isEmpty(app.getStatus())) {
			c.andEqualTo("status", app.getStatus());
		}
		ex.setOrderByClause("seq asc");
		List<ApplicationInfo> list = getMapper().selectByExample(ex);
		return new PageInfo(list);
	}

	@Override
	public PageInfo<ApplicationInfo> selectCurrentUserSynApps() {
		OrangeSideSecurityUser currentUserAccount = SecurityUtils
				.getCurrentSecurityUser();
		String mergeUuid = currentUserAccount.getMergeUuid();
		PageHelper.startPage(1, 100);
		List<ApplicationInfo> list;
		if (StringUtils.isEmpty(mergeUuid)) {
			list = appMapper.selectSynAppsByUserId(currentUserAccount.getId());
		} else {
			list = appMapper.selectSynAppsByMergeUuid(mergeUuid);
		}
		return new PageInfo<ApplicationInfo>(list);
	}

	@Override
	public Mapper<ApplicationInfo> getMapper() {
		return super.getMapper();
	}

	@Override
	public int save(ApplicationInfo entity) {
		return super.save(entity);
	}

	@Override
	public ApplicationInfo selectByKey(String key) {

		return super.selectByKey(key);
	}

	@Override
	public int delete(String key) {

		return super.delete(key);
	}

	@Override
	public List<ApplicationInfo> selectByExample(Example example) {

		return super.selectByExample(example);
	}

	@Override
	public int updateAll(ApplicationInfo entity) {
		return super.updateAll(entity);
	}

	@Override
	public int updateNotNull(ApplicationInfo entity) {
		return super.updateNotNull(entity);
	}

	@Override
	public List<ApplicationInfo> selectAll() {
		return super.selectAll();
	}

}
