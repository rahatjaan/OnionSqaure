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

@Entity
@Table(name="address")
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="address_id")
	private Integer addressId ;
	
	@Column(name="receiver_full_name")
	private String receiverFullName ;

	@Column(name="apartment_address")
	private String apartmentAddress;
	
	@Column(name="street_name")
	private String streetName;
	
	@Column(name="city_name")
	private String cityName;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="state_id")
	private State state ;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="country_id")
	private Country country;
	
	@Column(name="zip_code")
	private String zipCode;
	
	@Column(name="phone")
	private String phone; 	
	
	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getReceiverFullName() {
		return receiverFullName;
	}

	public void setReceiverFullName(String receiverFullName) {
		this.receiverFullName = receiverFullName;
	}	 

	public String getApartmentAddress() {
		return apartmentAddress;
	}

	public void setApartmentAddress(String apartmentAddress) {
		this.apartmentAddress = apartmentAddress;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}	
	
	public boolean equal(Address address){
		if(address.getAddressId().equals(this.getAddressId()) && address.getPhone().equals(this.getPhone()) && address.getZipCode().equals(this.getZipCode()))
		{
			if(address.getCountry()!=null)
			{
				if(address.getCountry().getCountryId().equals(this.getCountry().getCountryId())){
				
					if(address.getState()!=null){
						if(address.getState().getStateId().equals(this.getState().getStateId()))
							return true;
						else
							return false;
					}else{
						return true;
					}
				}
			}
			
		}
		return false;

	}
	
	
	
}
