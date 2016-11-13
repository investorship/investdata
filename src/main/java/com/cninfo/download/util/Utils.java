package com.cninfo.download.util;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cninfo.download.http.HttpRequest;
import com.cninfo.download.vo.FinanceDataVo;
import com.cninfo.download.vo.OrgVo;

public class Utils {
	private static String code;
	private static String year;
	
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
	
	
	public static void getFinanceDataZip(String url, JSONObject jsonParam,String fileDir) throws Exception {
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
		
		getFile(url,fdv,fileDir); //利润表
		
		Thread.sleep(2000);
		
		fdv.setType("fzb");
		getFile(url, fdv,fileDir); //负债表
		
		Thread.sleep(2000);
		
		fdv.setType("llb"); //现金表
		getFile(url, fdv,fileDir); //负债表
		
	}
	
	
	private static void getFile(String url,FinanceDataVo fdv,String fileDir) throws Exception {
		code = fdv.getCode();
		String market = fdv.getMarket();
		String type = fdv.getType();
		String fileName = code + "_" + market + "_" + type + ".zip";
		fileName = fileDir + fileName;
		
		int times = 0;
		while (times < 3) {
			HttpEntity respEntity = HttpRequest.httpPost(url, fdv);
			long length = respEntity.getContentLength();
			System.err.println("length:" + length);
			if (respEntity != null) {
				InputStream is = respEntity.getContent();
				FileOutputStream fos = new FileOutputStream(new File(fileName));
				int d = -1;
				while ((d= is.read() )!= -1) {
					fos.write(d);					
				}
				fos.flush();
				fos.close();
				
				File checkFile = new File(fileName);
				if (checkFile != null && checkFile.isFile() && checkFile.length() > 0) {
					System.err.println("写入完成！fileName=" + fileName + " 文件大小：" + getFileSize(checkFile));
					break;					
				} else {
					times ++;
				}
			} 
		}
		
		if (times >=3) {
			System.err.println("被忽略的文件[" + fileName + "]");
		}
		
	}
	
	//获取文件大小
	public static String getFileSize(File file) throws Exception {
		long fileS = 0;
		String size = "";
		FileInputStream fis = null;
		if (file != null && file.exists()) {
			fis = new FileInputStream(file);
			fileS= fis.available();
	        DecimalFormat df = new DecimalFormat("#0.00");
	        if (fileS < 1024) {
	            size = df.format((double) fileS) + "B";
	        } else if (fileS < 1048576) {
	            size = df.format((double) fileS / 1024) + "K";
	        } else if (fileS < 1073741824) {
	            size = df.format((double) fileS / 1048576) + "M";
	        } else {
	            size = df.format((double) fileS / 1073741824) + "G";
	        }
		}else {
			size = "0B";
		}
		return size;
	}
	
	//解压zip文件
	public static void unZipFile(String fileDir) throws Exception {
		File fileList = new File(fileDir);
		if (fileList != null && fileList.isDirectory()) {
			File[] files = fileList.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) { //只查询 *.zip文件
					String  fileName = file.getName();
					if (fileName.endsWith(".zip")) {
						return true;
					} else {
						return false;						
					}
				}
			});
			
			for(File file : files) {
				ZipFile zipFile = new ZipFile(file);
				for(Enumeration entries = zipFile.getEntries();entries.hasMoreElements();) {
					ZipEntry entry = (ZipEntry)entries.nextElement(); 
					String fileName = entry.getName();
					String[] fileNameArry = fileName.split("\\_");
					String fileType = fileNameArry[1];
					if ("llb".equals(fileType)) {
						parseLLB(zipFile,entry);
					}else if ("fzb".equals(fileType)) {
//						parseFZB(zipFile,entry);
					} else if ("lrb".equals(fileType)) {
//						parseLRB(zipFile,entry);
					}else {
						throw new RuntimeException("解析的zip包里面文件不合法");
					}
					
				}
				
			}
			
		}
	}
	
	//解析流量表
	private static void parseLLB(ZipFile zipFile, ZipEntry entry) throws Exception{
		InputStream is = zipFile.getInputStream(entry);
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is,"gbk")); //防止乱码
		String strLine = "";
		String entryFileName = entry.getName();
		String reportYear = entryFileName.split("\\_")[3];
		int index = reportYear.indexOf(".");
		reportYear = reportYear.substring(0, index);
		year = reportYear;
		int i = 0;
		String[] columNameArry = null;
		String[] columValArray  = null;
		while((strLine = bReader.readLine()) != null) {
			if (i == 0) {
				columNameArry = strLine.split("\\,",-1);
			}else {
				columValArray = strLine.split("\\,",-1);
				if (columValArray[5].contains(reportYear + "-12")) { //只要年度报表 "2015-12-31" 包含 "2015-12"
					genSQL("LLB",entryFileName,columNameArry,columValArray);
				}
			}
			i++;
			
		}
		
	}
	
	//解析负债表
	private static void parseFZB(ZipFile zipFile, ZipEntry entry) throws Exception{
		InputStream is = zipFile.getInputStream(entry);
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is,"gbk")); //防止乱码
		String strLine = "";
		String entryFileName = entry.getName();
		String reportYear = entryFileName.split("\\_")[3];
		int index = reportYear.indexOf(".");
		reportYear = reportYear.substring(0, index);
		year = reportYear;
		int i = 0;
		String[] columNameArry = null;
		String[] columValArray  = null;
		while((strLine = bReader.readLine()) != null) {
			if (i == 0) {
				columNameArry = strLine.split("\\,",-1);
			}else {
				columValArray = strLine.split("\\,",-1);
				if (columValArray[4].contains(reportYear + "-12")) { //只要年度报表 "2015-12-31" 包含 "2015-12"
					genSQL("FZB",entryFileName,columNameArry,columValArray);
				}
			}
			i++;
			
		}
		
	}
	
	//解析利润表
	private static void parseLRB(ZipFile zipFile, ZipEntry entry) throws Exception{
		InputStream is = zipFile.getInputStream(entry);
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is,"gbk")); //防止乱码
		String strLine = "";
		String entryFileName = entry.getName();
		System.err.println("----解析文件:" + entryFileName + "------");
		while((strLine = bReader.readLine()) != null) {
//			genSQL("LRB",strLine,entryFileName);
		}
		
	}
	
	
	//生成SQL语句
	private static void  genSQL(String type,String fileName,String[] columName, String[] columVal) throws Exception {
		int len = columName.length;
		StringBuilder sql = new StringBuilder();
		if ("LLB".equals(type)) {
			for(int i=0; i<len; i++) {
				sql.append("insert into t_cashflow_sheet values ('").append(code).append("',").append(year).append(",");
				if("经营活动产生的现金流量净额".equals(columName[i])) {
					sql.append(columVal[i]).append(",");
				} else if (columName[i].endsWith("现金及现金等价物净增加额")) {
					sql.append(columVal[i]).append(",");
				}else {
					
				}
				sql.append("1,'").append(new Timestamp(System.currentTimeMillis())).append("','admin'))").append("\n");
			}
			System.out.println(sql);
		} else if ("FZB".equals(type)) {
			for(int i=0; i<len; i++) {
				System.err.println("名称：" + columName[i] + "值：" + columVal[i]);
			}
		}
		
	}
	
	

	public static void main(String[] args) throws Exception {
		Utils.unZipFile("d:\\job\\");
	}
	
	
	
}
