package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

public interface TBContentCategoryService {
	List<EasyUITree> list(long id);
	EgoResult insContentCategory(TbContentCategory cat);
	int insContentCategoryWithRollback(TbContentCategory cat) throws Exception;
	int updateContentCategory(TbContentCategory cat) throws Exception;
	TbContentCategory getCatById(long id);
	int delContentCategoryWithRollback(TbContentCategory cat) throws Exception;
}
