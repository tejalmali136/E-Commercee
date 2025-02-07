package com.wipro.bankofamerica.estore.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.bankofamerica.estore.exception.ProductNotFoundException;
import com.wipro.bankofamerica.estore.model.Product;
import com.wipro.bankofamerica.estore.service.ProductService;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;

	/**
	 * This method is used to get all the product details.
	 * 
	 */
	@GetMapping("/getAll")
	public @ResponseBody Iterable<Product> getAllProduct() throws Exception {
		logger.info("Product Rest Controller Implementation : getAllProduct() method");
		return productService.getAllProduct(); // here we are calling //
	}

	/*
	 * This method is used to store the product details for particular user.
	 */

	@PostMapping("/save")
	public ResponseEntity<Product> createProduct(@RequestBody Product product, HttpServletRequest request)
			throws Exception {
		Product createdProduct = this.productService.saveProduct(product); //press fn+f6
		logger.info("Product Rest Controller Implementation : createProduct() method");
		return ResponseEntity.ok().body(createdProduct);
	}

	/*
	 * This method is used to get the product details by using id
	 */
	@GetMapping("/get/{id}")   //1
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id) throws Exception {
		Product product = productService.getProductById(id); //id should be same
		logger.info("Product Rest Controller Implementation : getProductById() method");
		return ResponseEntity.ok().body(product);
	}

	/*
	 * This method is used to delete the product details of user by using id.
	 */

	@DeleteMapping("/delete/{id}")
	public Map<String, String> deleteProductById(@PathVariable("id") Integer id) throws Exception {
		Product product = productService.getProductById(id); //9
		logger.info("Product Rest Controller Implementation : deleteProductById() method");
		if (product != null) { //9!=null
			productService.deleteProduct(id); //9
			return Collections.singletonMap("success", "Record Deleted Successfully");
		} else {
			return Collections.singletonMap("fail", "Something is wrong");
		}

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProductById(@PathVariable("id") Integer id, @RequestBody Product product) throws Exception {
		Product products = productService.getProductById(id);
		products.setProductId(product.getProductId());
		products.setAmount(product.getAmount());
		products.setProductDescription(product.getProductDescription());
		products.setProductName(product.getProductName());
		products.setQuantity(product.getQuantity());
		products.setStatus(product.getStatus());
		Product products2 = productService.saveProduct(products);
		return ResponseEntity.ok().body(products2);
	}

	
	
	@GetMapping("api/products/download/excel")
	public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
	    ByteArrayInputStream in = productService.exportProductsToExcel();

	    // Prepare headers for file download
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attachment; filename=products.xlsx");

	    // Return ResponseEntity with InputStreamResource
	    InputStreamResource resource = new InputStreamResource(in);
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	            .body(resource);
	}
	
	@GetMapping("/application/pdf")
	public ResponseEntity<Void> downloadProductPdf(HttpServletResponse res) {
		ByteArrayInputStream pdf = productService.exportProductsToPDF();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attchement: filename=product_report.pdf");
		headers.add("Content-Type", "application/pdf");
		
		try {
			OutputStream outputStream = res.getOutputStream();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while((bytesRead = pdf.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			pdf.close();
			outputStream.flush();
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok().headers(headers).build();
		
	}

}
