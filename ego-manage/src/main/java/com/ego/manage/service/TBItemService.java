package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemParamItem;

public interface TBItemService {
	EasyUIDataGrid show(int page,int rows);
	int update(String ids,int status);
	int save(TbItem tbitem,String desc,String tbItemParamItem);
}
