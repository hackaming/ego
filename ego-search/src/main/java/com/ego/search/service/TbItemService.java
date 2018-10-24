package com.ego.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.ego.pojo.TbItem;

public interface TbItemService {
	void init();
	Map<String,Object> selByQuery(String query,int page,int rows);
	int add(TbItem item) throws SolrServerException, IOException;
}
