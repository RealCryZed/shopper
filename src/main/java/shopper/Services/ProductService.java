package shopper.Services;

import org.springframework.stereotype.Service;
import shopper.Interfaces.ProductRepository;
import shopper.Interfaces.ProductTypeRepository;
import shopper.Models.Product;
import shopper.Models.ProductType;

import java.util.List;

@Service("productService")
public class ProductService {

    private ProductRepository productRepo;
    private ProductTypeRepository productTypeRepo;

    public ProductService(ProductRepository productRepo, ProductTypeRepository productTypeRepo) {
        this.productRepo = productRepo;
        this.productTypeRepo = productTypeRepo;
    }

    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    public void saveProductType(ProductType productType) {
        productTypeRepo.save(productType);
    }

    public List<Product> findAllProductsByName(String productName) {
        return productRepo.findAllByNameContainsIgnoreCase(productName);
    }

    public List<ProductType> findAllByOrderAndType() {
        return productTypeRepo.findAllByOrderByType();
    }
}
