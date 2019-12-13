package shopper.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shopper.Interfaces.ProductRepository;
import shopper.Interfaces.ProductTypeRepository;
import shopper.Models.Product;
import shopper.Models.ProductType;
import shopper.Services.ProductService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/addProduct")
    public ModelAndView addProduct() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        ProductType productType = new ProductType();

        List<ProductType> typesList = productService.findAllByOrderAndType();

        modelAndView.addObject("productTypes", typesList);
        modelAndView.addObject("product", product);
        modelAndView.addObject("productType", productType);

        modelAndView.setViewName("addProduct");
        return modelAndView;
    }

    @PostMapping("/addProduct")
    public ModelAndView addProduct(@ModelAttribute("productType") ProductType prodType,
                                   @Valid @ModelAttribute("product") Product product,
                                   BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors() || product.getName().trim().length() == 0 ||
                product.getDescription().trim().length() == 0) {
            modelAndView.setViewName("addProduct");
            modelAndView.addObject("productTypes", productService.findAllByOrderAndType());
        } else {
//            product.setName(product.getName().trim());
//            product.setDescription(product.getDescription().trim());
//            productService.saveProduct(product);
            addProductApi(product);
            modelAndView.setViewName("index");
        }

        return modelAndView;
    }

//    @PostMapping
    public ResponseEntity<Product> addProductApi(@RequestBody Product product) {
        product.setName(product.getName().trim());
        product.setDescription(product.getDescription().trim());
        productService.saveProduct(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:8080/products/" + product.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/addType")
    public ModelAndView addType(@ModelAttribute("product") Product product,
                                @Valid @ModelAttribute("productType") ProductType prodType,
                                BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors() || prodType.getType().trim().length() == 0) {
            modelAndView.addObject("productTypes", productService.findAllByOrderAndType());
            modelAndView.setViewName("addProduct");
        } else {
            prodType.setType(prodType.getType().trim());
            productService.saveProductType(prodType);
            modelAndView.setViewName("index");
        }

        return modelAndView;
    }

    @GetMapping("/getProducts")
    public ModelAndView findProducts(@RequestParam("productName") String productName,
                                     Model model) {
        ModelAndView modelAndView = new ModelAndView();

        List<Product> productList = productService.findAllProductsByName(productName);

        if(productList.size() == 0) {
            modelAndView.addObject("productNotFound", "Product " + productName + " not found");
            modelAndView.setViewName("index");
        } else{
            model.addAttribute("products", productList);
            modelAndView.setViewName("getProducts");
        }

        return modelAndView;
    }

    @GetMapping("/getProduct/{id}")
    public ModelAndView findProductById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Optional<Product> product = findProductByIdApi(id);
            modelAndView.addObject("productById", product.get());
            modelAndView.setViewName("getProductById");
        } catch (Exception e){
            modelAndView.setViewName("index");
            return modelAndView;
        }
        return modelAndView;
    }

    public Optional<Product> findProductByIdApi(Long id) {
        return productService.getProductById(id);
    }
}
