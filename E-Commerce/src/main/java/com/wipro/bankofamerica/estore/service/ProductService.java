package com.wipro.bankofamerica.estore.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.wipro.bankofamerica.estore.model.Product;

public interface ProductService {
	
	public Iterable<Product> getAllProduct();
	public Product saveProduct(Product product);
	public Product getProductById(Integer Id) throws Exception;
	public void deleteProduct(Integer id);
	
	ByteArrayInputStream exportProductsToExcel() throws IOException;
	ByteArrayInputStream exportProductsToPDF();

}
