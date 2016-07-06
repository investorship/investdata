package com.investdata.dao.po;

import java.util.List;

/**
 * 封装图表展示界面所需要的各种参数
 * @author Administrator
 * @since 20160890
 */
public class Chart {
	private String xAxis;
	private String text; //主标题
	private String subtext; //子标题
	private String legendData;
	private String data;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getxAxis() {
		return xAxis;
	}
	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSubtext() {
		return subtext;
	}
	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}
	public String getLegendData() {
		return legendData;
	}
	public void setLegendData(String legendData) {
		this.legendData = legendData;
	}

}
