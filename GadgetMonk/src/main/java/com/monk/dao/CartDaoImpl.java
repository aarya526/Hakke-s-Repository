package com.monk.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.monk.cart.Cart;

@Repository
@Transactional
public class CartDaoImpl implements CartDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	@SuppressWarnings("unchecked")
	public List<Cart> listAllCart() {
		Criteria criteria=getSession().createCriteria(Cart.class);
		return (List<Cart>) criteria.list();
	}

	public void saveOrUpdate(Cart cart) {
		getSession().saveOrUpdate(cart);
		
	}

	public Cart findById(int cid) {
		Cart cart=(Cart) getSession().get(Cart.class,cid);
		return cart;
	}

	public void delete(int cid) {
		Cart cart=(Cart) getSession().get(Cart.class,cid);
		getSession().delete(cart);
		
	}

	
}
