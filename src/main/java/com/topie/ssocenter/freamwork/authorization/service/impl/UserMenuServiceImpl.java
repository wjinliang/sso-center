package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.topie.ssocenter.freamwork.authorization.dao.UserMenuMapper;
import com.topie.ssocenter.freamwork.authorization.dao.UserRoleMapper;
import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.authorization.service.UserMenuService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
@Service("userMenuService")
public class UserMenuServiceImpl extends BaseServiceImpl<UserMenu, Long>
		implements UserMenuService {

	@Autowired
	UserMenuMapper menuMapper;
	@Autowired
	UserRoleMapper roleMapper;

	@Override
	public List<UserMenu> selectMenusByRoleCode(String code) {
		return menuMapper.selectMenusByRoleCode(code);
	}

	@Override
	public List<UserMenu> findMenusByUserId(String id) {
		return this.menuMapper.findMenusByUserId(id);
	}

	@Override
	public int delete(Long menuId) {
		roleMapper.deleteRoleMenu(null, menuId);
		return this.menuMapper.deleteByPrimaryKey(menuId);
	}

	@Override
	public void seqList(Long currentid, Long targetid, String moveType,
			String moveMode) {
		if (currentid == null || targetid == null)
			return;
		UserMenu t2 = this.getMapper().selectByPrimaryKey(currentid);// 要移动的项
		UserMenu t1 = this.getMapper().selectByPrimaryKey(targetid);// 基准项
		if (t1 == null || t2 == null)
			return;
		if (moveType.equals("inner")) {// inner
			t2.setPid(targetid);
			this.getMapper().updateByPrimaryKeySelective(t2);
			return;
		}
		if (moveMode.equals("same")) {// 同级内移动
		} else {// 垮父级移动
			t2.setPid(t1.getPid());
		}
		if (moveType.equals("next")) {// 当前节点的后面
			t2.setSeq(t1.getSeq() + 1);
		} else {// 当前节点的前面
			t2.setSeq(t1.getSeq());
		}

		List<UserMenu> list = findByPid(t1.getPid());// order by seq asc
		Long seq = t2.getSeq();
		for (UserMenu t : list) {
			if (t.getId().equals(t2.getId())) {// 同级内移动才会出现 出现当前先不处理
				continue;
			}
			if (t.getSeq() > seq) {
				break;
			}
			if (t.getSeq() == seq) {
				seq++;
				t.setSeq(seq);
				this.getMapper().updateByPrimaryKeySelective(t);
			}
		}
		this.getMapper().updateByPrimaryKeySelective(t2);

	}

	private List<UserMenu> findByPid(Long pid) {
		Example ex = new Example(UserMenu.class);
		ex.createCriteria().andEqualTo("pid", pid);
		ex.setOrderByClause("seq asc");
		return getMapper().selectByExample(ex);
	}

	@Override
	public Mapper<UserMenu> getMapper() {

		return super.getMapper();
	}

	@Override
	public UserMenu selectByKey(Long key) {

		return super.selectByKey(key);
	}

	@Override
	public int save(UserMenu entity) {

		return super.save(entity);
	}

	@Override
	public int updateAll(UserMenu entity) {

		return super.updateAll(entity);
	}

	@Override
	public int updateNotNull(UserMenu entity) {

		return super.updateNotNull(entity);
	}

	@Override
	public List<UserMenu> selectByExample(Example example) {

		return super.selectByExample(example);
	}

	@Override
	public List<UserMenu> selectAll() {

		return super.selectAll();
	}

}
