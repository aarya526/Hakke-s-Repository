package com.monk.cart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.monk.model.Customer;
import com.monk.model.Product;

@Entity
@Table(name="cart")
public class Cart {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int cid;
	
	@NotEmpty(message="Shipping Address cannot be empty")
	private String shipping_address;
	
	@NotEmpty(message="Billing Address cannot be empty")
	private String billing_address;
	
	@Transient
	Product product;
	
	@Transient
	Customer customer;

	public int getCid() {
		return cid;
	}

	public void setOid(int cid) {
		this.cid = cid;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public String getBilling_address() {
		return billing_address;
	}

	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
