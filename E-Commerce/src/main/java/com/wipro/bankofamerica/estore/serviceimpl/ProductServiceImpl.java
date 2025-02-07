package com.wipro.bankofamerica.estore.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.wipro.bankofamerica.estore.exception.ProductNotFoundException;
import com.wipro.bankofamerica.estore.model.Product;
import com.wipro.bankofamerica.estore.repo.ProductRepository;
import com.wipro.bankofamerica.estore.service.ProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService{
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository repo; // injecting repo object into productserviceimpl class

	@Override
	public Iterable<Product> getAllProduct() {
		logger.info("Product Service Implementation : getAllProduct() method");
		return repo.findAll(); // to get the all list
	}

	@Override
	@Transactional
	public Product saveProduct(Product product) {
		logger.info("Product Service Implementation : saveProduct() method");
		return repo.save(product); // repo---db
	}

	/*
	 * @Override public Product getProductById(Integer Id) {
	 * logger.info("Product Service Implementation : getProductById() method");
	 * Product product=repo.findById(Id);//null if(product==null) {
	 * 
	 * throw new EStoreException("Product id "+Id+" incorrect.."); //calling the
	 * constructor } return product; }
	 */

	@Override
	public void deleteProduct(Integer id) {
		logger.info("Product Service Implementation : deleteProduct() method");
		repo.deleteById(id);
	}

	@Override
	public Product getProductById(Integer id) {
		Product product = repo.findById(id); //3
		if(product != null) {
			return product;
		} else {
			throw new ProductNotFoundException("Product id " + id + " incorrect..");

		}

	}

	@Override
	public ByteArrayInputStream exportProductsToExcel() throws IOException {
		try (Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
			Sheet createSheet = workbook.createSheet("product");
			Row headerRow = createSheet.createRow(0);
			
			headerRow.createCell(0).setCellValue("productId");
			headerRow.createCell(1).setCellValue("productName");
			headerRow.createCell(2).setCellValue("productDescription");
			headerRow.createCell(3).setCellValue("quantity");
			headerRow.createCell(4).setCellValue("amount");
			headerRow.createCell(5).setCellValue("status");
			
			List<Product> products = (List<Product>) repo.findAll();
			int rowIdx = 1;
			
			for(Product product : products) {	
				
			Row row = createSheet.createRow(rowIdx++);
			row.createCell(0).setCellValue(product.getProductId());
			row.createCell(1).setCellValue(product.getProductName());
			row.createCell(2).setCellValue(product.getProductDescription());
			row.createCell(3).setCellValue(product.getQuantity());
			row.createCell(4).setCellValue(product.getAmount());
			row.createCell(5).setCellValue(product.getStatus());
			
			}
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		}catch (Exception e) {
			throw new  RuntimeException("Failed to export data to excel " + e);
		}
	}

	@Override
	public ByteArrayInputStream exportProductsToPDF() {
		List<Product> products = (List<Product>) repo.findAll();
		
		//To hold PDF Content
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			PdfWriter pdfWriter = new PdfWriter(baos);
			PdfDocument pdf = new PdfDocument(pdfWriter);
			Document document = new Document(pdf);
			
			//Add a title to the PDF
			document.add(new Paragraph("Product List Report").setFontSize(18).setBold());
			
			//Add product derails to the PDF
			for(Product product : products) {
				document.add(new Paragraph("Product ID :" + product.getProductId()));
				document.add(new Paragraph("Product Name :" + product.getProductName()));
				document.add(new Paragraph("Product Discription :" + product.getProductDescription()));
				document.add(new Paragraph("Amount :" + product.getAmount()));
				document.add(new Paragraph("Quantity :" + product.getQuantity()));
				document.add(new Paragraph("Status :" + product.getStatus()));
				document.add(new Paragraph("------------------------------------"));
			}
			
			document.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(baos.toByteArray());
	}
	
	}


