package com.ecommerce.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.product.pojo.Products;
import com.ecommerce.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepo;
	
	public List<Products> getProducts(){
		return productRepo.findAll();
	}
	
	public Optional<Products> getProductsById(long id){
		return productRepo.findById(id);
	}
	
	public Products createProduct(Products products) {
		return productRepo.save(products);
	}
	
	public Products updateProduct(Long id, Products updatedProduct) {
	    Products byId = productRepo.findProductById(id);
	    if(byId != null) {
	    	byId.setName(updatedProduct.getName());
	    	byId.setDescription(updatedProduct.getDescription());
	    	byId.setPrice(updatedProduct.getPrice());
	    	byId.setStock(updatedProduct.getStock());
	    }
	    return byId;
	}

	public void deleteById(Long id) {
		 productRepo.deleteById(id);
	}
	
	
	}

