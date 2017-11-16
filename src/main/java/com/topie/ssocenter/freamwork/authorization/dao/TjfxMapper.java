package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TjfxMapper {

	List<Map> selectBase(@Param("type")String type,@Param("isDelete") String isDelete,@Param("systemId") String systemId);

}
