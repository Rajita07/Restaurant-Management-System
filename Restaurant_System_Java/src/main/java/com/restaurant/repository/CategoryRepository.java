package com.restaurant.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.restaurant.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	List<Category> findAllByNameContainingIgnoreCase(String title);

	List<Category> findAllByNameContaining(String title);


}
