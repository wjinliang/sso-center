package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.UserAccount;

public interface UserAccountMapper extends Mapper<UserAccount> {
	UserAccount findUserByLoginName(String loginName);

    List<UserAccount> findUserList(UserAccount user);

    int findExistUser(UserAccount user);

	String selectMaxUserLoginNameByOrgCode(String code);
	
	long selectSynCount(String appId);
	/**
	 *  等于 appId
	 * @param user
	 * @param appId
	 * @return
	 */
	List<UserAccount> selectSynUserByAppId(@Param("user")UserAccount user,@Param("appId") String appId);
	/**
	 *  不等于 appId
	 * @param user
	 * @param appId
	 * @return
	 */
	List<UserAccount> selectToSynUserByAppId(@Param("user")UserAccount user,@Param("appId") String appId);
	/**
	 * 
	 * @param id user.code = 
	 * @param startTime syn.synTime >
	 * @return
	 */
	List<UserAccount> selectUserSystem(@Param("userId")String id, @Param("startTime")String startTime);

	void updateSystemIdByPrimaryKey(UserAccount user);
}