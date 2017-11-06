package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseService;

/**
 */
@Service
public class OrgServiceImpl extends BaseService<Org> implements OrgService {
	@Autowired
	private DivisionService divisionService;
	@Autowired
	private UserAccountService userService;

	@Override
	public PageInfo<Org> selectCurrentDivisionOrgPage(Integer pageNum, Integer pageSize,
			Org org) {
		Example ex = new Example(Org.class);
		Criteria c = ex.createCriteria();
		String did = org.getDivisionId();
		if(did ==null){
			Long orgId = SecurityUtils.getCurrentSecurityUser().getOrgId();
			Org o = this.getMapper().selectByPrimaryKey(orgId);
			if(o==null){//如果当前用户的机构不存在   返回空
				return new PageInfo<Org>();
			}
			org.setDivisionId(o.getDivisionId());//设置
		}
		
		if(org.getName()!=null){//要查询当前用户所能看到的所有机构匹配的
			c.andLike("name", org.getName());
			Long orgId = SecurityUtils.getCurrentSecurityUser().getOrgId();
			Org o = this.getMapper().selectByPrimaryKey(orgId);
			List<String> divisionIds = getAllSonLevelDivisionId(o.getDivisionId());
			c.andIn("divisionId", divisionIds);
			
		}else{//查询当前区划下的机构
			if(org.getId()!=null){
				c.andEqualTo("parentId", org.getId());
			}else{
				c.andEqualTo("divisionId", org.getDivisionId());
			}
		}
		if(org.getSystemId()!=null){
			c.andEqualTo("systeId", org.getSystemId());
		}
		ex.setOrderByClause("seq asc");
		PageHelper.startPage(pageNum, pageSize);
		List<Org> list = getMapper().selectByExample(ex);
		return new PageInfo<Org>(list);
	}
	/**
	 * 递归获取当前区划的所有子 区划ID  包含自己
	 * @param divisionId
	 * @return
	 */
	private List<String> getAllSonLevelDivisionId(String divisionId) {
		List<String> divisionIds = new ArrayList<String>();
		divisionIds.add(divisionId);
		List<Division> divisions = this.divisionService.findByPid(divisionId);
		for(Division d:divisions){
			divisionIds.addAll(getAllSonLevelDivisionId(d.getId()));
		}
		return divisionIds;
	}

	@Override
	public List<Division> selectCurrentDivisionTree(String divisionid) {
		Long orgId = SecurityUtils.getCurrentSecurityUser().getOrgId();
		Org o = this.getMapper().selectByPrimaryKey(orgId);
		List<Division> list = this.divisionService.findByPid(o.getDivisionId());
		Division parent = this.divisionService.selectByKey(o.getDivisionId());
		list.add(parent);
		Division lastson = this.divisionService.selectByKey(divisionid);
		if(lastson==null){return list;}
		String p = lastson.getParentId();
		for(int i=0;i<lastson.getLevel()-parent.getLevel()-1;i++){
			list.addAll(this.divisionService.findByPid(p));
			p = this.divisionService.selectByKey(p).getParentId();
		}
		return list;
	}

}
