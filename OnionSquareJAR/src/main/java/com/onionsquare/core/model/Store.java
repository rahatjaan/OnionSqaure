
package com.onionsquare.core.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

/**
 * Store class represents the store created by a seller.
 * 
 * @author Naresh
 * @version 0.0.1-SNAPSHOT
 * 
 */

@Entity
@Table(name = "store")
public class Store {

	@Id
	@Column(name = "store_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer storeId;

	/**
	 * It is seller who owns this store. A store is owned by a single seller.
	 * 
	 * @JoinColumn & optional=false Does inner join with seller.
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
	private List<Category> categoryList;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date")
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "store_name")
	private String storeName;

	@Column(name = "subdomain_name")
	private String subDomainName;

	@Column(name="paypal_email")
	private String paypalEmail;

	/**
	 * Added Recently
	 */
	@Column(name = "store_description")
	private String storeDescription;

	@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "city_name")
	private String cityName;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "state_id")
	private State state;

	/**
	 * @JoinColumn & optional=false does INNER  join.
	 * @JoinColumn & optional=true does LEFT OUTER join.
	 * For each required=false property, perform left outer join.
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "country_id")
	private Country country;

	@Column(name = "paypal_username")
	private String payPalUserName;

	@Column(name = "paypal_password")
	private String payPalPassword;

	@Column(name = "paypal_certificate")
	private String payPalCertificate;

	@Column(name = "status")
	private boolean storeStatus;
	
	@Transient
	private MultipartFile sellerPicture;

	/**
	 * Mapping Store with Order
	 * FetchType.LAZY does not do any Join with the Entity Order. But can be overrided in DetachedCriteria.
	 * Since a store may have 0..N orders, this field is to be fetched LAZY while getting order's of a store.
	 * If FetchType.EAGER is done, the it does left outer join.
	 * 
	 */
	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
	private List<Order> orderList;
	
	@Column(name="email_status")
	private Boolean emailStatus ;
	
	@Temporal(TemporalType.DATE)
	@Column(name="deactivated_date")
	private Date deactivatedDate ;
	
	// all the keywords are concatenated and separated by ';'. Latter they are 
	@Column(name = "keywords")
	private String keyWords;
	
	@Column(name="shipping_refund_policy")
	private String shippingRefundPolicy;
	
	
	@Transient
	private int totalOrderCount ;
	
	@Transient
	private Double revenue;
	
	@Transient
	private Double onionsquareProfit;
	
	@Transient
	private Double profit;
	
	@Column(name = "facebook_link")
	private String facebookLink;

	@Column(name = "twitter_link")
	private String twitterLink;

	@Column(name = "linkedin_link")
	private String linkedinLink;	


	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(boolean storeStatus) {
		this.storeStatus = storeStatus;
	}

	public String getSubDomainName() {
		return subDomainName;
	}

	public void setSubDomainName(String subDomainName) {
		this.subDomainName = subDomainName;
	}	

	public String getStoreDescription() {
		return storeDescription;
	}

	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getPayPalUserName() {
		return payPalUserName;
	}

	public void setPayPalUserName(String payPalUserName) {
		this.payPalUserName = payPalUserName;
	}

	public String getPayPalPassword() {
		return payPalPassword;
	}

	public void setPayPalPassword(String payPalPassword) {
		this.payPalPassword = payPalPassword;
	}

	public String getPayPalCertificate() {
		return payPalCertificate;
	}

	public void setPayPalCertificate(String payPalCertificate) {
		this.payPalCertificate = payPalCertificate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public Boolean getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(Boolean emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Date getDeactivatedDate() {
		return deactivatedDate;
	}

	public void setDeactivatedDate(Date deactivatedDate) {
		this.deactivatedDate = deactivatedDate;
	}

	public int getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(int totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	public String getPaypalEmail() {
		return paypalEmail;
	}

	public void setPaypalEmail(String paypalEmail) {
		this.paypalEmail = paypalEmail;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getShippingRefundPolicy() {
		return shippingRefundPolicy;
	}

	public void setShippingRefundPolicy(String shippingRefundPolicy) {
		this.shippingRefundPolicy = shippingRefundPolicy;
	}

	public Double getOnionsquareProfit() {
		return onionsquareProfit;
	}

	public void setOnionsquareProfit(Double onionsquareProfit) {
		this.onionsquareProfit = onionsquareProfit;
	}
	
	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	public String getTwitterLink() {
		return twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public String getLinkedinLink() {
		return linkedinLink;
	}

	public void setLinkedinLink(String linkedinLink) {
		this.linkedinLink = linkedinLink;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public MultipartFile getSellerPicture() {
		return sellerPicture;
	}

	public void setSellerPicture(MultipartFile sellerPicture) {
		this.sellerPicture = sellerPicture;
	}
	
	
}
