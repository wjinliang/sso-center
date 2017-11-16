package com.topie.ssocenter.freamwork.authorization.dao;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.Org;

public interface OrgMapper extends Mapper<Org> {

	String selectMaxCode(String divisionCode);

	Long selectMaxSeq(String divisionCode);

	long selectSynCount(String appId);
}