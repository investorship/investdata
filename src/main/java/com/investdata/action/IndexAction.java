package com.investdata.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;

import redis.clients.jedis.Jedis;

import com.investdata.common.BaseAction;
import com.investdata.common.Const;
import com.investdata.dao.po.FinanceIndexInfo;
import com.investdata.dao.po.SearchIndex;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.investdata.utils.StringUtils;

/**
 * @author HaiLong.Guo
 * @since 20160519
 * 跳转到首页Action
 */
public class IndexAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(IndexAction.class);
	private Map<String,Object> application = null;
	private Map<String,Object> request = null;
	private static String stocksItems;
	private static Map<String,String> stockCodeMapping;
	private static List<FinanceIndexInfo> parentsIndexList;
	private static Jedis jedis = RedisCache.getJedis();
	
	public String execute() throws Exception {
		
		//以下appliaction里的数据在启动阶段已经放入，目的是防止：如果不访问首页，数据就无法使用的情况
		
		/*if (StringUtils.isEmpty(stocksItems) || stockCodeMapping == null || parentsIndexList == null) {
			//准备从缓存获取数据
			stocksItems = jedis.get(Const.STOCK_SEARCH_LIST);
			Set<String> mappingKeys  = jedis.hkeys(Const.STOCK_CODE_MAPPING);
			stockCodeMapping = new HashMap<String,String>();
			for (String key : mappingKeys) {
				List<String> value = jedis.hmget(Const.STOCK_CODE_MAPPING, key);
				stockCodeMapping.put(key, value.get(0));
			}
			
			application.put("stocksItems", stocksItems);
			application.put("stockCodeMapping", stockCodeMapping); 
			
			//加载财务指标
			byte[] in = jedis.get(Const.FINANCEINDEXINFO_KEY.getBytes());
			parentsIndexList = ObjectsTranscoder.deserialize(in);  
			application.put("parentsIndexList", parentsIndexList);
		}*/
		
		//首页的搜索指数
		byte[] searchIndexIn = jedis.get(Const.SEARCHINDEX_KEY.getBytes());
		List<SearchIndex> searchIndexList = ObjectsTranscoder.deserialize(searchIndexIn);
		request.put("searchIndexList", searchIndexList);
		
		return INPUT;
	}

	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
}
