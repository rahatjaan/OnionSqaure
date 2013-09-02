package com.onionsquare.core.model;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Embeddable
public class AuditDetails  implements Serializable{
	private String createdby;
	private Date createdat;
	private String modifiedby;
	private Date modifiedat;
	private String userType;
	private Long userId;

@Column(name = "CREATED_BY", length = 32, updatable = false)
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

@Column(name="CREATED_AT", length=32, updatable=false)
	public Date getCreatedat() {
		return createdat;
	}
	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}
	
@Column(name="MODIFIED_BY", length=32)	
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

@Column(name="MODIFIED_AT", length=32)
	public Date getModifiedat() {
		return modifiedat;
	}
	public void setModifiedat(Date modifiedat) {
		this.modifiedat = modifiedat;
	}

@Transient
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
@Transient
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	

}
