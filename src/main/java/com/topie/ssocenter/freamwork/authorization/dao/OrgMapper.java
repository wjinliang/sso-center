package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.Org;

public interface OrgMapper extends Mapper<Org> {

	String selectMaxCode(String divisionCode);

	Long selectMaxSeq(String divisionCode);

	long selectSynCount(String appId);

	List<Org> selectSynOrgByAppId(@Param("appId")String appId,@Param("org") Org org);

	List<Org> selectToSynOrgByAppId(@Param("appId")String appId,@Param("org") Org org);
}