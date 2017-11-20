package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.dao.LogMapper;
import com.topie.ssocenter.freamwork.authorization.model.Log;
import com.topie.ssocenter.freamwork.authorization.service.LogService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 */
@Service
public class LogServiceImpl extends BaseService<Log> implements LogService {
	
	@Autowired
	private LogMapper logService;
	
	@Override
	public PageInfo<Log> findLogList(int pageNum, int pageSize,
			Log log) {
		PageHelper.startPage(pageNum, pageSize);
		Example ex = new Example(Log.class);
		Criteria c = ex.createCriteria();
		if(!StringUtils.isEmpty(log.getContent())){
			c.andLike("content", "%"+log.getContent()+"%");
		}
		if(!StringUtils.isEmpty(log.getType())){
			c.andEqualTo("type",log.getType());
		}
		ex.setOrderByClause("date desc");
		List<Log> list = getMapper().selectByExample(ex);
		return new PageInfo(list);
	}

}
