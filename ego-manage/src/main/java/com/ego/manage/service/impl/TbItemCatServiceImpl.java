package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	TBItemCatDubboService tbItemCatDubboServiceImpl;
	@Override
	public List<EasyUITree> list(long id) {
		// TODO Auto-generated method stub
		List <TbItemCat> list = tbItemCatDubboServiceImpl.list(id);
		List<EasyUITree> uiTree = new ArrayList<EasyUITree>();
		for (TbItemCat itemcat:list){
			EasyUITree t = new EasyUITree();
			t.setId(itemcat.getId());
			t.setText(itemcat.getName());
			t.setState(itemcat.getIsParent()?"closed":"open");
			uiTree.add(t);
		}
		return uiTree;
	}

}
