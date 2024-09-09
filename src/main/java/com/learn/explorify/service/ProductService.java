package com.learn.explorify.service;

import com.learn.explorify.model.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    public Optional<Product> findById(int id){
        if(id==1) return Optional.of(new Product(1,"keyboard",600));
        return Optional.empty();
    }

    public int delete(int id){
        if(id==1) return id;
        return -1;
    }

    public Product save(Product product){
        return product;
    }
}
