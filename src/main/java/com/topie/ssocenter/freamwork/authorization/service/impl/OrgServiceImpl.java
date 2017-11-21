package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.dao.OrgMapper;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 */
@Service
public class OrgServiceImpl extends BaseServiceImpl<Org,Long> implements OrgService {
	@Autowired
	private DivisionService divisionService;
	@Autowired
	private UserAccountService userService;
	@Autowired
	private OrgMapper orgMapper;

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
				return ResponseUtil.emptyPage();
			}
			org.setDivisionId(o.getDivisionId());//设置
		}
		
		if(org.getName()!=null){//要查询当前用户所能看到的所有机构匹配的
			c.andLike("name", "%"+org.getName()+"%");
			Long orgId = SecurityUtils.getCurrentSecurityUser().getOrgId();
			Org o = this.getMapper().selectByPrimaryKey(orgId);
			List<String> divisionIds = getAllSonLevelDivisionId(o.getDivisionId());
			c.andIn("divisionId", divisionIds);
			
		}else{//查询当前区划下的机构
			if(org.getParentId()!=null){//查看子机构   子区划下的机构
				//c.andEqualTo("parentId", org.getId());
				List<Division> list = this.divisionService.findByPid(org.getDivisionId());
				if(list.size()==0){
					return ResponseUtil.emptyPage();
				}
				List<String> ids = new ArrayList<String>();
				for(Division d: list){
					ids.add(d.getId());
				}
				c.andIn("divisionId",ids );
			}else{
				c.andEqualTo("divisionId", org.getDivisionId());
			}
		}
		if(org.getSystemId()!=null){
			c.andEqualTo("systemId", org.getSystemId());
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
	@Override
	public String selectMaxCode(String divisionCode) {
		String code = orgMapper.selectMaxCode(divisionCode);
		return code;
	}
	@Override
	public Long selectMaxSeq(String divisionCode) {
		return orgMapper.selectMaxSeq(divisionCode);
	}
	@Override
	public PageInfo<UserAccount> selectCurrentOrgUserPage(Integer pageNum,
			Integer pageSize, Org org, UserAccount user) {
		if(user.getOrgId()==null){
			
			return ResponseUtil.emptyPage();
		}
		PageHelper.startPage(pageNum, pageSize);
		Example ex = new Example(UserAccount.class);
		Criteria ca = ex.createCriteria();
		ca.andEqualTo("orgId", user.getOrgId());
		if(user.getLoginname()!=null){
			ca.andEqualTo("loginname", user.getLoginname());
		}
		if(user.getName()!=null){
			ca.andLike("name", "%"+user.getName() +"%");
		}		
		List<UserAccount> list = this.userService.selectByExample(ex);
		return new PageInfo<UserAccount>(list);
	}
	@Override
	public PageInfo<UserAccount> listMergeUsers(UserAccount user, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(UserAccount.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("mergeUuid", user.getMergeUuid())
			.andNotEqualTo("code", user.getCode());
		if(user.getName()!=null){
			c.andEqualTo("name", user.getName());
		}
		if(user.getLoginname()!=null){
			c.andEqualTo("loginname", user.getLoginname());
		}
		List<UserAccount> list = this.userService.selectByExample(example);
		return new PageInfo<UserAccount>(list);
	}
	@Override
	public String selectNextUserLoginNameByOrgCode(String code) {
		String maxname = this.userService.selectMaxUserLoginNameByOrgCode(code);
		if(maxname==null){
			return code+"001";
		}
		maxname = String.valueOf((Long.valueOf(maxname)+1));
		return maxname;
	}
	@Override
	public Mapper<Org> getMapper() {
		
		return super.getMapper();
	}
	@Override
	public Org selectByKey(Long key) {
		
		return super.selectByKey(key);
	}
	@Override
	public int save(Org entity) {
		
		return super.save(entity);
	}
	@Override
	public int delete(Long key) {
		
		return super.delete(key);
	}
	@Override
	public int updateAll(Org entity) {
		
		return super.updateAll(entity);
	}
	@Override
	public int updateNotNull(Org entity) {
		
		return super.updateNotNull(entity);
	}
	@Override
	public List<Org> selectByExample(Example example) {
		
		return super.selectByExample(example);
	}
	@Override
	public List<Org> selectAll() {
		
		return super.selectAll();
	}

}
