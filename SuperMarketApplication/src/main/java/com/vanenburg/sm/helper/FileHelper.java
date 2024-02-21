package com.vanenburg.sm.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.vanenburg.sm.domain.Product;
import com.vanenburg.sm.enums.Category;

@Component
public class FileHelper {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "id", "product_title", "price", "product_quantity", "category", "min_stock",
			"exp_date" };

	public boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public List<Product> csvToProducts(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<Product> products = new ArrayList<Product>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				Product product = new Product();

				product.setId(Integer.parseInt(csvRecord.get("id")));
				product.setName(csvRecord.get("product_title"));
				product.setPrice(Double.parseDouble(csvRecord.get("price")));
				product.setQuantity(Integer.parseInt(csvRecord.get("product_quantity")));
				product.setCategory(Category.valueOf(csvRecord.get("category")));
				product.setMinStockThreshold(Integer.parseInt(csvRecord.get("min_stock")));
				product.setExpirationDate(LocalDate.parse(csvRecord.get("exp_date")));
				products.add(product);
			}

			return products;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}
	

	  public  ByteArrayInputStream productsToCSV(List<Product> products) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	      for (Product product : products) {
	        List<String> data = Arrays.asList(
	              String.valueOf(product.getId()),
	              product.getName(),
	              String.valueOf(product.getPrice()),
	              String.valueOf(product.getQuantity()),
	              product.getCategory().toString(),
	              String.valueOf(product.getMinStockThreshold()),
	              product.getExpirationDate().toString()
	            );

	        csvPrinter.printRecord(data);
	      }

	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }

}
