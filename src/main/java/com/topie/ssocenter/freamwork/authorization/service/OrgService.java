package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

public interface OrgService extends IService<Org>{
	/**
	 * 获取当前用户的区划树，
	 * @param divisionid   当前所在的节点 可以为null
	 * @return jsonStr
	 */
	List<Division> selectCurrentDivisionTree(String divisionid);
	/**
	 * 获取当前用户 加载出当前division下的机构    
	 * @param pagesize 
	 * @param thispage 
	 * @param org   divisionId   orgName 
	 * @return
	 */
	PageInfo<Org> selectCurrentDivisionOrgPage(Integer thispage, Integer pagesize, Org org);
	
	

}
