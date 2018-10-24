package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

public interface TBContentCategoryDubboService {
	List<TbContentCategory> list(long id);
	TbContentCategory getCatById(long id);
	int insContentCategory(TbContentCategory cat); //haven't throws exception
	int insContentCategoryWithRollback(TbContentCategory cat) throws Exception; //haven't throws exception
	int updateContentCategory(TbContentCategory cat) throws Exception;
	int delContentCategoryWithRollback(TbContentCategory cat) throws Exception;
}
