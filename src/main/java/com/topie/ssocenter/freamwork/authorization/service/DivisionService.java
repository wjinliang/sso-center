package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

public interface DivisionService extends IService<Division,String>{

	List<Division> findSheng();

	List<Division> findByPid(String id);

//	Integer selectNextSeqByPid(String divisionId);

	List<Division> findByPidAndSeq(String parentId, Integer i);
	public PageInfo<Division> findPage(int pageNum, int pageSize,
			Division divison) ;

	void seqList(String currentid, String targetid, String moveType,
			String moveMode);

}
