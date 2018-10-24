package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

public interface TBContentService {
	EasyUIDataGrid list(long categoryId,int page,int rows);
	EgoResult save(TbContent tbContent) throws Exception;
	EgoResult delContent(String ids) throws Exception;
	EgoResult updateContent(TbContent tBContent) throws Exception;
}
