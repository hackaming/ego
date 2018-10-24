package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

public interface TBItemParamService {
	EasyUIDataGrid list(int page,int rows);
	public EgoResult selById(Long catId);
	int insItemParam(TbItemParam tBItemParam) throws Exception;
	int delItemParam(String ids) throws Exception;
}
