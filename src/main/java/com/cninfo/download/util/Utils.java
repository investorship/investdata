package com.cninfo.download.util;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cninfo.download.http.HttpRequest;
import com.cninfo.download.vo.FinanceDataVo;
import com.cninfo.download.vo.OrgVo;

public class Utils {
	private static String code;
	private static String year;
	
	
	private static StringBuilder sql = new StringBuilder();
	private static StringBuilder updateSQL = new StringBuilder();
	private static StringBuilder gendataSQL = new StringBuilder(); //综合数据表项内容，由于比较特殊，字段掺杂在利润表中，所以单独处理。

	
	private static StringBuilder financeDataSQL;
	
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
		
//		fileDir = fileDir + code;
		
		File dirFile = new File(fileDir);
		
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		
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
				is.close();
				
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
	public static void unZipFile(String fileDir, String code) throws Exception {
		File fileList = new File(fileDir);
		financeDataSQL = new StringBuilder();
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
			
			for(File file : files) { //解析文件生成SQL
				ZipFile zipFile = new ZipFile(file);
				for(Enumeration entries = zipFile.getEntries();entries.hasMoreElements();) {
					ZipEntry entry = (ZipEntry)entries.nextElement(); 
					String fileName = entry.getName();
					String[] fileNameArry = fileName.split("\\_");
					String fileType = fileNameArry[1];
					if ("llb".equals(fileType)) {
						parseLLB(zipFile,entry);
					}else if ("fzb".equals(fileType)) {
						parseFZB(zipFile,entry);
					} else if ("lrb".equals(fileType)) {
						parseLRB(zipFile,entry);
					}else {
						throw new RuntimeException("解析的zip包里面文件不合法");
					}
					
				}
				
			}
			
			
			financeDataSQL.append(sql).append(gendataSQL).append(updateSQL); //四大报表SQL汇总 保证Insert在前，update在后
			
			//每只股票的SQL写入一个以股票代码命名的sql文件中。
			BufferedWriter  sqlWriter = new BufferedWriter(new FileWriter(new File("D:\\job\\sql\\" + code + ".sql")));
			sqlWriter.write(financeDataSQL.toString());
			sqlWriter.flush();
			sqlWriter.close();
			
			//写入完成之后，清空保存SQL的 StringBuffer
			financeDataSQL.delete(0, financeDataSQL.length());
			sql.delete(0, sql.length());
			gendataSQL.delete(0, gendataSQL.length());
			updateSQL.delete(0, updateSQL.length());
			
			
			//删除文件
			File[] deleFiles = fileList.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(".zip")) return true;
					else return false;
				}
			});
			
			for (File file : deleFiles) {
				
				if (!file.isDirectory()) {
					file.getAbsoluteFile().delete();				
				}
//				System.err.println("删除文件" + file.getName());
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
		bReader.close();
		is.close();
		
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
		
		bReader.close();
		is.close();
		
	}
	
	//解析利润表
	private static void parseLRB(ZipFile zipFile, ZipEntry entry) throws Exception{
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
					genSQL("LRB",entryFileName,columNameArry,columValArray);
				}
			}
			i++;
			
		}
		
		bReader.close();
		is.close();
	}
	
	
	//生成SQL语句
	private static void  genSQL(String type,String fileName,String[] columName, String[] columVal) throws Exception {
		int len = columName.length;
		if ("LLB".equals(type)) {
			String opera_active_cash = "0";
			String cash_and_cashequ = "0";
			String lntang_assets_amortize = "0"; //资产负债表中的无形资产摊销  在流量表中。
			String fixed_ass_depre = "0";// 利润表中的 固定资产折旧 在流量表中
			String long_pre_amort = "0"; //利润表中的 长期待摊费用摊销 在流量表中
			sql.append("insert into t_cashflow_sheet values ('").append(code).append("',").append(year).append(",");
			for(int i=0; i<len; i++) {
				
				String filterStr = columVal[i];
				if("".equals(filterStr.trim())) { 
					continue;
				}
				
				if("经营活动产生的现金流量净额".equals(columName[i])) {
					opera_active_cash = columVal[i];
				} else if (columName[i].endsWith("现金及现金等价物净增加额")) {
					cash_and_cashequ = columVal[i];
				} else if (columName[i].endsWith("无形资产摊销")) {
					lntang_assets_amortize = columVal[i];
					
					updateSQL.append("update t_balance_sheet set lntang_assets_amortize=").append(lntang_assets_amortize). 
					append(" where year=").append(year).append(" and code='").append(code).append("';").append("\n");
					
				}else if (columName[i].endsWith("固定资产折旧、油气资产折耗、生产性生物资产折旧")){
					fixed_ass_depre = columVal[i];
					
					updateSQL.append("update t_incstate_sheet set fixed_ass_depre=").append(fixed_ass_depre). 
					append(" where year=").append(year).append(" and code='").append(code).append("';").append("\n");
					
				}else if (columName[i].endsWith("长期待摊费用摊销")) {
					long_pre_amort = columVal[i];
					
					updateSQL.append("update t_incstate_sheet set long_pre_amort=").append(long_pre_amort). 
					append(" where year=").append(year).append(" and code='").append(code).append("';").append("\n");
				}
			}
			
			sql.append(opera_active_cash).append(",").append(cash_and_cashequ).append(",");
			sql.append("1,'").append(new Timestamp(System.currentTimeMillis())).append("','admin');\n");
			
			
		} else if ("FZB".equals(type)) {
			
			String note_recable = "0"; //应收票据
			String adv_customers = "0"; //预收账款
			String acc_payable = "0"; //应付账款
			String constr_in_pro = "0"; //在建工程
			String lntang_assets = "0"; //无形资产
			String lntang_assets_amortize = "0"; //无形资产摊销 //这个在流量表中，需要单独处理
			String goodwill = "0"; //商誉
			String short_term_loans = "0"; //短期借款
			String note_payable = "0"; //应付票据
			String debit_within_year = "0"; //一年内到期的非流动负债
			String long_term_loans = "0"; //长期借款
			String bounds_payable = "0"; //应付债券
			String long_acc_payable = "0";//长期应付款
			String liquid_assets_start = "0"; //期初流动资产 --这个表里没有，需要看财报手动录入
			String liquid_assets_end= "0"; //期末流动资产--报表里只有 流动资产合计
			String curr_liab = "0"; //流动负债 --用报表里流动负债合计 代替
			String curr_liab_non = "0"; //非流动负债 用报表里非流动负债合计 代替
			String goods_start = "0"; //期初存货  不存在，需要看报表
			String goods_end = "0"; //期末存货  取  存货
			String cash = "0"; //货币资金
			String trad_assets = "0"; //交易性金融资产 没有 用[以公允价值计量且其变动计入当期损益的金融资产]代替?
			String total_liab_start = "0"; //期初负债总额
			String total_liab_end = "0"; //期末负债总额 用负债合计代替
			String total_ass_start = "0"; //期初资产总额
			String total_ass_end = "0"; //期末资产总额 用资产总计代替
			String share_holder_start = "0"; //期初股东权益
			String share_holder_end = "0"; //期末股东权益 用[所有者权益（或股东权益）合计]代替
			String fixed_assets_start = "0"; //期初固定资产
			String fixed_assets_end = "0"; //期末固定资产 用【固定资产】代替
			String retain_earnings = "0"; //未分配利润
			String acc_recable_start = "0"; //期初应收账款
			String acc_recable_end = "0"; //期末应收账款 用 应收账款 代替
			String capital_surplus = "0"; //资本公积
			String surplus_reserve = "0"; //盈余公积
			
			sql.append("insert into t_balance_sheet values ('").append(code).append("',").append(year).append(",");
			
			
			
			for(int i=0; i<len; i++) {
				
				String filterStr = columVal[i];
				if("".equals(filterStr.trim())) { 
					continue;
				}
				
				if("应收票据".equals(columName[i])) {
					note_recable = columVal[i];
				}else if ("预收款项".equals(columName[i])) {
					adv_customers = columVal[i];
				}else if ("应付账款".equals(columName[i])) {
					acc_payable = columVal[i];
				}else if ("在建工程".equals(columName[i])) {
					constr_in_pro = columVal[i];
				}else if ("无形资产".equals(columName[i])) {
					lntang_assets = columVal[i];
				}else if ("无形资产摊销".equals(columName[i])) {
					lntang_assets_amortize = columVal[i];
				}else if ("商誉".equals(columName[i])) {
					goodwill = columVal[i];
				}else if ("短期借款".equals(columName[i])) {
					short_term_loans = columVal[i];
				}else if ("应付票据".equals(columName[i])) {
					note_payable = columVal[i];
				}else if ("一年内到期的非流动负债".equals(columName[i])) {
					debit_within_year = columVal[i];
				}else if ("长期借款".equals(columName[i])) {
					long_term_loans = columVal[i];
				}else if ("应付债券".equals(columName[i])) {
					bounds_payable = columVal[i];
				}else if ("长期应付款".equals(columName[i])) {
					long_acc_payable = columVal[i];
				}else if ("期初流动资产".equals(columName[i])) {
					liquid_assets_start = columVal[i];
				}else if ("流动资产合计".equals(columName[i])) {
					liquid_assets_end = columVal[i]; 
					
					updateSQL.append("update t_balance_sheet set liquid_assets_start=").append(liquid_assets_end). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
				}else if ("流动负债合计".equals(columName[i])) {
					curr_liab = columVal[i];
				}else if ("非流动负债合计".equals(columName[i])) {
					curr_liab_non = columVal[i];
				}else if ("期初存货".equals(columName[i])) {
					goods_start = columVal[i];
				}else if ("存货".equals(columName[i])) {
					goods_end = columVal[i];
					
					updateSQL.append("update t_balance_sheet set goods_start=").append(goods_end). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("货币资金（元）".equals(columName[i])) {
					cash = columVal[i];
				}else if ("交易性金融资产".equals(columName[i])) {
					trad_assets = columVal[i];
				}else if ("期初负债总额".equals(columName[i])) {
					total_liab_start = columVal[i];
				}else if ("负债合计".equals(columName[i])) {
					total_liab_end = columVal[i];
					
					updateSQL.append("update t_balance_sheet set total_liab_start=").append(total_liab_end). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("期初资产总额".equals(columName[i])) {
					total_ass_start = columVal[i];
				}else if ("资产总计".equals(columName[i])) {
					total_ass_end = columVal[i];
					
					updateSQL.append("update t_balance_sheet set total_ass_start=").append(total_ass_end). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("期初股东权益".equals(columName[i])) {
					share_holder_start = columVal[i];
				}else if ("所有者权益（或股东权益）合计".equals(columName[i])) {
					share_holder_end = columVal[i];
					
					updateSQL.append("update t_balance_sheet set share_holder_start=").append(share_holder_end). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
					
				}else if ("期初固定资产".equals(columName[i])) {
					fixed_assets_start = columVal[i];
				}else if ("固定资产".equals(columName[i])) {
					fixed_assets_end = columVal[i];
					
					updateSQL.append("update t_balance_sheet set fixed_assets_start=").append(fixed_assets_end). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("未分配利润".equals(columName[i])) {
					retain_earnings = columVal[i];
				}else if ("期初应收账款".equals(columName[i])) {
					acc_recable_start = columVal[i];
				}else if ("应收账款".equals(columName[i])) {
					acc_recable_end = columVal[i];
					
					updateSQL.append("update t_balance_sheet set acc_recable_start=").append(acc_recable_end). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("资本公积".equals(columName[i])) {
					capital_surplus = columVal[i];
				}else if ("盈余公积".equals(columName[i])) {
					surplus_reserve = columVal[i];
				}
				
			}
			sql.append(note_recable).append(",")
			.append(adv_customers).append(",")
			.append(acc_payable).append(",")
			.append(constr_in_pro).append(",");
			sql.append(lntang_assets).append(",")
			.append(lntang_assets_amortize).append(",")
			.append(goodwill).append(",")
			.append(short_term_loans).append(",")
			.append(note_payable).append(",");
			sql.append(debit_within_year).append(",")
			.append(long_term_loans).append(",")
			.append(bounds_payable).append(",")
			.append(long_acc_payable).append(",");
			sql.append(liquid_assets_start).append(",")
			.append(liquid_assets_end).append(",")
			.append(curr_liab).append(",")
			.append(curr_liab_non).append(",")
			.append(goods_start).append(",");
			sql.append(goods_end).append(",")
			.append(cash).append(",")
			.append(trad_assets).append(",")
			.append(total_liab_start).append(",")
			.append(total_liab_end).append(",");
			sql.append(total_ass_start).append(",")
			.append(total_ass_end).append(",")
			.append(share_holder_start).append(",")
			.append(share_holder_end).append(",");
			sql.append(fixed_assets_start).append(",")
			.append(fixed_assets_end).append(",")
			.append(retain_earnings).append(",")
			.append(acc_recable_start).append(",");
			sql.append(acc_recable_end).append(",")
			.append(capital_surplus).append(",")
			.append(surplus_reserve).append(",");
			sql.append("1,'").append(new Timestamp(System.currentTimeMillis())).append("','admin');").append("\n");
//			sql.append(updateSQL); //追加update SQL
//			System.out.println(sql);
			
		} else if ("LRB".equals(type)) {
			sql.append("insert into t_incstate_sheet values ('").append(code).append("',").append(year).append(",");
			
			String busi_income_this = "0"; //本期营业收入 用 【其中：营业收入（元）】代替
			String busi_income_last = "0"; //上期营业收入
			String total_busi_inc_this = "0"; //本期营业总收入 用【营业总收入】
			String total_busi_inc_last = "0"; //上期营业总收入
			String opera_profits_this = "0"; // 本期营业利润 用 【三、营业利润】营业利润代替
			String opera_profits_last = "0"; //上期营业利润
			String income_tax = "0"; //所得税 用 [减：所得税]代替
			String fixed_ass_depre = "0"; //固定资产折旧 没有 要单补
			String long_pre_amort = "0"; //长期待摊费用摊销
			String total_profit_start = "0"; //期初利润总额
			String total_profit_end = "0"; //期末利润总额 用 【四、利润总额】代替
			String market_consts_this = "0"; // 本期销售费用 用 【销售费用】代替
			String market_consts_last = "0"; //上期销售费用
			String finance_consts_this = "0"; // 本期财务费用 用【财务费用】代替
			String finance_consts_last = "0"; //上期财务费用
			String mgr_consts_this = "0"; //本期管理费用 用【管理费用】代替
			String mgr_consts_last = "0"; //上期管理费用
			String inter_expense = "0"; //财务费用-利息支出
			String busi_tax_surcharge = "0";//营业税金及附加
			String net_profits_this = "0"; //本期净利润 用【五、净利润】 代替
			String net_profits_last = "0"; //上期净利润
			String net_profits_kf_last = "0"; //上期净利润（扣非）没有
			String net_profits_kf_this = "0"; //本期净利润（扣非）没有
			String operat_cost = "0"; //营业成本 用 【其中：营业成本】代替
			String busi_income_kf = "0"; //营业收入(扣非) 没有
			String fair_val_change = "0"; //公允价值变动 用【加：公允价值变动净收益】代替
			String invest_income = "0"; //投资收益
			String non_opera_income = "0";//营业外收入
			String non_opera_outcome = "0"; //营业外支出 用【减：营业外支出】代替
			
			///下面是 综合数据表项的内容
			String total_stocks = "0";//总股本
			String roe_wa = "0"; //加权平均净资产收益率
			String roe_wa_kf = "0"; //加权平均净资产收益率（扣非）
			String dividen_pay_sum = "0";// 本年度发放的现金股利总和
			String eps = "0"; //基本每股收益
			String eps_diluted = "0"; //稀释每股收益
			
			gendataSQL.append("insert into t_gendata_sheet values ('").append(code).append("',").append(year).append(",");
			
			for(int i=0; i<len; i++) {
				
				String filterStr = columVal[i];
				if("".equals(filterStr.trim())) { 
					continue;
				}
				
				if("其中：营业收入（元）".equals(columName[i])) {
					busi_income_this = filterStr;
					
					updateSQL.append("update t_incstate_sheet set busi_income_last=").append(busi_income_this). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("上期营业收入".equals(columName[i])) {
					busi_income_last = filterStr;
					
				}else if ("一、营业总收入".equals(columName[i])) {
					total_busi_inc_this = filterStr;
					
					updateSQL.append("update t_incstate_sheet set total_busi_inc_last=").append(total_busi_inc_this). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("上期营业总收入".equals(columName[i])) {
					total_busi_inc_last = filterStr;
				}else if ("三、营业利润".equals(columName[i])) {
					opera_profits_this = filterStr;
					
					updateSQL.append("update t_incstate_sheet set opera_profits_last=").append(opera_profits_this). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
					
				}else if ("上期营业利润".equals(columName[i])) {
					opera_profits_last = filterStr;
				}else if ("减：所得税".equals(columName[i])) {
					income_tax = filterStr;
				}else if ("固定资产折旧".equals(columName[i])) {
					fixed_ass_depre = filterStr;
				}else if ("长期待摊费用摊销".equals(columName[i])) {
					//在现金流量表中
					long_pre_amort = filterStr;
				}else if ("期初利润总额".equals(columName[i])) {
					total_profit_start = filterStr;
				}else if ("四、利润总额".equals(columName[i])) {
					total_profit_end = filterStr;
					
					updateSQL.append("update t_incstate_sheet set total_profit_start=").append(total_profit_end). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("销售费用".equals(columName[i])) {
					market_consts_this = filterStr;
					
					updateSQL.append("update t_incstate_sheet set market_consts_last=").append(market_consts_this). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("上期销售费用".equals(columName[i])) {
					market_consts_last = filterStr;
				}else if ("财务费用".equals(columName[i])) {
					finance_consts_this = filterStr;
					
					updateSQL.append("update t_incstate_sheet set finance_consts_last=").append(finance_consts_this). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
					
				}else if ("上期财务费用".equals(columName[i])) {
					finance_consts_last = filterStr;
				}else if ("管理费用".equals(columName[i])) {
					mgr_consts_this = filterStr;
					
					updateSQL.append("update t_incstate_sheet set mgr_consts_last=").append(mgr_consts_this). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("上期管理费用".equals(columName[i])) {
					mgr_consts_last = filterStr;
				}else if ("利息支出".equals(columName[i])) {
					inter_expense = filterStr;
				}else if ("营业税金及附加".equals(columName[i])) {
					busi_tax_surcharge = filterStr;
				}else if ("五、净利润".equals(columName[i])) {
					net_profits_this = filterStr;
					
					updateSQL.append("update t_incstate_sheet set net_profits_last=").append(net_profits_this). //今年的期末就是明年的期初
					append(" where year=").append(Integer.parseInt(year) + 1).append(" and code='").append(code).append("';").append("\n");
					
				}else if ("上期净利润".equals(columName[i])) {
					net_profits_last = filterStr;
				}else if ("上期净利润（扣非）".equals(columName[i])) {
					net_profits_kf_last = filterStr;
				}else if ("本期净利润（扣非）".equals(columName[i])) {
					net_profits_kf_this = filterStr;
				}else if ("其中：营业成本".equals(columName[i])) {
					operat_cost = filterStr;
				}else if ("营业收入(扣非)".equals(columName[i])) {
					busi_income_kf = filterStr;
				}else if ("加：公允价值变动净收益".equals(columName[i])) {
					fair_val_change = filterStr;
				}else if ("投资收益".equals(columName[i])) {
					invest_income = filterStr;
				}else if ("营业外收入".equals(columName[i])) {
					non_opera_income = filterStr;
				}else if ("减：营业外支出".equals(columName[i])) {
					non_opera_outcome = filterStr;
				}
				//下面是 综合数据表项的2个字段
				else if ("（一）基本每股收益".equals(columName[i])) {
					eps = filterStr;
				}else if ("（二）稀释每股收益".equals(columName[i])) {
					eps_diluted = filterStr;
				}
				
				
			}
			
			gendataSQL.append(total_stocks).append(",");
			gendataSQL.append(roe_wa).append(",");
			gendataSQL.append(roe_wa_kf).append(",");
			gendataSQL.append(dividen_pay_sum).append(",");
			gendataSQL.append(eps).append(",");
			gendataSQL.append(eps_diluted).append(",");
			gendataSQL.append("1,'").append(new Timestamp(System.currentTimeMillis())).append("','admin');").append("\n");
			
			
			sql.append(busi_income_this).append(",");
			sql.append(busi_income_last).append(",");
			sql.append(total_busi_inc_this).append(",");
			sql.append(total_busi_inc_last).append(",");
			sql.append(opera_profits_this).append(",");
			sql.append(opera_profits_last).append(",");
			sql.append(income_tax).append(",");
			sql.append(fixed_ass_depre).append(",");
			sql.append(long_pre_amort).append(",");
			sql.append(total_profit_start).append(",");
			sql.append(total_profit_end).append(",");
			sql.append(market_consts_this).append(",");
			sql.append(market_consts_last).append(",");
			sql.append(finance_consts_this).append(",");
			sql.append(finance_consts_last).append(",");
			sql.append(mgr_consts_this).append(",");
			sql.append(mgr_consts_last).append(",");
			sql.append(inter_expense).append(",");
			sql.append(busi_tax_surcharge).append(",");
			sql.append(net_profits_this).append(",");
			sql.append(net_profits_last).append(",");
			sql.append(net_profits_kf_last).append(",");
			sql.append(net_profits_kf_this).append(",");
			sql.append(operat_cost).append(",");
			sql.append(busi_income_kf).append(",");
			sql.append(fair_val_change).append(",");
			sql.append(invest_income).append(",");
			sql.append(non_opera_income).append(",");
			sql.append(non_opera_outcome).append(",");
			
			sql.append("1,'").append(new Timestamp(System.currentTimeMillis())).append("','admin');").append("\n");
//			sql.append(updateSQL); //追加update SQL
//			System.out.println(sql);
			
			
		} else {
			System.err.println("错误的报表标识.....");
		}
		
		
//		sql.append(updateSQL).append(gendataSQL);  //追加update SQL
//		System.out.println(sql);
		
	}
	
	public static void getStockBaseInfo(String code,StringBuilder updateSQL) throws Exception {
		String temp = getMarket(code);
		
		HttpEntity respEntity = HttpRequest.httpPost("http://www.cninfo.com.cn/information/brief/" + temp + code + ".html",null);
		String stockInfo = EntityUtils.toString(respEntity,"GBK");
//		System.out.println(stockInfo);
		Document doc = Jsoup.parse(stockInfo);
		Elements trs = doc.select("table").select("tr");
		
		String ipotime = "0";
		String ipostocks = "0";
		String issuedPE = "0";
		String issuedprice = "0";
		String address = "";
		String compywebsite = "0";
		String reportaddress = "http://www.cninfo.com.cn/information/companyinfo_n.html?fulltext?" + temp;
		String phone = "0";
		String legaler = "";
		
//		updateSQL = new StringBuilder();
		
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
                	/*if (!"".equals(text.trim())) {
                		issuedPE = text;
                	}*/
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
		updateSQL.append("address='").append(address).append("',").append("compywebsite='http://").append(compywebsite).append("',");
		updateSQL.append("phone='").append(phone).append("',").append("legaler='").append(legaler).append("',");
		updateSQL.append("reportaddress='").append(reportaddress).append("'");
		updateSQL.append(" where code='").append(code).append("';").append("\n");
		
	}
	
	
	public static String getMarket(String stockCode){
		String sc = stockCode.substring(0,1);
		if(sc.equals("6") || sc.equals("9")){
			return "shmb";
		}else if(sc.equals("3")){
			return "szcn";
		}else if(stockCode.substring(0,3).equals("002")){
			return "szsme";
		}else{
			return "szmb";
		}
	}

	

	public static void main(String[] args) throws Exception {
//		Utils.unZipFile("d:\\job\\");
	}
	
	
	
}
