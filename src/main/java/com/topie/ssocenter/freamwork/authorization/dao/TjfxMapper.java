package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;

public interface TjfxMapper {

	List<Map> selectBase(@Param("type")String type,@Param("isDelete") String isDelete,@Param("systemId") String systemId);

	List<Map> getSynUserCountByMonth(ApplicationInfo app);

	List<Map> getSynErrorCount(ApplicationInfo app);

	List<Map> getLoginTime(ApplicationInfo app);

}
