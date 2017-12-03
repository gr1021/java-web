package com.itheima.store.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 订单的实体
 *
 */
public class Order {
	private String oid;	//订单id
	private Date ordertime;//订单生成时间
	private Double total;//总价格
	private Integer state; // 1:未付款   2:已经付款,未发货. 3:已结发货,没有确认收货.  4:已结确认收货,订单结束.
	private String address;//收货地址
	private String name;//收货人
	private String telephone;//收货人联系电话
	private User user;
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public boolean  removeOrder(String oid){
		//删除订单项
		boolean flag = orderItems.remove(oid);
		
		return flag;
	}
}
