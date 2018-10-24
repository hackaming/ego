package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

public interface TBItemDubboService {
	EasyUIDataGrid show(int page,int rows);
	int updateItemStatus(TbItem record);
	int insItem(TbItem record);
	/**
	 * ͬʱ������Ʒ����Ʒ������
	 * @param tbItem
	 * @param tbItemDesc
	 * @return
	 * @throws Exception
	 */
	int insItemDesc(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws Exception;
	
	List<TbItem> selAllbyStatus(byte status);
}
