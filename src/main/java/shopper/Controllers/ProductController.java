package shopper.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shopper.Interfaces.ProductRepository;
import shopper.Interfaces.ProductTypeRepository;
import shopper.Models.Product;
import shopper.Models.ProductType;
import shopper.Models.User;
import shopper.Services.UserService;

import java.util.Collections;
import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository prodRepo;

    @Autowired
    private ProductTypeRepository prodTypeRepo;

    @GetMapping("/addProduct")
    public ModelAndView addProduct(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<ProductType> typesList = (List<ProductType>) prodTypeRepo.findAllByOrderByType();
        model.addAttribute("productTypes", typesList);

        return new ModelAndView("addProduct");
    }

    @PostMapping("/addProduct")
    public ModelAndView addProduct(@RequestParam(value = "productId") Long id,
                                   @RequestParam(value = "productName") String name,
                                   @RequestParam(value = "productPrice") int price,
                                   @RequestParam(value = "productDescription") String description,
                                   @RequestParam(value = "productType") String type) {
        Product product = new Product();
        if (name.trim().length() == 0 || description.trim().length() == 0) {
            return new ModelAndView("redirect:/addProduct");
        } else {
            product.setId(id);
            product.setName(name.trim());
            product.setPrice(price);
            product.setDescription(description.trim());
            product.setType(type);
            prodRepo.save(product);
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/getProducts")
    public ModelAndView findProducts(
            @RequestParam("productName") String productName,
            Model model) {
        List<Product> productList = prodRepo.findAllByNameContainsIgnoreCase(productName);
        model.addAttribute("products", productList);

        return new ModelAndView("getProducts");
    }

    @PostMapping("/addType")
    public ModelAndView addType(@RequestParam(value = "addingProductType") String type) {
        ProductType prodType = new ProductType();
        if (type.trim().length() == 0) {
            return new ModelAndView("redirect:/addProduct");
        } else {
            prodType.setType(type.trim());
            prodTypeRepo.save(prodType);
        }

        return new ModelAndView("redirect:/addProduct");
    }
}
