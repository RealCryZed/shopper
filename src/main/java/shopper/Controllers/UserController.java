package shopper.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shopper.Models.Product;
import shopper.Models.User;
import shopper.Services.ProductService;
import shopper.Services.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class UserController {

   @Autowired
   private UserService userService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult.rejectValue("username", "Username was already registered");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }
        
        return modelAndView;
    }

    @GetMapping("/accountInfo")
    public ModelAndView getAccountInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findAllUserInfoByUsername(auth.getName());
        List<Product> productList = productService.findAllProductsByUser(user.getUsername());

        model.addAttribute("loggedUser", user);
        model.addAttribute("userProducts", productList);

        return new ModelAndView("accountInfo");
    }
}
