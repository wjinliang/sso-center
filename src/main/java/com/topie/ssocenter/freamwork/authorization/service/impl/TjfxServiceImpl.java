package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.topie.ssocenter.freamwork.authorization.dao.ApplicationInfoMapper;
import com.topie.ssocenter.freamwork.authorization.dao.OrgMapper;
import com.topie.ssocenter.freamwork.authorization.dao.TjfxMapper;
import com.topie.ssocenter.freamwork.authorization.dao.UserAccountMapper;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.service.TjfxService;

@Service
public class TjfxServiceImpl implements TjfxService {
	
	@Autowired
	private TjfxMapper tjfxMapper;
	@Autowired
	private ApplicationInfoMapper appMapper;
	@Autowired
	private UserAccountMapper userMapper;
	@Autowired
	private OrgMapper orgMapper;
	
	@Override
	public List<Map> getUserOrgCountForApp(ApplicationInfo app) {
		List<Map> resultList = new ArrayList<Map>();
		Example ex = new Example(ApplicationInfo.class);
		ex.setOrderByClause("seq asc");
		List<ApplicationInfo> appList = appMapper.selectByExample(ex);
		for (int i = 0; i < appList.size(); i++) {
			Map resultMap = new HashMap();
			ApplicationInfo applicationInfo = appList.get(i);
			String id = applicationInfo.getId();
			resultMap.put("app", applicationInfo);
			long userCount = userMapper.selectSynCount(applicationInfo.getId());
			resultMap.put("userCount", userCount);
			
			long orgCount = orgMapper.selectSynCount(applicationInfo.getId());
			resultMap.put("orgCount", orgCount);
			resultList.add(resultMap);
		}
		return resultList;
	}

	@Override
	public List<Map> getUserCountForDivision(ApplicationInfo app) {
		String type=" count(t.`NAME`) ";
		String isDelete="0";
		String systemId=app.getAppName()==null?"动物标识及动物产品追溯系统":app.getAppName();
		return this.tjfxMapper.selectBase(type,isDelete,systemId);
	}

	@Override
	public List<Map> getUserLonginCountForDivision(ApplicationInfo app) {
		String type=" sum(t.`LOGINCOUNT`) ";
		String isDelete="0";
		String systemId=app.getAppName()==null?"动物标识及动物产品追溯系统":app.getAppName();
		return this.tjfxMapper.selectBase(type,isDelete,systemId);
	}

	@Override
	public List<Map> getUserDelCountForDivision(ApplicationInfo app) {
		String type=" count(t.`NAME`) ";
		String isDelete="1";
		String systemId=app.getAppName()==null?"动物标识及动物产品追溯系统":app.getAppName();
		return this.tjfxMapper.selectBase(type,isDelete,systemId);
	}

}
