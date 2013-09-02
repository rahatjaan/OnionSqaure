package com.onionsquare.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "inquiry")
public class Inquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "inqiry_id")
	private Integer inqueryId;

	@ManyToOne(fetch = FetchType.EAGER,optional=true)
	@JoinColumn(name = "parent_inquiry_id")
	@NotFound (action = NotFoundAction.IGNORE)
	private Inquiry parentInquiry;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy="parentInquiry")
//	private Set<Inquiry> inquiryList;

	@Column(name = "from_user_type")
	private Integer senderUserType;

	@Column(name = "to_user_type")
	private Integer receiverUserType;

	@Column(name = "from_user_id")
	private Integer senderUserId;

	@Column(name = "to_user_id")
	private Integer receiverUserId;

	@Column(name = "content")
	private String inquiryContent;

	@Column(name = "created_date")
	private Date postedDate;


	@Column(name="closed_date")
	private Date closedDate ;
	
	@Column(name = "from_user_name")
	private String  senderName;
	
	@Column(name = "from_user_email")
	private String senderEmail;
	
	@Column(name = "to_user_email")
	private String  receiverEmail;
	
	@Column(name = "to_user_name")
	private String receiverName;

	
	
	
	@Transient
	private Store store;
	
	@Transient 
	private Customer customer;
	/**
	 * inquiry status = 1 is active, inquiry status=0 is closed.
	 */
	@Column(name = "status")
	private Boolean inquiryStatus;

	public Integer getInqueryId() {
		return inqueryId;
	}

	public void setInqueryId(Integer inqueryId) {
		this.inqueryId = inqueryId;
	}

	public Integer getSenderUserType() {
		return senderUserType;
	}

	public void setSenderUserType(Integer senderUserType) {
		this.senderUserType = senderUserType;
	}

	public Integer getReceiverUserType() {
		return receiverUserType;
	}

	public void setReceiverUserType(Integer receiverUserType) {
		this.receiverUserType = receiverUserType;
	}

	public Integer getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(Integer senderUserId) {
		this.senderUserId = senderUserId;
	}

	public Integer getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(Integer receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public String getInquiryContent() {
		return inquiryContent;
	}

	public void setInquiryContent(String inquiryContent) {
		this.inquiryContent = inquiryContent;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Boolean getInquiryStatus() {
		return inquiryStatus;
	}

	public void setInquiryStatus(Boolean inquiryStatus) {
		this.inquiryStatus = inquiryStatus;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public Inquiry getParentInquiry() {
		return parentInquiry;
	}

	public void setParentInquiry(Inquiry parentInquiry) {
		this.parentInquiry = parentInquiry;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}    

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	
	
	
}
