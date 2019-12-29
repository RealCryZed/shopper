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

    public void deleteProductById(Integer id) {
        productRepo.deleteById(id);
    }

    public Product getProductById(Integer id) {
        return productRepo.findProductById(id);
    }

    public Boolean doesProductExistById(Integer id) {
        if (productRepo.findProductById(id) == null) {
            return false;
        }
        return true;
    }

    public List<Product> findAllProductsByName(String productName) {
        return productRepo.findAllByNameContainsIgnoreCase(productName);
    }

    public List<Product> findAllProductsByUser(String username) {
        return productRepo.findAllByUsername(username);
    }

    public List<ProductType> findAllByOrderAndType() {
        return productTypeRepo.findAllByOrderByType();
    }
}
