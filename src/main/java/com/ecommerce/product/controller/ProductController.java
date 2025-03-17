package com.ecommerce.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.pojo.Products;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductRepository productRepo;
	@Autowired
	ProductService productService;
	
	
	@PostMapping("/saveProduct")
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Products>createProduct(@RequestBody Products products){
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepo.save(products));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Products> getProductsById(@PathVariable long id){
		return productService.getProductsById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Products> updateProduct(@PathVariable long id, @RequestBody Products products) {
		return ResponseEntity.ok(productService.updateProduct(id, products));
	}
}
