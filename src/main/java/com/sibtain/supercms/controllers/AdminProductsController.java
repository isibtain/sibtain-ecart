package com.sibtain.supercms.controllers;

import com.sibtain.supercms.models.CategoryRepository;
import com.sibtain.supercms.models.ProductRepository;
import com.sibtain.supercms.models.data.Category;
import com.sibtain.supercms.models.data.Page;
import com.sibtain.supercms.models.data.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;



    @GetMapping
    public String index(Model model){
     List<Product> products = productRepository.findAll();
     model.addAttribute("products",products);
     return "admin/products/index";
    }


    @GetMapping("/add")
    public String add(@ModelAttribute Product product, Model model) {

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "admin/products/add";
    }

    @PostMapping("/add")
    public String add(@Valid Product product, BindingResult bindingResult, MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws IOException {


        List<Category> categories = categoryRepository.findAll();

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categories);
            return "admin/products/add";
        }

        boolean fileOk = false;

        byte[] bytes = file.getBytes();

        String filename= file.getOriginalFilename();
         Path path = Paths.get("src/main/resources/static/media/" +filename);

         if (filename.endsWith("jpg") || filename.endsWith("png"))
             fileOk=true;

        redirectAttributes.addFlashAttribute("message", "Product added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");
        Product productExists = productRepository.findBySlug(slug);

        if (! fileOk){
            redirectAttributes.addFlashAttribute("message", "Image must a jpg or png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        }

        else if ( productExists != null ) {
            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);

        } else {
            product.setSlug(slug);
            product.setImage(filename);
            productRepository.save(product);
            Files.write(path, bytes);
        }

        return "redirect:/admin/products/add";
    }

}
