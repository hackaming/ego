package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TBItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TBItemParamDubboServiceImpl implements TBItemParamDubboService{
	@Resource
	TbItemParamMapper tbItemParamMapper;
	@Override
	public EasyUIDataGrid list(int page,int rows) {
		PageHelper.startPage(page, rows);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		PageInfo<TbItemParam> pi = new PageInfo<TbItemParam>(list);
		EasyUIDataGrid grid = new EasyUIDataGrid();
		grid.setTotal(pi.getTotal());
		grid.setRows(pi.getList());
		return grid;
	}
	public TbItemParam selById(Long id) {
		// TODO Auto-generated method stub
		return tbItemParamMapper.selectByPrimaryKey(id);
	}
	public int insItemParam(TbItemParam tbItemParam) throws Exception{
		int index = tbItemParamMapper.insertSelective(tbItemParam);
		if (1 != index){
			throw new Exception("ItemParam insert failure!");
		}
		return index;
	}
	@Override
	public int delItemParam(String ids) throws Exception {
		String[] id = ids.split(",");
		EgoResult  er = new EgoResult();
		int index = 0;
		try{
			for (String idx:id){
				index += tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(idx));
			}
		} catch (Exception e) {
			e.printStackTrace();
			er.setMessage(e.getMessage());
		}
		
		if (1 != index){
			throw new Exception("Delete failure!");
		}
		return 1;
	}

}
