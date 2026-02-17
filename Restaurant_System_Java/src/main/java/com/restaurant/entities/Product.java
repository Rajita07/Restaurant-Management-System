package com.restaurant.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.Base64;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.dtos.ProductDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String price;
	private String description;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] img;

	
	@ManyToOne(fetch=FetchType.LAZY,optional = false)
	@JoinColumn(name="category_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Category category;

	public Product() {
		
	}
	public Product(Long id, String name, String price, String description, byte[] img, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.img = img;
		this.category = category;
	}
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public ProductDto getProductDto() {
	    ProductDto productDto = new ProductDto();
	    productDto.setId(id);
	    productDto.setName(name);
	    productDto.setPrice(price);
	    productDto.setDescription(description);

	    if (img != null) {
	        productDto.setReturnedImg(
	            Base64.getEncoder().encodeToString(img)
	        );
	    }

	    productDto.setCategoryId(category.getId());
	    productDto.setCategoryName(category.getName());
	    return productDto;
	}
	
	
	
}
