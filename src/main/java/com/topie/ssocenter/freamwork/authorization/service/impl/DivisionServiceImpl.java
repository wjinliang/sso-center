package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 */
@Service
public class DivisionServiceImpl extends BaseService<Division> implements DivisionService {

	@Override
	public PageInfo<Division> findPage(int pageNum, int pageSize,
			Division division) {
		PageHelper.startPage(pageNum, pageSize);
		List<Division> list = getMapper().select(division);
		return new PageInfo(list);
	}

	@Override
	public List<Division> findSheng() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Division> findByPid(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectNextSeqByPid(String divisionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Division> findByPidAndSeq(String parentId, Integer i) {
		// TODO Auto-generated method stub
		return null;
	}
}
