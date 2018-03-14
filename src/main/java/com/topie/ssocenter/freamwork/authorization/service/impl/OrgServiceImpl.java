package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.dao.OrgMapper;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.model.Org;
import com.topie.ssocenter.freamwork.authorization.model.SynLog;
import com.topie.ssocenter.freamwork.authorization.model.SynOrg;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.ApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.authorization.service.OrgService;
import com.topie.ssocenter.freamwork.authorization.service.SynService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 */
@Service
public class OrgServiceImpl extends BaseServiceImpl<Org,Long> implements OrgService {
	Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);
	@Autowired
	private DivisionService divisionService;
	@Autowired
	private UserAccountService userService;
	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private SynService synService;
	@Autowired
	private ApplicationInfoService appService;

	@Override
	public PageInfo<Org> selectCurrentDivisionOrgPage(Integer pageNum, Integer pageSize,
			Org org) {
		Example ex = new Example(Org.class,false);
		Criteria c = ex.createCriteria();
		String did = org.getDivisionId();
		Long orgId = SecurityUtils.getCurrentSecurityUser().getOrgId();
		Org o = this.getMapper().selectByPrimaryKey(orgId);
		if(o==null){//如果当前用户的机构不存在   返回空
			return ResponseUtil.emptyPage();
		}
		if(StringUtils.isEmpty(did)){
			org.setDivisionId(o.getDivisionId());//设置
		}
		Division d = divisionService.selectByKey(org.getDivisionId());
		if(StringUtils.isNotEmpty(org.getName())){//要查询当前用户所能看到的所有机构匹配的
			c.andLike("name", "%"+org.getName()+"%");
			String dCode = d.getCode()+"";
			//d.getLevel();
			String type = d.getType();
			int end = 0;
			switch (type) {
				case "0"://中央
					break;
				case "1"://省级 
				case "2"://直辖市 
				case "3"://计划单列市 
					end = 2;
					if(dCode.length()>=end)
						c.andLike("code", dCode.substring(0,end)+"%");
					break;
				case "4"://市级 
					end = 4;
					if(dCode.length()>=end)
						c.andLike("code", dCode.substring(0,end)+"%");
					break;
				case "5"://区县 
				case "6"://乡镇 
				case "7"://村 
					end = 6;
					if(dCode.length()>=end)
						c.andLike("code", dCode.substring(0,end)+"%");
					break;
				default:
					break;
			}
		}else if(org.getParentId()!=null){//查看子机构   子区划下的机构
			//c.andEqualTo("parentId", org.getId());
			List<Division> list = this.divisionService.findByPid(org.getDivisionId());
			if(list.size()==0){
				return ResponseUtil.emptyPage();
			}
			List<String> ids = new ArrayList<String>();
			for(Division ds: list){
				ids.add(ds.getId());
			}
			c.andIn("divisionId",ids);
		}else{
			c.andEqualTo("divisionId", org.getDivisionId());
		}
		/*//
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
		}*/
		List<String> listSYSIDS = new ArrayList<String>();
		if(org.getSystemId()!=null){
			c.andEqualTo("systemId", org.getSystemId());
		}else{//获取当前用户可以看到的系统
			Division division  = divisionService.selectByKey(o.getDivisionId());
			String type = division.getType();
			if(type.equals("0")){
				
			}else{
				PageInfo<ApplicationInfo> apps = appService.selectCurrentUserSynApps();
				for(ApplicationInfo a:apps.getList()){
					listSYSIDS.add(a.getId());
				}
				if(listSYSIDS.size()<=0)return ResponseUtil.emptyPage();
				c.andIn("synSystemId", listSYSIDS);//无效
			}
		}
		ex.setOrderByClause("seq asc");
		PageHelper.startPage(pageNum, pageSize);
		List<Org> list = orgMapper.selectByExampleEx(ex,listSYSIDS);
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
		Example ex = new Example(UserAccount.class);
		Criteria ca = ex.createCriteria();
		ca.andEqualTo("isDelete",false);
		if(user.getName()!=null){
			ca.andLike("name", "%"+user.getName() +"%");
			Division d = divisionService.selectByKey(org.getDivisionId());
			String dCode = d.getCode()+"";
			//d.getLevel();
			String type = d.getType();
			int end = 0;
			switch (type) {
				case "0"://中央
					break;
				case "1"://省级 
				case "2"://直辖市 
				case "3"://计划单列市 
					end = 2;
					if(dCode.length()>=end)
						ca.andLike("loginname", dCode.substring(0,end)+"%");
					break;
				case "4"://市级 
					end = 4;
					if(dCode.length()>=end)
						ca.andLike("loginname", dCode.substring(0,end)+"%");
					break;
				case "5"://区县 
				case "6"://乡镇 
				case "7"://村 
					end = 6;
					if(dCode.length()>=end)
						ca.andLike("loginname", dCode.substring(0,end)+"%");
					break;
				default:
					break;
			}
			if(user.getLoginname()!=null){
				ca.andEqualTo("loginname", user.getLoginname());
			}
		}else{		
			ca.andEqualTo("orgId", user.getOrgId());
			if(user.getLoginname()!=null){
				ca.andEqualTo("loginname", user.getLoginname());
			}
		}
		PageHelper.startPage(pageNum, pageSize);
		List<UserAccount> list = this.userService.selectByExample(ex);
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
		int i = super.save(entity);
		return i;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Map synOneOrg(Org org, String appId,String type,String typeName) {
		ApplicationInfo app = appService.selectByKey(appId);
		
		Map u = new HashMap();
		u.put("opType", typeName);
		u.put("opTypeCode", type);
		if(app==null){
			String s = "同步"+typeName+"Org时 {appId="+appId+"}未找到对应的应用";
			logger.info(s);
			u.put("result", s);
			u.put("status", false);
			return u;
		}
		u.put("appName", app.getAppName());
		u.put("appId", app.getId());
		u.put("appCode", app.getAppCode());
		u.put("status", true);
		if(app.getStatus().equals("2")){
			u.put("result", "系统维护中，暂无操作");
			u.put("isAuthorize",false);
			return u;
		}
//		u.put(, );
		String result = "000";
		String today = DmDateUtil.Current();
		if(app.getIsOrgSyn()){//如果该系统要同步机构
			logger.info("["+today+"]开始同步Org："+app.getAppName()+"-"+org.getName()+"-"+typeName);
			result = this.synService.synStart(appId, org.getId()
					.toString(), type);
			if (result != null && result.equals("000")) {
				result = "同步成功";
				if(type.equals("41")){//新增
				SynOrg synOrg = new SynOrg();
				String uuid = UUIDUtil.getUUID();
				synOrg.setAppId(appId);
				synOrg.setId(uuid);
				synOrg.setOrgId(org.getId().toString());
				synOrg.setSynTime(today);
				this.synService.save(synOrg);
				}
				if(type.equals("42")){//更新
					//nothing todo
				}
				if(type.equals("43")){//删除
					synService.deleteSynOrg(org.getId().toString(),appId);
				}
				u.put("isAuthorize",app.getIsOrgAuthorize());
			}else{
				u.put("status", false);
			}
		}else{//不同不到该系统
			result = "该系统设置为不同步机构";
		}
		logger.info("["+today+"]同步Org结果："+app.getAppName()+"-"+org.getName()+"-"+typeName+"-"+result);
		/*
		 * 添加记录日志的操作
		 */
		SynLog synLog = new SynLog();
		synLog.setId(UUIDUtil.getUUID());
		synLog.setAppId(appId);
		synLog.setAppName(app.getAppName());
		synLog.setSynTime(today);
		synLog.setSynResult("组织(" + org.getName() + ")"+typeName+"操作："
				+ result);
		OrangeSideSecurityUser currentUser = SecurityUtils.getCurrentSecurityUser();
		synLog.setSynUserid(currentUser.getId());
		synLog.setSynUsername(currentUser.getDisplayName());
		this.synService.save(synLog);
		u.put("result", result);
		return u;
		
	}
	@Override
	public PageInfo<Org> findSynOrgByAppId(Integer pageNum, Integer pageSize,
			Org org, String appId) {
		PageHelper.startPage(pageNum, pageSize);
		List<Org> orgList = this.orgMapper.selectSynOrgByAppId(appId,org);
		PageInfo<Org> page = new PageInfo<Org>(orgList);
		return page;
	}
	@Override
	public PageInfo<Org> findToSynOrgByAppId(Integer pageNum,
			Integer pageSize, Org org, String appId) {
		PageHelper.startPage(pageNum, pageSize);
		List<Org> orgList = this.orgMapper.selectToSynOrgByAppId(appId,org);
		PageInfo<Org> page = new PageInfo<Org>(orgList);
		return page;
	}

}
