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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "invitation")
public class Invitation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "invitation_id")
	private Integer invitationId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "invited_by")
	private Seller seller;

	@Column(name = "invitee_email")
	private String inviteeEmail;

	@Temporal(TemporalType.DATE)
	@Column(name = "invitation_date")
	private Date invitationDate;

	@Column(name = "token")
	private String token;

	@Column(name = "invitation_status")
	private Boolean invitationStatus;

	public Integer getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(Integer invitationId) {
		this.invitationId = invitationId;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getInviteeEmail() {
		return inviteeEmail;
	}

	public void setInviteeEmail(String inviteeEmail) {
		this.inviteeEmail = inviteeEmail;
	}

	public Date getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(Date invitatioDate) {
		this.invitationDate = invitatioDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(Boolean invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

}
