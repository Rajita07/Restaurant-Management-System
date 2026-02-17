package com.restaurant.services.admin;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.InvalidIsolationLevelException;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;
import com.restaurant.dtos.ReservationDto;
import com.restaurant.entities.Category;
import com.restaurant.entities.Product;
import com.restaurant.entities.Reservation;
import com.restaurant.enums.ReservationStatus;
import com.restaurant.repository.CategoryRepository;
import com.restaurant.repository.ProductRepository;
import com.restaurant.repository.ReservationRepository;



@Service
public class AdminServiceImpl implements AdminService{
	private final CategoryRepository categoryRepository ;
	private final ProductRepository productRepository;
	private final ReservationRepository reservationRepository;

	public AdminServiceImpl(CategoryRepository categoryRepository,ProductRepository productRepository,ReservationRepository reservationRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.reservationRepository = reservationRepository;
	}
	@Override
	public CategoryDto postCategory(CategoryDto categoryDto) throws IOException {
		Category category=new Category();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setImg(categoryDto.getImg().getBytes());
		
		Category createdCategory=categoryRepository.save(category);
		CategoryDto createCategoryDto=new CategoryDto();
		createCategoryDto.setId(createdCategory.getId());
		return createCategoryDto;
		
	}
	public List<CategoryDto> getAllCategories(){
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}
	@Override
	public List<CategoryDto> getAllCategoriesByTitle(String title) {
		return categoryRepository.findAllByNameContainingIgnoreCase(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}
	//product Operations
	@Override
	public ProductDto postProduct(Long categoryId, ProductDto productDto) throws Exception {

	    Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

	    if (optionalCategory.isPresent()) {

	        Product product = new Product();
	        BeanUtils.copyProperties(productDto, product);

	        product.setImg(productDto.getImg().getBytes());
	        product.setCategory(optionalCategory.get());

	        Product savedProduct = productRepository.save(product);

	        ProductDto responseDto = new ProductDto();
	        responseDto.setId(savedProduct.getId()); // ✅ FIX
	        responseDto.setName(savedProduct.getName());
	        responseDto.setPrice(savedProduct.getPrice());
	        responseDto.setDescription(savedProduct.getDescription());

	        return responseDto;
	    }

	    return null;
	}

	@Override
	public List<ProductDto> getAllProductsByCategory(Long categoryId) {
		
		return productRepository.findByCategory_Id(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
	}
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<ProductDto> getProductsByCategoryAndTitle(Long categoryId,String title) {
		return productRepository.findAllByCategoryIdAndNameContaining(categoryId,title).stream().map(Product::getProductDto).collect(Collectors.toList());
	}
	@Override
	public void deleteProduct(Long id) {
	    Optional<Product> optionalProduct = productRepository.findById(id);

	    if(optionalProduct.isPresent()){
	        productRepository.deleteById(id);
	    } else {
	        throw new IllegalArgumentException("Product With Id :"+id+" Not Found");
	    }
	}

	@Override
	public ProductDto updateProduct(Long productId, ProductDto productDto) throws Exception {
		Optional<Product> optionalProduct=productRepository.findById(productId);
		if(optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setPrice(productDto.getPrice());
			if(productDto.getImg()!=null) {
				product.setImg(productDto.getImg().getBytes());
			}
			Product updatedProduct=productRepository.save(product);
			ProductDto updatedProductDto =new ProductDto();
			updatedProductDto.setId(updatedProduct.getId());
			return updatedProductDto;
			
		}
		return null;
	}
	@Override
	public ProductDto getProductById(Long productId) {
		Optional<Product> optionalProduct  = productRepository.findById(productId);
		return optionalProduct.map(Product::getProductDto).orElse(null);
	}
	@Override
	public List<ReservationDto> getReservations() {
		return reservationRepository.findAll().stream().map(Reservation::getReservationDto).collect(Collectors.toList());
		
	}
	@Override
	public ReservationDto changeReservationStatus(Long reservationId, String status) {

	    Optional<Reservation> optionalReservation =
	            reservationRepository.findById(reservationId);

	    if(optionalReservation.isPresent()) {

	        Reservation existReservation = optionalReservation.get();

	        if ("APPROVED".equalsIgnoreCase(status)) {
	            existReservation.setReservationStatus(ReservationStatus.APPROVED);
	        } 
	        else if ("DISAPPROVED".equalsIgnoreCase(status)) {
	            existReservation.setReservationStatus(ReservationStatus.DISAPPROVED);
	        }

	        Reservation updatedReservation =
	                reservationRepository.save(existReservation);

	        return updatedReservation.getReservationDto();
	    }

	    return null;
	}

	
}
