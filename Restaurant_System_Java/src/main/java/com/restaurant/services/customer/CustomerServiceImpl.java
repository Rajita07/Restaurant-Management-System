package com.restaurant.services.customer;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.restaurant.entities.Product;

import org.springframework.stereotype.Service;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;
import com.restaurant.dtos.ReservationDto;
import com.restaurant.entities.Category;
import com.restaurant.entities.Reservation;
import com.restaurant.entities.User;
import com.restaurant.enums.ReservationStatus;
import com.restaurant.repository.CategoryRepository;
import com.restaurant.repository.ProductRepository;
import com.restaurant.repository.ReservationRepository;
import com.restaurant.repository.UserRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	private final CategoryRepository categoryRepository ;
	private final ProductRepository productRepository;
	private final ReservationRepository reservationRepository;
	private final UserRepository userRepository;

	public CustomerServiceImpl(CategoryRepository categoryRepository,ProductRepository productRepository,ReservationRepository reservationRepository,
			UserRepository userRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.reservationRepository =reservationRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	@Override
	public List<CategoryDto> getCategoriesByName(String title) {
		return categoryRepository.findAllByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());
		
	}

	@Override
	public List<ProductDto> getProductsByCategory(Long categoryId) {
	    return productRepository.findAllByCategoryId(categoryId)
	            .stream()
	            .map(product -> product.getProductDto())
	            .collect(Collectors.toList());
	}


	@Override
	public List<ProductDto> getProductsByCategoryAndTitle(Long categoryId, String title) {
		return productRepository.findAllByCategoryIdAndNameContaining(categoryId, title).stream().map(Product::getProductDto)
				.collect(Collectors.toList());
	}

	@Override
	public ReservationDto postReservation(ReservationDto reservationDto) {
		Optional<User> optionalUser=userRepository.findById(reservationDto.getCustomerId());
		if(optionalUser.isPresent()) {
			Reservation reservation=new Reservation();
			reservation.setTableType(reservationDto.getTableType());
			reservation.setDateTime(reservationDto.getDateTime());
			reservation.setDescription(reservationDto.getDescription());
			reservation.setUser(optionalUser.get());
			reservation.setReservationStatus(ReservationStatus.PENDING);
			Reservation postedReservation=reservationRepository.save(reservation);
			ReservationDto postedReservationDto=new ReservationDto();
			postedReservationDto.setId(postedReservation.getId());
			return postedReservationDto;
		}
		return null;
	}
		

	@Override
	public List<ReservationDto> getReservationsByUser(Long customerId) {
		
		return reservationRepository.findAllByUserId(customerId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
	}

	

}