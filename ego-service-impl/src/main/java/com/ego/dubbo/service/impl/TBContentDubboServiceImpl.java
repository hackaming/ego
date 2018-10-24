package com.ego.dubbo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TBContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TBContentDubboServiceImpl implements TBContentDubboService{
	
	@Resource
	private TbContentMapper tbContentMapperImpl; 
	
	@Override
	public EasyUIDataGrid list(long categoryId, int page, int rows) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapperImpl.selectByExample(example);
		PageInfo<TbContent> pi = new PageInfo<>(list);
		EasyUIDataGrid grid = new EasyUIDataGrid();
		grid.setRows(pi.getList());
		grid.setTotal(pi.getTotal());
		return grid;
	}
	@Override
	public int insContent(TbContent tbContent) throws Exception{
		int index = 0;
		index = tbContentMapperImpl.insertSelective(tbContent);
		if (1 != index){
			throw new Exception("Content can't insert into DB");
		}
		return 1;
	}
	@Override
	public int delContent(String ids) throws Exception {
		
		String[] idlist = ids.split(",");
		int index = 0;
		for(String id:idlist){
			index += tbContentMapperImpl.deleteByPrimaryKey(Long.parseLong(id));
		}
		if (idlist.length != index){
			throw new Exception("Delete failure!");
		}
		return 1;
	}
	@Override
	public int updateContent(TbContent tBContent) throws Exception {
		TbContent tBInitial = tbContentMapperImpl.selectByPrimaryKey(tBContent.getId());
		tBContent.setCreated(tBInitial.getCreated());
		Date date = new Date();
		tBContent.setUpdated(date);
		int index = 0;
		index = tbContentMapperImpl.updateByPrimaryKey(tBContent);
		if (1 != index){
			throw new Exception("Can't update");
		}
		return 1;
	}
	@Override
	public List<TbContent> selByCount(int count, boolean sort) {
		if (tbContentMapperImpl == null){
			System.out.println("Dubbo tbContentMapperImpl is null!");
		}
		TbContentExample example = new TbContentExample();
		if (sort){
			example.setOrderByClause("updated desc");
		}
		if (0 != count){
			PageHelper.startPage(1, count);
			PageInfo<TbContent> pi = new PageInfo<TbContent>();
			pi.setList(tbContentMapperImpl.selectByExampleWithBLOBs(example));
			return pi.getList();
		} else {
			return tbContentMapperImpl.selectByExampleWithBLOBs(example); 
		}
	}


}
