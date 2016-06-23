package com.investdata.dao.po;

import java.util.List;

/**
 * 封装图表展示界面所需要的各种参数
 * @author Administrator
 * @since 20160890
 */
public class Chart {
	private String xAxis;
	private List<String> dataList;
	public List<String> getDataList() {
		return dataList;
	}
	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}
	public String getxAxis() {
		return xAxis;
	}
	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}
	
	
	
}
