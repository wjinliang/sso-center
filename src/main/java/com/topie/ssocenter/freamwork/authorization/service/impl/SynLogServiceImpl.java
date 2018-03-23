package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.dao.SynLogMapper;
import com.topie.ssocenter.freamwork.authorization.model.SynLog;
import com.topie.ssocenter.freamwork.authorization.service.SynLogService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 */
@Service
public class SynLogServiceImpl extends BaseServiceImpl<SynLog,String> implements SynLogService {
	
	@Autowired
	private SynLogMapper synLogService;
	
	@Override
	public PageInfo<SynLog> findLogList(int pageNum, int pageSize,
			SynLog log) {
		PageHelper.startPage(pageNum, pageSize);
		Example ex = new Example(SynLog.class);
		Criteria c = ex.createCriteria();
		if(!StringUtils.isEmpty(log.getSynUsername())){
			c.andLike("synUsername", "%"+log.getSynUsername()+"%");
		}
		if(!StringUtils.isEmpty(log.getAppId())){
			c.andEqualTo("appId",log.getAppId());
		}
		if(!StringUtils.isEmpty(log.getSynResult())){
			c.andLike("synResult","%"+log.getSynResult()+"%");
		}
		ex.setOrderByClause("syn_time desc");
		List<SynLog> list = getMapper().selectByExample(ex);
		return new PageInfo(list);
	}

}
