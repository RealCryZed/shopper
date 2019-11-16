package shopper.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shopper.Interfaces.ProductRepository;

@Controller
public class ProductController {

    @Autowired
    ProductRepository prodRepo;

    @GetMapping("/getProducts")
    public String findProducts(
            @RequestParam("productName") String productName,
            Model model) {
        model.addAttribute("products", prodRepo.findAllByName(productName));
        return "getProducts";
    }
}
