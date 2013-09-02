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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "state") 
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="state_id")
	private Integer stateId;

	@Column(name = "state_name")
	private String stateName;

	@NotFound(action=NotFoundAction.IGNORE)
 	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "country_id")
	private Country country;

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}