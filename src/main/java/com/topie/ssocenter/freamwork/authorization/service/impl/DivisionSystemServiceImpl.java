package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.DivisionSystem;
import com.topie.ssocenter.freamwork.authorization.service.DivisionSystemService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 */
@Service
public class DivisionSystemServiceImpl extends BaseServiceImpl<DivisionSystem,String> implements DivisionSystemService {

	@Override
	public PageInfo<DivisionSystem> findPage(int pageNum, int pageSize,
			DivisionSystem division) {
		PageHelper.startPage(pageNum, pageSize);
		List<DivisionSystem> list = getMapper().select(division);
		return new PageInfo<DivisionSystem>(list);
	}


	@Override
	public Mapper<DivisionSystem> getMapper() {
		
		return super.getMapper();
	}

	@Override
	public DivisionSystem selectByKey(String key) {
		
		return super.selectByKey(key);
	}

	@Override
	public int save(DivisionSystem entity) {
		
		return super.save(entity);
	}

	@Override
	public int delete(String key) {
		
		return super.delete(key);
	}

	@Override
	public int updateAll(DivisionSystem entity) {
		
		return super.updateAll(entity);
	}

	@Override
	public int updateNotNull(DivisionSystem entity) {
		
		return super.updateNotNull(entity);
	}

	@Override
	public List<DivisionSystem> selectByExample(Example example) {
		
		return super.selectByExample(example);
	}

	@Override
	public List<DivisionSystem> selectAll() {
		
		return super.selectAll();
	}

	
	
}
