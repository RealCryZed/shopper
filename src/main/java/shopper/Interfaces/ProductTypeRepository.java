package shopper.Interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shopper.Models.ProductType;

import java.util.List;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {

}
