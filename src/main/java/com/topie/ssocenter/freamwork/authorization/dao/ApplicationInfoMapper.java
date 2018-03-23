package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;

public interface ApplicationInfoMapper extends Mapper<ApplicationInfo> {

	List<ApplicationInfo> selectSynAppsByUserId(String userId);

	List<ApplicationInfo> selectSynAppsByMergeUuid(String mergeUuid);

	List<ApplicationInfo> selectOrgSynApps(Long id);

}