package com.itheima.store.domain;
/*
 * 购物项：
 * 		商品信息
 * 		商品数量
 * 		商品价格小计
 */
public class CartItem {

	private Product product;
	private Integer count;
	private Double subtotal;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	//小计是通过计算得到的，小计等于商品数量乘以商品价格
	public Double getSubtotal() {
		return count*product.getShop_price();
	}
	/*public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}*/
	
	
}
