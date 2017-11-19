package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.JKApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.service.JKApplicationInfoService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 */
@Service
public class JKApplicationInfoServiceImpl extends BaseService<JKApplicationInfo> implements JKApplicationInfoService {
	
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

}
