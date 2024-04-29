package com.example.junit_test.modules;

import com.example.junit_test.modules.products.entities.Product;

import java.util.List;

public class ProductMockData {
    public static List<Product> validProducts() {
        return List.of(
                Product.builder().id(1).name("Iphone 11").image("http").quantity(2).price(10000000).categoryId(100).build(),
                Product.builder().id(2).name("Iphone 12").image("http").quantity(3).price(20000000).categoryId(101).build(),
                Product.builder().id(3).name("Iphone 13").image("http").quantity(4).price(30000000).categoryId(102).build()
        );
    }

    public static Product validProduct() {
        return Product.builder().id(1).name("Iphone 11").image("http").quantity(2).price(10000000).categoryId(100).build();
    }
}
