package com.itheima.store.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 购物车：
 * 		购物项的集合：
 */
public class Cart {

	/*
	 * 购物车的属性
	 */
	private Double total = 0d;
	// Map集合用于存放购物项的集合列表,因为要进行删除购物项,所以使用Map而不使用List.
	//使用商品的ID作为map的key. 购物项作为Map的value:
	private Map<String,CartItem> map = new LinkedHashMap<String,CartItem>();
	
	public Double getTotal() {
		return total;
	}
	public Map<String, CartItem> getMap() {
		return map;
	} 
	
	/**
	 * 购物车的方法
	 */
	//添加购物项的方法
	public void addCart(CartItem cartItem){
		//首先判断购物项是否在购物车中
		//获取商品的id
		String pid = cartItem.getProduct().getPid();
		//判断id是否在map集合中
		if(map.containsKey(pid)){
			//说明在购物车中，则将购物项的商品数量加1
			CartItem item = map.get(pid);
			item.setCount(cartItem.getCount()+item.getCount());
		}else{
			//若不在购物车中，则添加到购物车map中
			map.put(pid, cartItem);
		}
		
		total+=cartItem.getSubtotal();
	}
	//删除商品的方法
	public void deleteCart(String pid){
		//从map中移除某个商品
		CartItem cartItem = map.remove(pid);
		//总金额中减去移除商品的小计
		total -= cartItem.getSubtotal();
		
	}
	//清空购物购物车的方法
	public void clearCart(){
		//清空map集合
		map.clear();
		//设置总金额
		total = 0d;
		
	}
}