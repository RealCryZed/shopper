package shopper.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import shopper.Models.Product;
import shopper.Services.ProductService;

import java.util.*;

@Controller
@CrossOrigin(origins = "*")
public class FeatureController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        List<Product> listOfProducts = productService.findAllProducts();
        List<Product> randListOfProducts = new ArrayList<>();

        Collections.shuffle(listOfProducts);

        for (int i = 0; i < 6; i++) {
            randListOfProducts.add(listOfProducts.get(i));
        }

        modelAndView.addObject("randomProductList", randListOfProducts);
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/error")
    public ModelAndView errorPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("error");

        return modelAndView;
    }
}
