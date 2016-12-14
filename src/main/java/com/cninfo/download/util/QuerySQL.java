package com.cninfo.download.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class QuerySQL {
	public static void main(String[] args) throws Exception {
		File file = new File("d:\\job\\sql");
		File[] sqlList = file.listFiles();
		System.out.println(sqlList.length);
		
		BufferedWriter zuhe = new BufferedWriter(new FileWriter(new File("e:\\sqlCount.sql")));
		
		String temp = "";
		int i = 0;
		int l = 0;
		Connection conn = JDBC.getConn();
		for (File sqlFile : sqlList) {
			++i;
			BufferedReader reader = new BufferedReader(new FileReader(sqlFile));
			while ((temp = reader.readLine()) != null) {
//				zuhe.write(temp);
//				zuhe.write("\n");
//				++l;
				if (temp.contains("600350")) {
					System.out.println(temp);
				}
				
				/*Thread.sleep(100);
				PreparedStatement pst = conn.prepareStatement(temp);
				int a = pst.executeUpdate();
				Thread.sleep(100);*/
			}
			
//			System.err.println("第【" + i +"】个sql文件执行完成,执行SQL条数【 "+ l +"】");
			
		}
		
		zuhe.flush();
		
	}
}
