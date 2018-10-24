package com.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TBItemCatService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
@Service
public class TBItemCatServiceImpl implements TBItemCatService{
	
	@Reference
	TBItemCatDubboService tBItemCatDubboServiceImpl;

	@Override
	public PortalMenu showMenu() {
		List<TbItemCat> list = tBItemCatDubboServiceImpl.list(0l);
		PortalMenu pm = new PortalMenu();
		pm.setData(showAllMenu(list));
		return pm;
	}
	
	public List<Object> showAllMenu(List<TbItemCat> list){
		List<Object> nr = new ArrayList<Object>();
		for(TbItemCat l:list){
			if (l.getIsParent()){
				PortalMenuNode pmd = new PortalMenuNode();
				pmd.setU("/products/"+l.getId()+".html");
				pmd.setN("<ahref='/products/"+l.getId()+".html'>"+l.getName()+"</a>");
				pmd.setI(showAllMenu(tBItemCatDubboServiceImpl.list(l.getId())));
				nr.add(pmd);
			} else {
				nr.add("/products/"+l.getId()+".html|"+l.getName());
			}
		}
		return nr;
	}
}
