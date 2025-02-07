package com.wipro.bankofamerica.estore.repo;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wipro.bankofamerica.estore.model.Product;

public interface ProductRepository extends CrudRepository<Product, Serializable>{
	
	public Product findById(Integer Id);

}
