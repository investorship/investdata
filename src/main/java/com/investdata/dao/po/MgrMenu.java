package com.investdata.dao.po;

import java.sql.Timestamp;

/**
 * 后台管理菜单树
 * @author Bill
 *
 */
public class MgrMenu {
	
		private Integer id;//菜单id
		private Integer pid;//菜单父id
		private String name;//菜单名称
		private Integer isLeaf;//是否叶子节点，0-否，1-是
		private String reqUrl; //菜单连接地址
		private Integer flag;//是否启用标记,0-禁用，1-启用
		private Timestamp inTime;//入库时间
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getPid() {
			return pid;
		}
		public void setPid(Integer pid) {
			this.pid = pid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getIsLeaf() {
			return isLeaf;
		}
		public void setIsLeaf(Integer isLeaf) {
			this.isLeaf = isLeaf;
		}
		public String getReqUrl() {
			return reqUrl;
		}
		public void setReqUrl(String reqUrl) {
			this.reqUrl = reqUrl;
		}
		public Integer getFlag() {
			return flag;
		}
		public void setFlag(Integer flag) {
			this.flag = flag;
		}
		public Timestamp getInTime() {
			return inTime;
		}
		public void setInTime(Timestamp inTime) {
			this.inTime = inTime;
		}
		
}
