package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.ssocenter.freamwork.authorization.model.UserRole;

public interface UserRoleMapper extends Mapper<UserRole> {


    List<Map> findRoleMatchUpMenus();

	List<Long> selectMenuIdsByRoleID(String code);


	Map selectRoleMenuRecord(@Param("roleId")String roleId,@Param("menuId") Long menuId);
	int insertRoleMenu(@Param("roleId")String roleId,@Param("menuId") Long menuId);
	void deleteRoleMenu(@Param("roleId")String roleId,@Param("menuId") Long menuId);
}