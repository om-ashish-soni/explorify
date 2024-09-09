package com.learn.explorify.controller;

import com.learn.explorify.model.Product;
import com.learn.explorify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.Objects.isNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        return productService
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody Product product) {
        if (isNull(product.name())) return ResponseEntity.badRequest().build();
        return ResponseEntity.created(URI.create("".concat(String.format("/product/%s", product.id())))).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        if (productService.delete(id) != -1) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
