package com.investdata.dao.po;

import java.io.Serializable;

import com.investdata.utils.StringUtils;

import freemarker.template.utility.StringUtil;

public class SearchIndex implements Serializable{
	private String code;
	private String name;
	private Integer count;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//复写equals方法，用于搜索指数排序.
	
	@Override
	public boolean equals(Object obj) {
		boolean retVal = false;
		if (!(obj instanceof SearchIndex)) {
			retVal =  false;
		} else {
			SearchIndex si =  (SearchIndex)obj;
			String objCode = si.getCode();
			
			
			if (!StringUtils.isEmpty(objCode) && !StringUtils.isEmpty(this.code) ) {
				retVal =  objCode.equals(this.code);
			}else {
				String objName = si.getName();
				
				if (!StringUtils.isEmpty(objName)) {
					retVal =  objName.equals(this.name);
				}
			}
		}
		return retVal;
	}
	
	
	
}
