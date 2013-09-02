package com.onionsquare.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "customer_tracker")
public class CustomerTracker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_tracker_id")
	private Integer customerTrackerId;	
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="product")
	private Product product;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customer")	
	private Customer customer;

	
	@Temporal(TemporalType.DATE)
	@Column(name="tracked_date")
	private Date trackedDate;


	public Integer getCustomerTrackerId() {
		return customerTrackerId;
	}


	public void setCustomerTrackerId(Integer customerTrackerId) {
		this.customerTrackerId = customerTrackerId;
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


	public Date getTrackedDate() {
		return trackedDate;
	}


	public void setTrackedDate(Date trackedDate) {
		this.trackedDate = trackedDate;
	}
	
	
	
	
	
	


	


}
