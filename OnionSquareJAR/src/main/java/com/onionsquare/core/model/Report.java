package com.onionsquare.core.model;

public class Report {
	
	private int storeId ;
	private Long totalCount ;
	private Double totalAmount ;
	private Double average ;	
	
	public Report() {		
	}
	
	public Report(int storeId, Double totalAmount, Long totalCount,  Double average) {
		this.storeId=storeId;
		this.totalAmount=totalAmount ;
		this.totalCount=totalCount;
		this.average=average ;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}
	

}
