package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

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
		return new PageInfo<Division>(list);
	}

	@Override
	public List<Division> findSheng() {
		Division ch = selectByKey("1");
		List<Division> list = findByPid("1");
		list.add(ch);
		return list;
	}

	@Override
	public List<Division> findByPid(String id) {
		Example example = new Example(Division.class);
		Example.Criteria criteria = example.createCriteria();
		example.setOrderByClause("seq asc");
		
		criteria.andEqualTo("parentId", id);
		return getMapper().selectByExample(example);
	}

	@Override
	public Integer selectNextSeqByPid(String divisionId) {
		
		return null;
	}

	@Override
	public List<Division> findByPidAndSeq(String parentId, Integer i) {
		Division ex = new Division();
		ex.setParentId(parentId);
		ex.setSeq(i);
		return getMapper().selectByExample(ex);
	}
}
