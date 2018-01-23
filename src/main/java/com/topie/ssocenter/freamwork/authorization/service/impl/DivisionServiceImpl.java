package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.Division;
import com.topie.ssocenter.freamwork.authorization.service.DivisionService;
import com.topie.ssocenter.freamwork.database.baseservice.impl.BaseServiceImpl;

/**
 */
@Service
public class DivisionServiceImpl extends BaseServiceImpl<Division,String> implements DivisionService {

	@Override
	public PageInfo<Division> findPage(int pageNum, int pageSize,
			Division division) {
		PageHelper.startPage(pageNum, pageSize);
		List<Division> list = getMapper().select(division);
		return new PageInfo<Division>(list);
	}

	@Override
	public List<Division> findSheng() {
		Division ch = selectByKey("1");
		List<Division> list = findByPid("1");
		list.add(ch);
		return list;
	}

	@Override
	public List<Division> findByPid(String id) {
		Example example = new Example(Division.class);
		Example.Criteria criteria = example.createCriteria();
		example.setOrderByClause("seq asc");
		
		criteria.andEqualTo("parentId", id);
		return getMapper().selectByExample(example);
	}

	/*@Override
	public Integer selectNextSeqByPid(String divisionId) {
		
		return null;
	}*/

	@Override
	public List<Division> findByPidAndSeq(String parentId, Integer i) {
		Division ex = new Division();
		ex.setParentId(parentId);
		ex.setSeq(i);
		return getMapper().selectByExample(ex);
	}


	@Override
	public void seqList(String currentid, String targetid, String moveType,
			String moveMode) {
		if(currentid==null || targetid==null) return ;
		Division t2 = this.getMapper().selectByPrimaryKey(currentid);//要移动的项
		Division t1 = this.getMapper().selectByPrimaryKey(targetid);//基准项
		if(t1==null || t2==null) return ;
		if(moveType.equals("inner")){//inner
			t2.setParentId(targetid);
			this.getMapper().updateByPrimaryKeySelective(t2);
			return;
		}
		if(moveMode.equals("same")){//同级内移动
		}else{//垮父级移动
			t2.setParentId(t1.getParentId());
		}
		if(moveType.equals("next")){//当前节点的后面
			t2.setSeq(t1.getSeq()+1);
		}else{//当前节点的前面
			t2.setSeq(t1.getSeq());
		}
		
		List<Division> list = findByPid(t1.getParentId());//order by seq asc
		int seq = t2.getSeq();
		for(Division t:list){
			if(t.getId().equals(t2.getId())){//同级内移动才会出现   出现当前先不处理
				continue;
			}
			if(t.getSeq()>seq){
				break;
			}
			if(t.getSeq()==seq){
				seq++;
				t.setSeq(seq);
				this.getMapper().updateByPrimaryKeySelective(t);
			}
		}
		this.getMapper().updateByPrimaryKeySelective(t2);
		
	}

	@Override
	public Mapper<Division> getMapper() {
		
		return super.getMapper();
	}

	@Override
	public Division selectByKey(String key) {
		
		return super.selectByKey(key);
	}

	@Override
	public int save(Division entity) {
		
		return super.save(entity);
	}

	@Override
	public int delete(String key) {
		
		return super.delete(key);
	}

	@Override
	public int updateAll(Division entity) {
		
		return super.updateAll(entity);
	}

	@Override
	public int updateNotNull(Division entity) {
		
		return super.updateNotNull(entity);
	}

	@Override
	public List<Division> selectByExample(Example example) {
		
		return super.selectByExample(example);
	}

	@Override
	public List<Division> selectAll() {
		
		return super.selectAll();
	}

	
	
}
