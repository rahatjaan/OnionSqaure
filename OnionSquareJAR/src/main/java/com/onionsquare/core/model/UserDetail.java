package com.onionsquare.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@SuppressWarnings("serial")
@Table(name = "USER_INFO")
public class UserDetail extends BaseObject implements UserDetails, Auditable {

	@Id
	@Column(name = "USR_ID_PK")
	@GeneratedValue
	private Long id;

	@Column(name = "USR_LOGIN_ID", nullable = false)
	private String userName;

	@Column(name = "USR_USER_FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "USR_MIDDLE_NAME")
	private String middleName;

	@Column(name = "USR_USER_LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "USR_PASSWORD", nullable = false)
	private String password;

	@Column(name = "USR_EMAIL_ID", nullable = false)
	private String emailId;

	@Column(name = "USR_DOB", nullable = false)
	private Date dateOfBirth;

	@Column(name = "USR_NATIONALITY")
	private String nationality;

	@Column(name = "USR_GENDER")
	private String gender;

	@Column(name = "USR_TELEPHONE_NO")
	private String tellPhoneNo;

	@Column(name = "USR_MOBILE_NO")
	private String mobileNo;

	@Column(name = "USR_PASSPORT_NUMBER")
	private String passportNo;

	@Column(name = "USR_TEMP_ADDRESS")
	private String tempAddress;

	@Column(name = "USR_PERM_ADDRESS")
	private String perAddress;

	@Column(name = "USR_CITY")
	private String city;

	@Embedded
	private AuditDetails auditDetails;

	@Column(name = "USR_STATUS")
	private String status;

	@ManyToOne
	@JoinColumn(name = "USR_ROLE_ID")
	private Role roles;

	@Column(name = "USR_ACTIVATIONSTATUS")
	private boolean activationStatus;

	@Transient
	private String fullName;

	@ManyToOne
	@JoinColumn(name = "USR_PARENT_AGENT")
	private UserDetail parentAgent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTellPhoneNo() {
		return tellPhoneNo;
	}

	public void setTellPhoneNo(String tellPhoneNo) {
		this.tellPhoneNo = tellPhoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getTempAddress() {
		return tempAddress;
	}

	public void setTempAddress(String tempAddress) {
		this.tempAddress = tempAddress;
	}

	public String getPerAddress() {
		return perAddress;
	}

	public void setPerAddress(String perAddress) {
		this.perAddress = perAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}

	public String getFullName() {
		if (fullName == null) {
			fullName = firstName;
			if (getMiddleName() != null)
				fullName += " " + middleName;
			fullName += " " + lastName;
		}
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public UserDetail getParentAgent() {
		return parentAgent;
	}

	public void setParentAgent(UserDetail parentAgent) {
		this.parentAgent = parentAgent;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
