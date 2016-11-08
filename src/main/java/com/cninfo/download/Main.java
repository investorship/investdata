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
		
		/*Connection conn = JDBC.getConn();
		PreparedStatement pst = conn.prepareStatement("select code,name from t_stocks");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			String code = rs.getString("code");
			Thread.sleep(2000);
			
			//获取orgId 与market等信息。
		}*/
		JSONObject jsonParam = Utils.getOrgInfoJsonObj("000858", origIdUrl);
		Utils.getFinanceDataZip(url, jsonParam);
	}
}
