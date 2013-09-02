package com.onionsquare.core.model;

public interface Auditable {
	

		void setAuditDetails(AuditDetails auditDetails);
		AuditDetails getAuditDetails();
	

}
