package com.investdata.common.interceptor;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.investdata.common.Const;
import com.investdata.dao.po.SearchIndex;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import redis.clients.jedis.Jedis;

/**
 * 搜索指数拦截器，主要用于统计首页热搜榜 股票和财务指标的搜索频率
 * 
 * @author Administrator
 * 
 */
public class SearchIndexInterceptor extends AbstractInterceptor {
	Logger _log = Logger.getLogger(SearchIndexInterceptor.class);
	
	private static final long serialVersionUID = 1L;
//	private static List<Map<String,String>> stockIndexList = new ArrayList<Map<String,String>>(); //股票搜索指数
//	private static List<Map<String,String>> financeIndexList = new ArrayList<Map<String,String>>(); //财务指标指数
	
	private static StockIndexSort sis = new StockIndexSort();
	
	private static HashMap<String,String> stockCodeMapping = null;
	
	private static Set<String> mappingKeys  = null;
	static  {
		Jedis jedis = RedisCache.getJedis();
		mappingKeys = jedis.hkeys(Const.STOCK_CODE_MAPPING);
		stockCodeMapping = new HashMap<String,String>();
		for (String key : mappingKeys) {
			List<String> value = jedis.hmget(Const.STOCK_CODE_MAPPING, key);
			stockCodeMapping.put(key, value.get(0));
		}
		
		jedis.close();
	}


	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		Jedis jedis = RedisCache.getJedis();
		byte[] searchIndexIn = jedis.get(Const.SEARCHINDEX_KEY.getBytes());		
		List<SearchIndex> searchIndexList = ObjectsTranscoder.deserialize(searchIndexIn);
		
		ActionContext actionContext = arg0.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		String url = request.getRequestURI();
		
		if (url.contains("stock.action")) {
			String[] params = request.getParameterValues("keyword"); //股票代码
			String searchCode = params[0];
			if (mappingKeys.contains(searchCode)) {
				
				SearchIndex si = new SearchIndex();
				si.setCode(searchCode);
				si.setName(stockCodeMapping.get(searchCode));
				
				
				int removeFlag = 0;
				
				if (searchIndexList.contains(si)) { //访问次数 + 1
					int index = searchIndexList.indexOf(si);
					SearchIndex tempSI = searchIndexList.get(index);
					tempSI.setCount(tempSI.getCount() + 1);
				} else {
					removeFlag = 1;
					si.setCount(1);
					searchIndexList.add(si);
				}

				
				//按照访问次数排序
				Collections.sort(searchIndexList,sis);
				
				if (removeFlag == 1) {
					searchIndexList.remove(searchIndexList.size() -1);  //取访问量最高的前8名					
				}
			}
		}
		
		for (SearchIndex si : searchIndexList) {
			_log.info(String.format("%s:访问次数[%s]", si.getName(),si.getCount()));
		}
		
		jedis.set(Const.SEARCHINDEX_KEY.getBytes(), ObjectsTranscoder.serialize(searchIndexList));
		jedis.close();
		return arg0.invoke();
	}
	
	


}


class StockIndexSort implements Comparator<SearchIndex> {
	@Override
	public int compare(SearchIndex m1, SearchIndex m2) {
		if (m1 == null || m2 == null) {
			return 0;
		} else {
			int m1Count = m1.getCount();
			int m2Count = m2.getCount();
			return m1Count > m2Count ? -1 : 1;
		}
	}
	
}
