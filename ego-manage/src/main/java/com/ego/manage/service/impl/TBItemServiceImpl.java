package com.ego.manage.service.impl;


import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TBItemDubboService;
import com.ego.manage.service.TBItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

@Service
public class TBItemServiceImpl implements TBItemService{
	
	@Reference
	private TBItemDubboService tbitemdubboservice; 

	@Override
	public EasyUIDataGrid show(int page, int rows) {
		// TODO Auto-generated method stub
		return tbitemdubboservice.show(page, rows);
	}

	@Override
	public int update(String ids,int status) {
		String[] idstr = ids.split(",");
		int index = 0;
		for (String id:idstr){
			TbItem record = new TbItem();
			record.setId(Long.parseLong(id));
			record.setStatus((byte)status);
			index += tbitemdubboservice.updateItemStatus(record);
		}
		if (idstr.length == index){
			return 1;
		}
		return 0;
	}

	@Override
	public int save(TbItem tbitem,String desc,String itemParams) {
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		Date date = new Date();
		long id = IDUtils.genItemId();
		tbitem.setId(id);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		tbItemDesc.setItemId(id);
		tbitem.setUpdated(date);
		tbitem.setCreated(date);
		
		TbItemParamItem param = new TbItemParamItem();
		param.setCreated(date);
		param.setUpdated(date);
		param.setItemId(id);
		System.out.println("Service:"+itemParams);
		param.setParamData(itemParams);
		
		
		int index = 0;
		try {
			index = tbitemdubboservice.insItemDesc(tbitem, tbItemDesc,param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return index;
	}
}
