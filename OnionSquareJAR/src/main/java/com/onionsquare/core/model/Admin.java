package com.onionsquare.core.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "admin")
public class Admin implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "admin_id")
	private Integer adminId;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;
	
	@Column(name = "paypal_email")
	private String paypalEmail;
	
	@Column(name="role")
	private String roleName;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "paypal_username")
	private String paypalUsername;
	
	@Column(name = "paypal_password")
	private String paypalPassword;
	
	@Column(name = "paypal_signature")
	private String paypalSignature;
	
	@Column(name = "paypal_appId")
	private String paypalAppId;
	
	@Column(name = "paypalMode")
	private String paypalMode;
	
	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private java.util.Date createdDate ;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="modified_date")
	private java.util.Date modifiedDate ;
	
	@Column(name="email")
	private String adminEmail ;
	
	@Transient
	private String oldPassword;
	
	@Transient
	private String fullName;

	public Integer getId() {
		return adminId;
	}

	public void setId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date date) {
		this.createdDate = date;
	}

	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPaypalEmail() {
		return paypalEmail;
	}

	public void setPaypalEmail(String paypalEmail) {
		this.paypalEmail = paypalEmail;
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

	public String getPaypalUsername() {
		return paypalUsername;
	}

	public void setPaypalUsername(String paypalUsername) {
		this.paypalUsername = paypalUsername;
	}

	public String getPaypalPassword() {
		return paypalPassword;
	}

	public void setPaypalPassword(String paypalPassword) {
		this.paypalPassword = paypalPassword;
	}

	public String getPaypalSignature() {
		return paypalSignature;
	}

	public void setPaypalSignature(String paypalSignature) {
		this.paypalSignature = paypalSignature;
	}

	public String getPaypalAppId() {
		return paypalAppId;
	}

	public void setPaypalAppId(String paypalAppId) {
		this.paypalAppId = paypalAppId;
	}

	public String getPaypalMode() {
		return paypalMode;
	}

	public void setPaypalMode(String paypalMode) {
		this.paypalMode = paypalMode;
	}	
	
}
