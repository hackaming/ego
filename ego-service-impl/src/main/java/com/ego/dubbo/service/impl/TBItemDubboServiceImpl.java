package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TBItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;

//���ཻ��DUBBO�������ܼ�SERVICE,���˾ͽ���SPRING��
public class TBItemDubboServiceImpl implements TBItemDubboService {
	@Resource
	private TbItemMapper tbItemMapper;
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Override
	public EasyUIDataGrid show(int page, int rows) {

		PageHelper.startPage(page, rows);
		// GET ALL THE DATA
		List<TbItem> tbitems = tbItemMapper.selectByExample(new TbItemExample());
		// ���÷�ҳ����

		PageInfo<TbItem> pi = new PageInfo<TbItem>(tbitems);
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
		return datagrid;
	}

	@Override
	public int updateItemStatus(TbItem record) {
		// TODO Auto-generated method stub
		return tbItemMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insItem(TbItem record) {
		return tbItemMapper.insert(record); // ��ɶ���𣬻���һ��selective
	}

	@Override
	public int insItemDesc(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws Exception {
		int index = 0;
		try {  // ����д���ر���Ҫ��mybatis���ܻ����쳣������ץס�������������󣬲����׳��쳣
			// ���쳣֮��spring��ع������Ҳ��ᱨ�κ��쳣������ֹ���� ��ִ��
			// Ϊ���ܹ��ع�������������ʽ��������������ã���	rollback-for="java.lang.Exception"
			index += tbItemMapper.insertSelective(tbItem);
			index += tbItemDescMapper.insertSelective(tbItemDesc);
			index += tbItemParamItemMapper.insertSelective(tbItemParamItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (3 == index) {
			return 1;
		} else {
			throw new Exception();
		}
	}

	@Override
	public List<TbItem> selAllbyStatus(byte status) {
		TbItemExample example = new TbItemExample();
		example.createCriteria().andStatusEqualTo(status);
		return tbItemMapper.selectByExample(example);
	}

	@Override
	public TbItem selByItemId(long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}

}
