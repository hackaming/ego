package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;

public interface TBItemParamDubboService {
	EasyUIDataGrid list(int page,int rows);
	TbItemParam selById(Long id);
	int insItemParam(TbItemParam tbItemParam) throws Exception;
	int delItemParam(String ids) throws Exception;
}
