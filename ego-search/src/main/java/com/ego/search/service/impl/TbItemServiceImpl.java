package com.ego.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TBItemChild;
import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.dubbo.service.TBItemDescDubboService;
import com.ego.dubbo.service.TBItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.service.TbItemService;


@Service
public class TbItemServiceImpl implements TbItemService{
	
	private Logger logger = LoggerFactory.getLogger(TbItemServiceImpl.class);
	@Reference
	private TBItemDubboService tbItemDubbleServiceImpl;
	@Reference
	private TBItemCatDubboService tBItemCatDubboServiceImpl;
	@Reference
	private TBItemDescDubboService TBItemDescDubboServiceImpl;
	@Resource
	private HttpSolrClient solrClient;
	@Override
	

	public void init() {
		List<TbItem> items = tbItemDubbleServiceImpl.selAllbyStatus((byte)1);
		System.out.println("initialized started to Solr, please wait!!");
		logger.info("initialized started to Solr, please wait!!");
		int i=0;
		for (TbItem item:items){
			TbItemDesc itemDesc = TBItemDescDubboServiceImpl.selByItemId(item.getId());
			TbItemCat cat = tBItemCatDubboServiceImpl.selById(item.getCid());
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_sell_point", item.getSellPoint());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_image", item.getImage());
			doc.addField("item_category_name",cat.getName());
			doc.addField("item_desc", itemDesc.getItemDesc());			
			try {
				solrClient.add(doc);
				solrClient.commit();
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
			System.out.println("id:"+item.getId() + "is added."+i);
		}
		System.out.println("initialized finished to Solr, please check!");
		logger.info("initialized finished to Solr, please check!");
	}
	public void initializetest(){
		tbItemDubbleServiceImpl.selAllbyStatus((byte)1);
		TBItemDescDubboServiceImpl.selByItemId(1);
		tBItemCatDubboServiceImpl.selById(1l);
	}
	@Override
	public Map<String, Object> selByQuery(String query,int page,int rows) {
		SolrQuery params = new SolrQuery();
		params.setStart(rows*(page-1));
		params.setRows(rows);
		params.setQuery("item_keywords:"+query);
		params.addHighlightField("item_title");
		params.setHighlightSimplePre("<span style='color:red'");
		params.setHighlightSimplePost("</span>");
		SolrDocumentList listSolr = null;
		QueryResponse response = null;
		List<TBItemChild> listChil = null;
		try {
			response = solrClient.query(params);
			listChil = new ArrayList<>();
			listSolr = response.getResults();
			Map<String, Map<String, List<String>>> hh = response.getHighlighting();
			for (SolrDocument doc:listSolr){
				TBItemChild child = new TBItemChild();
				System.out.println(doc.getFieldValue("id"));
				long id = Long.parseLong(doc.getFieldValue("id").toString());
				child.setId(id); //get errors
				List<String> list = hh.get(doc.getFieldValue("id")).get("item_title");
				if (null != list && list.size() > 0){
					child.setTitle(list.get(0));
				} else {
					child.setTitle(doc.getFieldValue("item_title").toString());					
				}
				child.setPrice(Long.parseLong(doc.getFieldValue("item_price").toString()));
				Object image = doc.getFieldValue("item_images");
				child.setImages(null == image || image.equals("") ? new String[1]:image.toString().split(","));
				child.setSellPoint(doc.getFieldValue("item_sell_point").toString());
				
				listChil.add(child);
			}
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> resultMap = new HashMap<>();
		System.out.println("InServiceImpl:itemList"+listChil.size());
		resultMap.put("itemList", listChil);
		resultMap.put("totalPages", listSolr.getNumFound()%rows==0?listSolr.getNumFound()/rows:listSolr.getNumFound()/rows+1);

		return resultMap;
	}
	@Override
	public int add(TbItem item) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", item.getId());
		doc.addField("item_title", item.getTitle());
		doc.addField("item_sell_point", item.getSellPoint());
		doc.addField("item_price", item.getPrice());
		doc.addField("item_image", item.getImage());
		doc.addField("item_category_name",tBItemCatDubboServiceImpl.selById(item.getCid()).getName());
		doc.addField("item_desc", TBItemDescDubboServiceImpl.selByItemId(item.getId()).getItemDesc());		
		UpdateResponse response = solrClient.add(doc);
		if (response.getStatus() == 0){
			return 1;
		}
		return 0;
	}


}
