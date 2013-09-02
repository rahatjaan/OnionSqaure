package com.onionsquare.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PaymentMethod {

	@Id
	private Integer paymentMethodId;
	private String name;

	public Integer getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(Integer paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}