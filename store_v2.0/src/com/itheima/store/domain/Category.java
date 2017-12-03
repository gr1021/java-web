package com.itheima.store.domain;

import java.io.Serializable;

public class Category implements Serializable {

	/**  
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)  
	 */ 
	private static final long serialVersionUID = 1L;
	
	private String cid;
	private String cname;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	
	
}
