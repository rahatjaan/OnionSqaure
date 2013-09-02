package com.onionsquare.core.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "seller")
public class Seller implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "seller_id")
	private Integer sellerId;

	/**
	 * Many Seller might have referred by a single seller.
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "referer_id")
	private Seller refererSeller;

	/**
	 * On seller can have multiple referee sellers.
	 */
	@OneToMany(mappedBy = "refererSeller")
	private List<Seller> refereeSellerList = new ArrayList<Seller>();

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "user_name")
	private String username;

	@Column(name = "password")
	private String password;

	@Transient
	private String oldPassword;

	@Column(name = "created_date")
	private Date createdDate;

	/**
	 * It is the status of a seller. If status =true, seller is active otherwise
	 * it is inactive
	 */
	@Column(name = "status")
	private Boolean status = true;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "token")
	private String token;

	@Transient
	private String invitationId;

	@Column(name = "invitation_profit")
	private Double invitationProfit;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "state_id")
	private State state;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "country_id")
	private Country country;

	@Column(name = "city_name")
	private String cityName;

	// String representation of profil picture name.
	@Column(name = "profile_picture")
	private String profilePictureName;
	
	

	@Column(name = "last_login_date")
	private Date lastLoginDate;


	
	
	
	@Transient
	private String fullName;

	public Seller getRefererSeller() {
		return refererSeller;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public void setRefererSeller(Seller refererSeller) {
		this.refererSeller = refererSeller;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public Boolean getStatus() {
		return status;
	}

	public List<Seller> getRefereeSellerList() {
		return refereeSellerList;
	}

	public void setRefereeSellerList(List<Seller> refereeSellerList) {
		this.refereeSellerList = refereeSellerList;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Double getInvitationProfit() {
		return invitationProfit;
	}

	public void setInvitationProfit(Double invitationProfit) {
		this.invitationProfit = invitationProfit;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProfilePictureName() {
		return profilePictureName;
	}

	public void setProfilePictureName(String profilePictureName) {
		this.profilePictureName = profilePictureName;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
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
	
	

}
