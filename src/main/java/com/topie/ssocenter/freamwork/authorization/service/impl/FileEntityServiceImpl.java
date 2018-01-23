package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.topie.ssocenter.freamwork.authorization.model.FileEntity;
import com.topie.ssocenter.freamwork.authorization.service.FileEntityService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 */
@Service
public class FileEntityServiceImpl extends BaseServiceImpl<FileEntity,String> implements FileEntityService {

	@Override
	public FileEntity selectByKey(String key) {
		return super.selectByKey(key);
	}

	@Override
	public int save(FileEntity entity) {
		return super.save(entity);
	}

	@Override
	public int delete(String key) {
		return super.delete(key);
	}

	@Override
	public int updateAll(FileEntity entity) {
		return super.updateAll(entity);
	}

	@Override
	public int updateNotNull(FileEntity entity) {
		return super.updateNotNull(entity);
	}

	@Override
	public List<FileEntity> selectByExample(Example example) {
		return super.selectByExample(example);
	}

	@Override
	public List<FileEntity> selectAll() {
		return super.selectAll();
	}

}
