package com.topie.ssocenter.freamwork.authorization.service;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.DivisionSystem;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

public interface DivisionSystemService extends IService<DivisionSystem,String>{

	 PageInfo<DivisionSystem> findPage(int pageNum, int pageSize,
			DivisionSystem divisonSystem) ;


}
