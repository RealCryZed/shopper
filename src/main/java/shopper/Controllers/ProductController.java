package shopper.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shopper.Interfaces.ProductRepository;
import shopper.Interfaces.ProductTypeRepository;
import shopper.Models.Product;
import shopper.Models.ProductType;

import java.util.List;

@Controller
@RequestMapping(produces = "application/json")
public class ProductController {

    @Autowired
    private ProductRepository prodRepo;

    @Autowired
    private ProductTypeRepository prodTypeRepo;

    @GetMapping("/getProducts")
    public String findProducts(
            @RequestParam("productName") String productName,
            Model model) {
        List<Product> productList = prodRepo.findAllByNameContainsIgnoreCase(productName);
        model.addAttribute("products", productList);
        return "getProducts";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam(value = "productId") Long id,
                             @RequestParam(value = "productName") String name,
                             @RequestParam(value = "productPrice") int price,
                             @RequestParam(value = "productDescription") String description,
                             @RequestParam(value = "productType") String type) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setType(type);
        prodRepo.save(product);

        return "redirect:/";
    }

    @GetMapping("/addProduct")
    public String addProduct() {
        return "addProduct";
    }

    @PostMapping("/addType")
    public String addType(@RequestParam(value = "addingProductType") String type) {
        ProductType prodType = new ProductType();
        prodType.setType(type);
        prodTypeRepo.save(prodType);

        return "redirect:/";
    }
}
