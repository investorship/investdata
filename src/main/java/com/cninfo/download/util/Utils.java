package com.cninfo.download.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cninfo.download.http.HttpRequest;
import com.cninfo.download.vo.FinanceDataVo;
import com.cninfo.download.vo.OrgVo;

public class Utils {
	// 使用反射机制填充参数。
	public static void convertFormParam(List<NameValuePair> nvp, Object obj)
			throws Exception {
		if (obj == null)
			return;
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
					clazz);
			Method getMethod = pd.getReadMethod();// 获得get方法
			String fieldName = field.getName();
			String fieldVal = (String) getMethod.invoke(obj);

			if (fieldVal != null) {
				// System.out.println("new BasicNameValuePair(" + fieldName +","
				// + fieldVal+")");
				nvp.add(new BasicNameValuePair(fieldName, fieldVal));
			}
		}
	}

	// 获取带组织机构代码的json对象
	public static JSONObject getOrgInfoJsonObj(String code, String url)
			throws Exception {

		int i = 0;
		String orgId = "";
		String startTime = "";
		String name = "";
		JSONObject jsonObj = null;
		while (i < 3) { // 获取失败后，连续获取3次
			OrgVo ov = new OrgVo();
			ov.setKeyWord(code);
			ov.setMaxNum("10");
			ov.setHq_or_cw("2");

			jsonObj = Utils.httpStr2JsonObj(url, ov);
			orgId = jsonObj.getString("orgId");
			startTime = jsonObj.getString("startTime");
			name = jsonObj.getString("zwjc");
			if (orgId != "") {
				System.err.println("代码： " + code + "  名称:" + name + "  组织ID："+ orgId);

				if ("6".equals(code)) { // 区分 上海 or 深圳 市场
					jsonObj.put("market", "sh");
				} else {
					jsonObj.put("market", "sz");
				}

				break;
			} else {
				i++;
			}

			if (i >= 3 && ("".equals(orgId) || "".equals(startTime))) {
				System.err.println("获取失败的代码code=" + code + "name=" + name);
				break; // 略过当前stock
			}
		}

		return jsonObj;

	}

	// 将http响应字符串转换为json对象。
	public static JSONObject httpStr2JsonObj(String url, Object obj)
			throws Exception {
		HttpEntity resultEntity = HttpRequest.httpPost(url, obj);
		JSONArray jsonArray = new JSONArray(EntityUtils.toString(resultEntity));
		JSONObject jsonObj = (JSONObject) jsonArray.get(0);
		return jsonObj;
	}
	
	
	public static void getFinanceDataZip(String url, JSONObject jsonParam) throws Exception {
		if (url == null || jsonParam == null) {
			throw new RuntimeException("参数非法");
		}
		
		String code = jsonParam.getString("code");
		String market = jsonParam.getString("market");
		String orgId = jsonParam.getString("orgId");
		String startTime = jsonParam.getString("startTime");
		
		FinanceDataVo fdv = new FinanceDataVo();
		fdv.setCode(code);
		fdv.setMarket(market);
		fdv.setType("lrb");
		fdv.setOrgid(orgId);
		fdv.setMinYear(startTime);
		fdv.setMaxYear("2016");
		
		getFile(url,fdv); //利润表
		
		fdv.setType("fzb");
		getFile(url, fdv); //负债表
		
		fdv.setType("llb"); //现金表
		getFile(url, fdv); //负债表
		
	}
	
	
	private static void getFile(String url,FinanceDataVo fdv) throws Exception {
		String code = fdv.getCode();
		String market = fdv.getMarket();
		String type = fdv.getType();
		String fileName = code + "_" + market + "_" + type + ".zip";
		fileName = "d:\\job\\" + fileName;
		
		int times = 0;
		while (times < 3) {
			HttpEntity respEntity = HttpRequest.httpPost(url, fdv);
			long length = respEntity.getContentLength();
			System.err.println("length:" + length);
			if (respEntity != null && length > 0) {
				InputStream is = respEntity.getContent();
				FileOutputStream fos = new FileOutputStream(new File(fileName));
				int d = -1;
				while ((d= is.read() )!= -1) {
					System.err.print(d);
					fos.write(d);					
				}
				fos.flush();
				fos.close();
				System.err.println("写入完成！fileName=" + fileName);
				break;
			} else {
				Thread.sleep(200); //等待200ms后再次获取
				times ++;
			}
		}
		
		if (times >=3) {
			System.err.println("被忽略的文件[" + fileName + "]");
		}
		
	}
}
