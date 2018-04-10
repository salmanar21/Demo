package sapient.test.demo.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sapient.test.demo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByType(String type);

}
