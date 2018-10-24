package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TBContentCategoryDubboService;
import com.ego.manage.service.TBContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Service
public class TBContentCategoryServiceImpl implements TBContentCategoryService{
	@Reference
	private TBContentCategoryDubboService tBContentCategoryDubboServiceImpl;
	@Override
	public List<EasyUITree> list(long id) {
		List<TbContentCategory> list = tBContentCategoryDubboServiceImpl.list(id);
		List<EasyUITree> tr = new ArrayList<EasyUITree> ();
		for (TbContentCategory cat:list){
			EasyUITree tree = new EasyUITree();
			tree.setId(cat.getId());
			tree.setState(cat.getIsParent()?"closed":"open");
			tree.setText(cat.getName());
			tr.add(tree);
		}
/*		EasyUIDataGrid grid = new EasyUIDataGrid();
		grid.setRows(list);
		grid.setTotal(list.size());*/
		return tr;
	}
	@Override
	public EgoResult insContentCategory(TbContentCategory cat) { //Without rollback
		EgoResult er = new EgoResult();
		List<TbContentCategory> list = tBContentCategoryDubboServiceImpl.list(cat.getParentId()); //needs to find the parent node and set the is parent to true
		for(TbContentCategory c:list){
			if (c.getName().equals(cat.getName())){
				er.setMessage("Message already exist");
				return er; //if ound the same object, don't insert
			}
		}
		Date date = new Date();
		long id = IDUtils.genItemId();
		cat.setCreated(date);
		cat.setUpdated(date);
		cat.setId(id);
		cat.setIsParent(false);
		cat.setSortOrder(1);
		cat.setStatus(1);
		int index  = tBContentCategoryDubboServiceImpl.insContentCategory(cat);
		if (index != 1){
			er.setMessage("Can't insert into DB");
			return er;
		}
		TbContentCategory parent = new TbContentCategory();
		parent.setId(cat.getParentId());
		parent.setIsParent(true);
		try {
			if (updateContentCategory(parent) == 1){
				er.setStatus(200);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		er.setData(cat);
		// needs to set data here
		return er;
	}

	@Override
	public int updateContentCategory(TbContentCategory cat) throws Exception { //¬ﬂº≠”–Œ Ã‚
		 int index = tBContentCategoryDubboServiceImpl.updateContentCategory(cat);
		 if (1 != index){
			 throw new Exception("Update exception.");
		 } 
		 return 1;
	}
	@Override
	public int insContentCategoryWithRollback(TbContentCategory cat) throws Exception {
		List<TbContentCategory> list = tBContentCategoryDubboServiceImpl.list(cat.getParentId()); //needs to find the parent node and set the is parent to true
		for(TbContentCategory c:list){
			if (c.getName().equals(cat.getName())){
				return 1; //if ound the same object, don't insert
			}
		}
		Date date = new Date();
		long id = IDUtils.genItemId();
		cat.setCreated(date);
		cat.setUpdated(date);
		cat.setId(id);
		cat.setIsParent(false);
		cat.setSortOrder(1);
		cat.setStatus(1);
		int index  = tBContentCategoryDubboServiceImpl.insContentCategory(cat);
		if (index != 1){
			throw new Exception("Can't insert into DB");
			//er.setMessage("Can't insert into DB");
			//return er;
		}
		TbContentCategory parent = new TbContentCategory();
		parent.setId(cat.getParentId());
		parent.setIsParent(true);
		if (updateContentCategory(parent) != 1){
			throw new Exception("Can't insert into DB");
		}
		return 1;
	}
	@Override
	public TbContentCategory getCatById(long id) {
		return tBContentCategoryDubboServiceImpl.getCatById(id);
	}
	@Override
	public int delContentCategoryWithRollback(TbContentCategory cat) throws Exception {
		return tBContentCategoryDubboServiceImpl.delContentCategoryWithRollback(cat);
	}
}
