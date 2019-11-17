package shopper.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shopper.Interfaces.ProductRepository;
import shopper.Models.Product;

@Controller
@RequestMapping(produces = "application/json")
public class ProductController {

    @Autowired
    private ProductRepository prodRepo;

    @GetMapping("/getProducts")
    public String findProducts(
            @RequestParam("productName") String productName,
            Model model) {
        model.addAttribute("products", prodRepo.findAllByName(productName));
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
}
