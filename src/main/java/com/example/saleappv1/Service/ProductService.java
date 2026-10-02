package com.example.saleappv1.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.saleappv1.Model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    // Đã chuyển đổi logic để tương thích với id kiểu Long của Product mới
    public Product getProductById(int id) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId() != null && product.getId().intValue() == id) {
                return product;
            }
        }
        return null; 
    }

    // Đã cập nhật logic lấy Category ID và chuyển đổi giá từ BigDecimal sang Double
    public List<Product> searchProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        List<Product> allProducts = getAllProducts();
        List<Product> result = new ArrayList<>();

        for (Product p : allProducts) {
            boolean match = true;

            if (categoryId != null && (p.getCategory() == null || p.getCategory().getId().intValue() != categoryId)) {
                match = false;
            }
            
            if (keyword != null && !keyword.trim().isEmpty() && p.getName() != null && !p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                match = false;
            }
            
            if (fromPrice != null && p.getPrice() != null && p.getPrice().doubleValue() < fromPrice) {
                match = false;
            }
            
            if (toPrice != null && p.getPrice() != null && p.getPrice().doubleValue() > toPrice) {
                match = false;
            }

            if (match) {
                result.add(p);
            }
        }
        return result;
    }
}