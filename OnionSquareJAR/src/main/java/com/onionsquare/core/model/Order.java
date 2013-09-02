package com.onionsquare.core.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "store_order")
public class Order {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_id")
	private Integer orderId;
	/**
	 * When FetchType.EAGER and optional= false & @JoinColumn is annotated, the inner join is applied. 
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "store_id")
	private Store store;

	/**
	 * When Order is saved, lineItems are to be saved at the same time.So
	 * Relationship owner is Order object marked by @JoinColumn Annotation and
	 * must be mandatory.Do not change the relationship.
	 */

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private List<LineItem> lineItemList;

	/**
	 * Unidirectional association with OrderStatus Object.
	 */
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "order_status")
	private OrderStatus orderStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	/**
	 * To be integrated when multiple payment methods are used.
	 */
	//	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	//	@JoinColumn(name="payment_method")
	
	@Column(name = "payment_method")
	private Integer paymentMethod;

	/**
	 * Total shipping cost incurred
	 */
	@Column(name = "shipping_cost")
	private Double shippingCost;

	/**
	 * Total Payable amount by customer
	 */
	@Column(name = "total_amount")
	private Double totalAmount;

	@Column(name = "number_of_items")
	private Integer numberOfItems;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "shipping_address")
	private Address shippingAddress;

	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "billing_address")
	private Address billingAddress;
	
	@Column(name="profit_to_onionsquare")
	private Double profitToOnionsquare ;	
	
	@Column(name="correlation_id")
	private String correlation_id;
	
	@Column(name="time_stamp")
	private String time_stamp;
	
	@Transient
	private Date fromDate;
	
	@Transient 
	private Date toDate;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(Integer numberOfItems) {
		this.numberOfItems =numberOfItems;
	}

	public List<LineItem> getLineItemList() {
		return lineItemList;
	}

	public void setLineItemList(List<LineItem> lineItemList) {
		this.lineItemList = lineItemList;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Double getProfitToOnionsquare() {
		return profitToOnionsquare;
	}

	public void setProfitToOnionsquare(Double profitToOnionsquare) {
		this.profitToOnionsquare = profitToOnionsquare;
	}

	public String getCorrelation_id() {
		return correlation_id;
	}

	public void setCorrelation_id(String correlation_id) {
		this.correlation_id = correlation_id;
	}

	public String getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}

   
	
	
	

}
