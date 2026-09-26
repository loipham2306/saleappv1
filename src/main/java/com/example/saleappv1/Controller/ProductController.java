package com.example.saleappv1.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.saleappv1.Model.Product;
import com.example.saleappv1.Service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String showProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double fromPrice,
            @RequestParam(required = false) Double toPrice,
            Model model) {

        // Gọi hàm search thay vì getAllProducts
        List<Product> filteredProducts = productService.searchProducts(categoryId, keyword, fromPrice, toPrice);
        
        model.addAttribute("products", filteredProducts);
        return "web/products"; 
    }
    @GetMapping("/products/{productId}")
    public String showProductDetail(@PathVariable int productId, Model model) {
        Product product = productService.getProductById(productId);
        
        if (product != null) {
            model.addAttribute("product", product);
            return "web/product-detail"; 
        } else {
            return "redirect:/products"; 
        }
    }
}