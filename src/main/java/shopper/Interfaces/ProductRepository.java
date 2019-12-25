package shopper.Interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shopper.Models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findAllByNameContainsIgnoreCase(String name);

    List<Product> findAllByUsername(String username);

    Product findProductById(Integer id);
}