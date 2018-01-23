package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.JKApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.service.JKApplicationInfoService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 */
@Service
public class JKApplicationInfoServiceImpl extends BaseServiceImpl<JKApplicationInfo,String> implements JKApplicationInfoService {
	
	@Override
	public PageInfo<JKApplicationInfo> findApplicationInfoList(int pageNum, int pageSize,
			JKApplicationInfo app) {
		PageHelper.startPage(pageNum, pageSize);
		Example ex = new Example(JKApplicationInfo.class);
		Criteria c = ex.createCriteria();
		if(!StringUtils.isEmpty(app.getAppName())){
			c.andLike("appName", "%"+app.getAppName()+"%");
		}
		List<JKApplicationInfo> list = getMapper().selectByExample(ex);
		return new PageInfo(list);
	}

	@Override
	public Mapper<JKApplicationInfo> getMapper() {
		
		return super.getMapper();
	}

	@Override
	public JKApplicationInfo selectByKey(String key) {
		
		return super.selectByKey(key);
	}

	@Override
	public int save(JKApplicationInfo entity) {
		
		return super.save(entity);
	}

	@Override
	public int delete(String key) {
		
		return super.delete(key);
	}

	@Override
	public int updateAll(JKApplicationInfo entity) {
		
		return super.updateAll(entity);
	}

	@Override
	public int updateNotNull(JKApplicationInfo entity) {
		
		return super.updateNotNull(entity);
	}

	@Override
	public List<JKApplicationInfo> selectByExample(Example example) {
		
		return super.selectByExample(example);
	}

	@Override
	public List<JKApplicationInfo> selectAll() {
		
		return super.selectAll();
	}


}
