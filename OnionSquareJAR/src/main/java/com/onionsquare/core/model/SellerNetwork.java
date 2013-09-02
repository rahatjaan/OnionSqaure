package com.onionsquare.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name="seller_network")
//Override the default Hibernation delete and set the deleted flag rather than deleting the record from the db. Here you are using 
//the column name of real database table.

public class SellerNetwork {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="seller_network_id")
	private Integer sellerNetworkId ;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="seller_id")
	private Seller seller ;
	
	@Column(name="store_url")
	private String peerStoreUrl ;
	
	@Column(name="display_name")
	private String displayName ;
	
	@Column(name="status")
	private Boolean status ;
	


	public Integer getSellerNetworkId() {
		return sellerNetworkId;
	}

	public void setSellerNetworkId(Integer sellerNetworkId) {
		this.sellerNetworkId = sellerNetworkId;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getPeerStoreUrl() {
		return peerStoreUrl;
	}

	public void setPeerStoreUrl(String peerStoreUrl) {
		this.peerStoreUrl = peerStoreUrl;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	
	
}
