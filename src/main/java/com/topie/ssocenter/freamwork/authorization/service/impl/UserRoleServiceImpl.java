package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.topie.ssocenter.freamwork.authorization.dao.UserMenuMapper;
import com.topie.ssocenter.freamwork.authorization.dao.UserRoleMapper;
import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.authorization.model.UserRole;
import com.topie.ssocenter.freamwork.authorization.service.UserRoleService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/3 说明：
 */
@Service("roleService")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole,String> implements
		UserRoleService {

	@Autowired
	UserRoleMapper roleMapper;
	@Autowired
	UserMenuMapper menuMapper;

	@Override
	public int insertUserRoleFunction(String roleId, Long functionId) {
		return roleMapper.insertRoleMenu(roleId, functionId);
	}

	@Override
	public List<Map> findUserRoleMatchUpFunctions() {
		return roleMapper.findRoleMatchUpMenus();
	}

	@Override
	public PageInfo<UserRole> findUserRoleList(int pageNum, int pageSize,
			UserRole role) {
		PageHelper.startPage(pageNum, pageSize);
		Example ex = new Example(UserRole.class);
		Criteria c = ex.createCriteria();
		ex.setOrderByClause("seq asc");
		if (!StringUtils.isEmpty(role.getName())) {
			c.andLike("name", "%" + role.getName() + "%");
		}
		if (role.getEnabled() != null) {
			c.andEqualTo("enabled", role.getEnabled());
		}
		List<UserRole> list = this.roleMapper.selectByExample(ex);
		return new PageInfo(list);
	}

	@Override
	public String selectRoleMenusTreeStr(String roleCode) {
		List list = new ArrayList();
		Example ex = new Example(UserMenu.class);
		ex.setOrderByClause("seq,id asc");
		List<UserMenu> mes = menuMapper.selectByExample(ex);
		List<Long> ids = roleMapper.selectMenuIdsByRoleID(roleCode);
		int i = 0;
		for (UserMenu me : mes) {
			Map m = new HashMap();
			m.put("id", me.getId());
			m.put("name", me.getName());
			m.put("pId", me.getPid());
			m.put("checked", false);

			if (i < ids.size()) {
				Long id = ids.get(i);
				if (id.equals(me.getId())) {
					i++;
					m.put("checked", true);
				}
			}
			list.add(m);
		}

		JSONArray jsonArr = new JSONArray(list);
		return jsonArr.toJSONString();
	}

	@Override
	public void save(UserRole role, String menuids) {
		this.getMapper().insert(role);
		if(StringUtil.isEmpty(menuids)) return;
		String[] arr = menuids.split(",");
		for (String id : arr) {
			Long mid = Long.valueOf(id);
			this.roleMapper.insertRoleMenu(role.getCode(), mid);
		}

	}

	@Override
	public void updateNotNull(UserRole role, String menuids) {
		this.getMapper().updateByPrimaryKeySelective(role);
		if(StringUtil.isEmpty(menuids)) return;
		String[] arr = menuids.split(",");
		String roleId  = role.getCode();
		for (String id : arr) {
			Long menuId = Long.valueOf(id);
			Map record = this.roleMapper.selectRoleMenuRecord(roleId,menuId);
			if(record==null){
				this.roleMapper.insertRoleMenu(roleId, menuId);
			}else{
				this.roleMapper.deleteRoleMenu(roleId, menuId);
			}
		}
	}

	@Override
	public int delete(String roleId) {
		this.roleMapper.deleteRoleMenu(roleId, null);
		return this.getMapper().deleteByPrimaryKey(roleId);
		
	}

	@Override
	public UserRole selectByKey(String key) {
		
		return super.selectByKey(key);
	}

	@Override
	/**
	 * 不可用  没有实现 只新增基本属性 没有关联表
	 */
	@Deprecated
	public int save(UserRole entity) {
		
		return super.save(entity);
	}

	@Override
	/**
	 * 不可用  没有实现 只更新基本属性 没有关联表
	 */
	@Deprecated
	public int updateAll(UserRole entity) {
		
		return super.updateAll(entity);
	}

	@Override
	/**
	 * 不可用  没有实现 只更新基本属性 没有关联表
	 */
	@Deprecated
	public int updateNotNull(UserRole entity) {
		
		return super.updateNotNull(entity);
	}

	@Override
	public List<UserRole> selectByExample(Example example) {
		
		return super.selectByExample(example);
	}

	@Override
	public List<UserRole> selectAll() {
		
		return super.selectAll();
	}
	
}
