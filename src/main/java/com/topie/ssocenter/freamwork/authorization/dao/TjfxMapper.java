package com.topie.ssocenter.freamwork.authorization.dao;

import java.util.List;
import java.util.Map;

import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;

public interface TjfxMapper {

	List<Map> selectBase(Map map);

	List<Map> getSynUserCountByMonth(ApplicationInfo app);

	List<Map> getSynErrorCount(ApplicationInfo app);

	List<Map> getLoginTime(ApplicationInfo app);

}
