package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
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
	/**
	 * 通过区划CODE 获取orgCODE
	 * @param divisionCode  区划的前6位
	 * @return
	 */
	String selectMaxCode(String divisionCode);
	/**
	 * 通过区划CODE 获取seq
	 * @param divisionCode  区划的前6位
	 * @return
	 */
	Long selectMaxSeq(String divisionCode);
	/**
	 * 获取当前区划 用户
	 * @param thispage
	 * @param pagesize
	 * @param org   orgid  
	 * @param user  userName
	 * @return
	 */
	PageInfo<UserAccount> selectCurrentOrgUserPage(Integer thispage,
			Integer pagesize, Org org, UserAccount user);
	/**
	 * 获取该用户关联的用户
	 * @param user
	 * @param thispage
	 * @param pagesize
	 * @return
	 */
	PageInfo<UserAccount> listMergeUsers(UserAccount user, Integer thispage,
			Integer pagesize);
	/**
	 * 根据机构编码 获取下一个用户名编码
	 * @param code
	 * @return
	 */
	String selectNextUserLoginNameByOrgCode(String code);
	
	

}
