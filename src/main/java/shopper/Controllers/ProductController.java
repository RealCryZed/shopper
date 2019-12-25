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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shopper.Models.Product;
import shopper.Models.ProductType;
import shopper.Services.ProductService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
            addProductApi(product);
            modelAndView.setViewName("index");
        }

        return modelAndView;
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

    @GetMapping("/editProduct/{id}")
    public ModelAndView getEditProductById(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product product = productService.getProductById(id);

        if (auth.getName().equals(product.getUsername())) {
            List<ProductType> typesList = productService.findAllByOrderAndType();
            modelAndView.addObject("productTypes", typesList);
            modelAndView.addObject("productToEdit", product);
            modelAndView.setViewName("editProduct");
        } else {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @GetMapping("/editProduct/error/{id}")
    public ModelAndView getEditProductByIdError(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product product = productService.getProductById(id);

        System.err.println("Inside error Page");

        if (auth.getName().equals(product.getUsername())) {
            List<ProductType> typesList = productService.findAllByOrderAndType();

            System.err.println("Inside error getMapping");
            modelAndView.addObject("error", true);
            modelAndView.addObject("errorMessage", "Fix the problems and try again!");

            modelAndView.addObject("productTypes", typesList);
            modelAndView.addObject("productToEdit", product);
            modelAndView.setViewName("editProduct");
        } else {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @PostMapping("/editProduct/{id}")
    public ModelAndView editProductById(@PathVariable Integer id,
                                        @Valid @ModelAttribute("productToEdit") Product product,
                                        BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Product productToEdit = productService.getProductById(product.getId());

        if (bindingResult.hasErrors() || product.getName().trim().length() == 0 ||
                product.getDescription().trim().length() == 0) {
            List<ProductType> typesList = productService.findAllByOrderAndType();

            modelAndView.addObject("productToEdit", productToEdit);
            modelAndView.addObject("productTypes", typesList);

            modelAndView.setViewName("redirect:/editProduct/error/" + id);
        } else {
            product.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            productService.saveProduct(product);
            modelAndView.setViewName("redirect:/accountInfo");
        }

        return modelAndView;
    }

    @GetMapping("/deleteProduct/{id}")
    public ModelAndView deleteProduct(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product product = productService.getProductById(id);

        if (auth.getName().equals(product.getUsername())) {
            deleteProductApi(id);
            modelAndView.setViewName("redirect:/accountInfo");
        } else {
            modelAndView.setViewName("redirect:/");
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
    public ModelAndView findProductById(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Product product = productService.getProductById(id);
            modelAndView.addObject("productById", product);
            modelAndView.setViewName("getProductById");
        } catch (Exception e){
            modelAndView.setViewName("index");
            return modelAndView;
        }

        return modelAndView;
    }

    public ResponseEntity<Product> addProductApi(@RequestBody Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        product.setName(product.getName().trim());
        product.setDescription(product.getDescription().trim());
        product.setUsername(auth.getName());
        productService.saveProduct(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:8080/products/" + product.getId()));

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    public void deleteProductApi(Integer id) {
        productService.deleteProductById(id);
    }
}
