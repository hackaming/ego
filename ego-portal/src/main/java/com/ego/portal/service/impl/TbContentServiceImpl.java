package com.ego.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TBContentDubboService;
import com.ego.jedis.service.JedisDao;
import com.ego.jedis.service.impl.JedisDaoImpl;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;


@Service
public class TbContentServiceImpl implements TbContentService {
	
	@Reference
	private TBContentDubboService tBContentDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${key}")
	private String key;
	@Override
	public String showBigAdvertisement() {
		if (jedisDaoImpl.exist(key)){
			return jedisDaoImpl.get(key).toString();
		}
		
		if (tBContentDubboServiceImpl == null){
			System.out.println("Service tBContentDubboServiceImpl is null!");
		}
		List<TbContent> list = tBContentDubboServiceImpl.selByCount(6, true);
		List<Map<String,Object>> listMap = new ArrayList<>();
		for (TbContent tc:list){
			//var data = [{"srcB":"http://image.ego.com/images/2015/03/03/2015030304360302109345.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304360302109345.jpg","widthB":550,"href":"http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/2015030304353109508500.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304353109508500.jpg","widthB":550,"href":"http://sale.jd.com/act/UMJaAPD2VIXkZn.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/2015030304345761102862.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304345761102862.jpg","widthB":550,"href":"http://sale.jd.com/act/UMJaAPD2VIXkZn.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/201503030434200950530.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/201503030434200950530.jpg","widthB":550,"href":"http://sale.jd.com/act/kj2pmwMuYCrGsK3g.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/2015030304333327002286.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304333327002286.jpg","widthB":550,"href":"http://sale.jd.com/act/xcDvNbzAqK0CoG7I.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/2015030304324649807137.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304324649807137.jpg","widthB":550,"href":"http://sale.jd.com/act/eDpBF1s8KcTOYM.html?cpdad=1DLSUE","heightB":240}];			
			Map<String,Object> map = new HashMap<>();
			map.put("srcB", tc.getPic2());
			map.put("height", "240");
			map.put("alt", "");
			map.put("width", "670");
			map.put("src", tc.getPic());
			map.put("widthB", "550");
			map.put("href", tc.getUrl());
			map.put("heightB", "240");
			listMap.add(map);
		}
		String str = JsonUtils.objectTOString(listMap);
		jedisDaoImpl.set(key, str);
		return str;
	}

}
