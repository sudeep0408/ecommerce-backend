package com.ecommerce.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.product.pojo.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

	List<Products>findProductByName(String name);
	Products findProductById(long id);
	
}
