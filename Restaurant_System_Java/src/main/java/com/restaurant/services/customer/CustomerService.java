package com.restaurant.services.customer;

import java.util.List;
import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;
import com.restaurant.dtos.ReservationDto;

public interface CustomerService {

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getCategoriesByName(String title);

	List<ProductDto> getProductsByCategory(Long categoryId);

	List<ProductDto> getProductsByCategoryAndTitle(Long categoryId, String title);

	ReservationDto postReservation(ReservationDto reservationDto);

	List<ReservationDto> getReservationsByUser(Long customerId);

	

}