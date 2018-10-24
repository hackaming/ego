package com.ego.dubbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TBContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;


public class TBContentCategoryDubboServiceImpl implements TBContentCategoryDubboService{
	
	@Resource
	TbContentCategoryMapper tbContentCategoryMapper;
	
	
	@Override
	public List<TbContentCategory> list(long id) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(id); // status 3 is normal one
		return tbContentCategoryMapper.selectByExample(example);
	}

	@Override
	public int insContentCategory(TbContentCategory cat) {
		return tbContentCategoryMapper.insertSelective(cat);
	}

	@Override
	public int updateContentCategory(TbContentCategory cat) throws Exception{
		int index =tbContentCategoryMapper.updateByPrimaryKeySelective(cat);
		if (1 != index){
			throw new Exception("Update exception");
		}
		return 1;
	}

	@Override
	public int insContentCategoryWithRollback(TbContentCategory cat) throws Exception{
		return tbContentCategoryMapper.insertSelective(cat);
	}

	@Override
	public TbContentCategory getCatById(long id) {
		return tbContentCategoryMapper.selectByPrimaryKey(id);
	}
	public List<TbContentCategory> getChildContentCategory(List<TbContentCategory> list){ // too get all ids 替规有问题
		List<TbContentCategory> my = new ArrayList<TbContentCategory>();
		for (TbContentCategory parent:list){
			if (parent.getIsParent()){
				my.addAll(list(parent.getId()));
			} else {
				my.add(parent);
			}
		}
		for (TbContentCategory l:my){
			list.add(l);
		}
		return list;
	}
	@Override
	public int delContentCategoryWithRollback(TbContentCategory cat) throws Exception { //find all the sub-node and update it.
		System.out.println("id get from dubbo:"+cat.getId());
		 TbContentCategory tbCat =tbContentCategoryMapper.selectByPrimaryKey(cat.getId());
		 int index = 0;
		 if (tbCat!=null&&tbCat.getIsParent()){
			 List<TbContentCategory> list = getChildContentCategory(list(cat.getId()));

			 for (TbContentCategory t:list){
				 t.setStatus(0);
				 index += tbContentCategoryMapper.updateByPrimaryKeySelective(t);
			 }
			 if (index !=list.size() ){
				 throw new Exception("Update exception.");
			 }
		 } else {
			 tbCat.setStatus(0);
			 index = tbContentCategoryMapper.updateByPrimaryKeySelective(tbCat);
			 if (index !=1){
				 throw new Exception("Update exception.");
			 }
		 }
		return 1;
	}
}
