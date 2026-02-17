package com.restaurant.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.restaurant.entities.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCategory_Id(Long categoryId);

	List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String title);

	List<Product> findAllByCategoryId(Long categoryId);
	

}
