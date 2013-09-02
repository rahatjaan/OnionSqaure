package com.onionsquare.core.model;

import java.awt.image.RenderedImage;
import java.io.Serializable;
import java.util.List;

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

import org.springframework.web.multipart.MultipartFile;

/**
 * This class is a domain class that consists properties of a product that is
 * created and displayed in a store of onionsquare.com
 * 
 * @author Naresh
 * 
 */
@Entity
@Table(name = "product")
public class Product implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private Integer productId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "store_id")
	private Store store;

	@Column(name = "product_name")
	private String productName;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "image")
	private String imageName;

	@Column(name = "unit_price")
	private Double unitPrice;

	@Column(name = "description")
	private String description;
	
	
	@Column(name="tag_line")
	private String tagLine ;
	
	
	@Transient
	private List<MultipartFile> productImageFiles;
	
	@Transient
	public List<String> productImagesName;
	
	@Transient
	private RenderedImage image;
	
	@Transient
	private Double total_order_amount;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	public List<MultipartFile> getProductImageFiles() {
		return productImageFiles;
	}

	public void setProductImageFiles(List<MultipartFile> productImageFiles) {
		this.productImageFiles = productImageFiles;
	}
	
	

	public RenderedImage getImage() {
		return image;
	}

	public void setImage(RenderedImage image) {
		this.image = image;
	}

	public List<String> getProductImagesName() {
		return productImagesName;
	}

	public void setProductImagesName(List<String> productImagesName) {
		this.productImagesName = productImagesName;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public Double getTotal_order_amount() {
		return total_order_amount;
	}

	public void setTotal_order_amount(Double total_order_amount) {
		this.total_order_amount = total_order_amount;
	}
	
	

}
