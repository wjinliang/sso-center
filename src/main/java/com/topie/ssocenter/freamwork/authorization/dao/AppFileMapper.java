package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.AppFile;
import com.topie.ssocenter.freamwork.authorization.model.FileEntity;

public interface AppFileMapper extends Mapper<AppFile> {

	List<FileEntity> selectFileByAppId(@Param("appId") String appId); 
}