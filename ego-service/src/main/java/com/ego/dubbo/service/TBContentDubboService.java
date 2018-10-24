package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

public interface TBContentDubboService {
	EasyUIDataGrid list(long categoryId,int page,int rows);
	//int save(TbContent tbContent) throws Exception;
	int insContent(TbContent tbContent) throws Exception;
	int delContent(String ids) throws Exception;
	int updateContent(TbContent tBContent) throws Exception;
	List<TbContent> selByCount(int count,boolean sort);
}
