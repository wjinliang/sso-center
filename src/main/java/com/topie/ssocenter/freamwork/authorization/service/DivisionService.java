package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;

import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

public interface DivisionService extends IService<Division>{

	List<Division> findSheng();

	List<Division> findByPid(String id);

	Integer selectNextSeqByPid(String divisionId);

	List<Division> findByPidAndSeq(String parentId, Integer i);

}
