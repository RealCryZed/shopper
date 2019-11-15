package shopper.Interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shopper.Models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
}
