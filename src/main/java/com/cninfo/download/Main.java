package com.cninfo.download;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONObject;

import com.cninfo.download.util.JDBC;
import com.cninfo.download.util.Utils;

/**
 * 因很多指标有年初和年末，而巨潮资讯下载的财务报表中的一般是指年末，年初的怎么来呢？ 去年的年末就是今年的年初。所以即便比如你想要
 * 录入2017年的数据，在下载财务数据文件时，也要从16年开始，这样：2017年的数据才能完整，只不过会生成很多2016年的insert数据，因为有
 * 主键防重，所以执行失败不用关心即可。
 * @author guohailong
 *
 */

public class Main {
	public static void main(String[] args) throws Exception{
		//findataURL
		String url = "http://www.cninfo.com.cn/cninfo-new/data/download";
		String origIdUrl = "http://www.cninfo.com.cn/cninfo-new/data/query";
		String fileDir = "d:\\job\\"; //文件存放路径
		
		Connection conn = JDBC.getConn();
		PreparedStatement pst = conn.prepareStatement("select code,name from t_stocks where flag = 1");
		ResultSet rs = pst.executeQuery();
		int i = 0;
		while(rs.next()) {
			String code = rs.getString("code");
			
			if (filter(code)) {
				continue;
			}
			
			try {
				JSONObject jsonParam = Utils.getOrgInfoJsonObj(code, origIdUrl);
				Utils.getFinanceDataZip(url, jsonParam,fileDir + code + "\\"); //获取财务数据
				Utils.unZipFile(fileDir + code + "\\",code);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
			System.out.println("当前处理条数：" + i + "当前处理代码:【" + code + "】");
			//获取orgId 与market等信息。
			Thread.sleep(2500);
		}
		
		
		/*JSONObject jsonParam1 = Utils.getOrgInfoJsonObj("000026", origIdUrl);
		Utils.getFinanceDataZip(url, jsonParam1,fileDir); //获取财务数据
		Utils.unZipFile(fileDir,"000026");*/
	}
	
	/*public static void main(String[] args) throws Exception {
		StringBuilder stockInfoSQL = new StringBuilder();
		Connection conn = JDBC.getConn();
		PreparedStatement pst = conn.prepareStatement("select code,name from t_stocks");
		ResultSet rs = pst.executeQuery();
		int i=0;
		while(rs.next()) {
			++i;
			String code = rs.getString("code");
			Utils.getStockBaseInfo(code,stockInfoSQL);
			Thread.sleep(100);
			System.out.println("当前处理条数 i=" + i);
		}
		
		BufferedWriter  stockBaseInfo = new BufferedWriter(new FileWriter(new File("d:\\job\\stockInfo.sql")));
		stockBaseInfo.write(stockInfoSQL.toString());
		stockBaseInfo.flush();
		stockBaseInfo.close();
	}*/
	
	private static boolean filter(String code) {
		File file = new File("D:\\job\\sql");
		File[] fileList = file.listFiles();
		boolean retVal = false;
		String fileName = "";
		for (File tempFile : fileList) {
			fileName = tempFile.getName();
			if (code.equals(fileName.substring(0,fileName.indexOf(".")))) {
				retVal =  true;
			} 
		}
		
		return retVal;
	}
	
//	public static void main(String[] args) {
//		Main m = new Main();
//		System.out.println(m.filter("000001"));
//	}
}
