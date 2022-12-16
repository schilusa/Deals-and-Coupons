package com.order.orderservice.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor

@NoArgsConstructor

@Document(collection ="cart")
public class Cart {
	  @Id
		private int cartId;
	    private List<Products> products;
	    private double total;
		public int getCartId() {
			return cartId;
		}
		public void setCartId(int cartId) {
			this.cartId = cartId;
		}
		public List<Products> getProducts() {
			return products;
		}
		public void setProducts(List<Products> products) {
			this.products = products;
		}
		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}
	    
	    
}