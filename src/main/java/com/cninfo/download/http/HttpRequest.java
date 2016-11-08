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
}
