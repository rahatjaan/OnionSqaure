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
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName ;
	
	@Column(name="last_name")
	private String lastName; 
	
	@Column(name="username")
	private String userName ;
	
	@Column(name="password")
	private String password ;
	
	@Column(name="email")
	private String email;
	
	@Column(name="paypal_email")
	private String paypal_email;
	
	@Column(name="phone")
	private String phone;
	
	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="modified_date")
	private Date modifiedDate ;
	
	@Column(name="token")
	private String token ;
	
	@Column(name="status")
	private Boolean status ;
	
	@Transient
	private String fullName;
	
	/**
	 * This will cause ignore, if Customer has an address id which does not exists in
	 * Address table. No exception of No row with given identifier is thrown. This must
	 * be written in every @JoinColumn annotated property if consistency cannot be maintained accross data tables. 
	 */
    @Transient	
	private Address shippingAddress; 
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="billing_address")
	private Address billingAddress ;
	
	@Transient
	private String oldPassword;
	
	
	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public String getFullName() {
		if(this.firstName!=null && !this.firstName.equals(""))
			fullName = this.firstName;
		if(this.middleName!=null && !this.middleName.equals(""))
			fullName = fullName +" "+ this.middleName;
		if(this.lastName!=null && !this.lastName.equals(""))
			fullName = fullName +" "+ this.lastName;
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPaypal_email() {
		return paypal_email;
	}

	public void setPaypal_email(String paypal_email) {
		this.paypal_email = paypal_email;
	}
    
	
	
	
	
	
}
