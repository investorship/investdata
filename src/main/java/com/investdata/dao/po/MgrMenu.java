package com.investdata.dao.po;

import java.sql.Timestamp;

/**
 * 后台管理菜单树
 * @author Bill
 *
 */
public class MgrMenu {
	
		private int id;//菜单id
		private int pid;//菜单父id
		private String name;//菜单名称
		private int isleaf;//是否叶子节点，0-否，1-是
		private int flag;//是否启用标记,0-禁用，1-启用
		private Timestamp intime;//入库时间
		private String url;//url
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getPid() {
			return pid;
		}
		public void setPid(int pid) {
			this.pid = pid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getIsleaf() {
			return isleaf;
		}
		public void setIsleaf(int isleaf) {
			this.isleaf = isleaf;
		}
		public int getFlag() {
			return flag;
		}
		public void setFlag(int flag) {
			this.flag = flag;
		}
		public Timestamp getIntime() {
			return intime;
		}
		public void setIntime(Timestamp intime) {
			this.intime = intime;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}

}
