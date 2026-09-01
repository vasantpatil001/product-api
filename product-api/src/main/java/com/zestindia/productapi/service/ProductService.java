package com.zestindia.productapi.service;

import com.zestindia.productapi.entity.AppUser;
import com.zestindia.productapi.entity.Product;
import com.zestindia.productapi.exception.ResourceNotFoundException;
import com.zestindia.productapi.repository.AppUserRepository;
import com.zestindia.productapi.repository.ProductRepository;
import com.zestindia.productapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create Product
    public Product createProduct(Product product) {

        product.setCreatedOn(LocalDateTime.now());

        return productRepository.save(product);
    }

    // Get All Products
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    // Get Product By ID
    public Product getProductById(Integer id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );
    }

    // Update Product
    public Product updateProduct(Integer id, Product product) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        existingProduct.setProductName(product.getProductName());
        existingProduct.setModifiedBy(product.getModifiedBy());
        existingProduct.setModifiedOn(LocalDateTime.now());

        return productRepository.save(existingProduct);
    }

    // Delete Product
    public void deleteProduct(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        productRepository.delete(product);
    }

    @Service
    public static class AuthService {

        private final AppUserRepository appUserRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;

        public AuthService(
                AppUserRepository appUserRepository,
                PasswordEncoder passwordEncoder,
                JwtService jwtService) {

            this.appUserRepository = appUserRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtService = jwtService;
        }

        public String login(String username, String password) {

            AppUser user = appUserRepository.findByUsername(username)
                    .orElseThrow(() ->
                            new RuntimeException("Invalid username or password"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid username or password");
            }

            return jwtService.generateToken(
                    user.getUsername(),
                    user.getRole()
            );
        }
    }
}