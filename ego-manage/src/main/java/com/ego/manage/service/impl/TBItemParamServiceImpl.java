package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.dubbo.service.TBItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TBItemParamService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;

@Service
public class TBItemParamServiceImpl implements TBItemParamService{
	@Reference
	private TBItemParamDubboService tBItemParamDubboServiceImpl;
	@Reference
	private TBItemCatDubboService tBItemCatDubboService;
	
	@Override
	public EasyUIDataGrid list(int page, int rows) {
		EasyUIDataGrid grid = tBItemParamDubboServiceImpl.list(page, rows);
		List<TbItemParam> tlist= (List<TbItemParam>) grid.getRows();
		List<TbItemParamChild> childList  = new ArrayList<TbItemParamChild>();
		for (TbItemParam t:tlist){
			TbItemParamChild child = new TbItemParamChild();
			child.setId(t.getId());
			child.setCreated(t.getCreated());
			child.setUpdated(t.getUpdated());
			child.setItemCatId(t.getItemCatId());
			child.setParamData(t.getParamData());
			child.setItemCatName(tBItemCatDubboService.selById(t.getItemCatId()).getName());
			childList.add(child);
		}
		grid.setRows(childList);
		grid.setTotal(childList.size());
		return grid;
	}

	@Override
	public EgoResult selById(Long catId) {
		 TbItemParam tbItemParam = tBItemParamDubboServiceImpl.selById(catId);
		 EgoResult er = new EgoResult();
		 if (null != tbItemParam){
			 er.setStatus(200);
			 er.setData(tbItemParam);
		 }
		 return er;
	}

	@Override
	public int insItemParam(TbItemParam tBItemParam) throws Exception {
		Date date = new Date();
		tBItemParam.setCreated(date);
		tBItemParam.setUpdated(date);
		return tBItemParamDubboServiceImpl.insItemParam(tBItemParam);
	}

	@Override
	public int delItemParam(String ids) throws Exception {
		return tBItemParamDubboServiceImpl.delItemParam(ids);
	}

}
