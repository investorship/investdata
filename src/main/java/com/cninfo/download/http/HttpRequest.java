package com.cninfo.download.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cninfo.download.util.Utils;


public class HttpRequest {

	//http request Method:post
	public static HttpEntity httpPost(String url, Object obj) throws Exception {
		HttpClient httpclient = new DefaultHttpClient(); // 创建默认的httpClient实例.
		HttpPost httppost = new HttpPost(url);	// 创建httppost
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // 创建参数队列
		Utils.convertFormParam(formparams, obj);
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httppost.setEntity(uefEntity);
		
		HttpResponse response;
		response = httpclient.execute(httppost);
		HttpEntity respEntity = response.getEntity();
		
		return respEntity;
	}
	
	
	public static void main(String[] args) throws Exception {
		HttpEntity respEntity = httpPost("http://www.cninfo.com.cn/information/brief/szmb000985.html",null);
		String stockInfo = EntityUtils.toString(respEntity,"GBK");
		System.out.println(stockInfo);
		Document doc = Jsoup.parse(stockInfo);
		Elements trs = doc.select("table").select("tr");
		
		String ipotime = "0";
		String ipostocks = "0";
		String issuedPE = "0";
		String issuedprice = "0";
		String address = "";
		String compywebsite = "0";
		String reportaddress = "http://www.cninfo.com.cn/information/companyinfo_n.html?fulltext?";
		String phone = "0";
		String legaler = "";
		
		StringBuilder updateSQL = new StringBuilder();
		
		for (int i=1; i<trs.size(); i++) {
			Elements tds = trs.get(i).select("td");
            for(int j = 0;j<tds.size();j++){
            	if (j % 2 ==0) continue;
                String text = tds.get(j).text();
//                System.out.println("j=" + j +" i=" + i +" text=" + text);
                
                if (i == 13) {
                	ipotime = text;
                } else if (i == 15) {
                	ipostocks = text;
                	ipostocks = ipostocks.replaceAll(",", "");
                } else if (i ==16) {
                	issuedprice = text;
                } else if (i == 17) {
                	issuedPE = text;
                } else if (i == 3) {
                	address = text;
                } else if (i == 12) {
                	compywebsite = text;
                } else if (i ==10) {
                	phone = text;
                } else if (i == 5) {
                	legaler = text;
                }
                
            }
		}
		
		updateSQL.append("update t_stocks set ipotime='").append(ipotime).append("',").append("ipostocks=").append(ipostocks).append(",");
		updateSQL.append("issuedPE=").append(issuedPE).append(",").append("issuedprice=").append(issuedprice).append(",");
		updateSQL.append("address='").append(address).append("',").append("compywebsite='").append(compywebsite).append("',");
		updateSQL.append("phone='").append(phone).append("',").append("legaler='").append(legaler).append("',");
		updateSQL.append("reportaddress='").append(reportaddress).append("'");
		updateSQL.append(" where code='").append("000985'");
		
		System.out.println(updateSQL.toString());
	}
	
}
