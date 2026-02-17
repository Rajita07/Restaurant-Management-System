package com.restaurant.services.admin;

import java.io.IOException;
import java.util.List;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;
import com.restaurant.dtos.ReservationDto;

public interface AdminService {
	public CategoryDto postCategory(CategoryDto categoryDto)throws IOException;
	List<CategoryDto> getAllCategories();
	List<CategoryDto> getAllCategoriesByTitle(String title);
	ProductDto postProduct(Long categoryId, ProductDto productDto) throws Exception;
	public List<ProductDto> getAllProductsByCategory(Long categoryId);
	public List<ProductDto> getProductsByCategoryAndTitle(Long categoryId,String title);
	public void deleteProduct(Long productId);
	public ProductDto updateProduct(Long productId, ProductDto productDto) throws Exception;
	public ProductDto getProductById(Long productId);
	public List<ReservationDto> getReservations();
	public ReservationDto changeReservationStatus(Long reservationId, String status);
	
}
