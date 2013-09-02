package com.onionsquare.core.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import com.onionsquare.core.model.BaseObject;

@SuppressWarnings("serial")
@Entity
@Table(name = "ROLES")
public class Role extends BaseObject implements GrantedAuthority {

	@Id
	@Column(name = "ROL_ID_PK")
	@GeneratedValue
	private Long id;

	@Column(name = "ROL_NAME", unique = true, nullable = false, length = 64)
	private String name;

	@Column(name = "ROL_STATUS", nullable = false, length = 1)
	private String status;

	@Embedded
	private AuditDetails auditDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}

	@Transient
	public String getAuthority() {
		// TODO Auto-generated method stub
		return getName();
	}

	public int compareTo(Object o) {
		return 0;
	}

}
