package com.monk.dao;

import java.util.List;

import com.monk.cart.Cart;

public interface CartDao {

	public List<Cart> listAllCart();
	
	public void saveOrUpdate(Cart cart);
	
	public Cart findById(int cid);
	
	public void delete(int cid);
}

