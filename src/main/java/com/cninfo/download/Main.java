package com.cninfo.download;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONObject;

import com.cninfo.download.util.JDBC;
import com.cninfo.download.util.Utils;

public class Main {
	public static void main(String[] args) throws Exception{
		//findataURL
		String url = "http://www.cninfo.com.cn/cninfo-new/data/download";
		String origIdUrl = "http://www.cninfo.com.cn/cninfo-new/data/query";
		String fileDir = "d:\\job\\"; //文件存放路径
		
		Connection conn = JDBC.getConn();
		PreparedStatement pst = conn.prepareStatement("select code,name from t_stocks");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			String code = rs.getString("code");
			
			JSONObject jsonParam = Utils.getOrgInfoJsonObj(code, origIdUrl);
			Utils.getFinanceDataZip(url, jsonParam,fileDir); //获取财务数据
			Utils.unZipFile(fileDir,code);
			
			//获取orgId 与market等信息。
		}
		
		/*Thread.sleep(1000);
		
		JSONObject jsonParam1 = Utils.getOrgInfoJsonObj("002537", origIdUrl);
		Utils.getFinanceDataZip(url, jsonParam1,fileDir); //获取财务数据
		Utils.unZipFile(fileDir,"002537");*/
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
}
