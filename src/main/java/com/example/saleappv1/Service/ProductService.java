package com.example.saleappv1.Service;

import com.example.saleappv1.Model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public List<Product> getAllProducts() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("data/products.json").getInputStream();
            return mapper.readValue(inputStream, new TypeReference<List<Product>>(){});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public Product getProductById(int id) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null; 
    }
    public List<Product> searchProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        List<Product> allProducts = getAllProducts();
        List<Product> result = new ArrayList<>();

        for (Product p : allProducts) {
            boolean match = true;

            if (categoryId != null && p.getCategoryId() != categoryId) {
                match = false;
            }
                   if (keyword != null && !keyword.trim().isEmpty() && !p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                match = false;
            }
            if (fromPrice != null && p.getPrice() < fromPrice) {
                match = false;
            }
            if (toPrice != null && p.getPrice() > toPrice) {
                match = false;
            }

            if (match) {
                result.add(p);
            }
        }
        return result;
    }
}