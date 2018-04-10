package sapient.test.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sapient.test.demo.domain.Product;
import sapient.test.demo.domain.repository.ProductRepository;

@RestController
public class ProductRestService {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/getProduct")
	public List<Product> getProductByType(
			@RequestParam(name = "type", required = false, defaultValue = "defaultType") String type) {
		List<Product> products = productRepository.findByType(type);
		return products;
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public ResponseEntity<String> addProduct(@RequestBody Product product) {
		productRepository.save(product);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
