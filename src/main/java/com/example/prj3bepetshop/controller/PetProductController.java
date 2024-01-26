package com.example.prj3bepetshop.controller;

import com.example.prj3bepetshop.domain.Product;
import com.example.prj3bepetshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class PetProductController {

   private final ProductService service;


   @GetMapping("")
   public  List<Product> allProductList(){

      return service.selectAll();
   }


   @GetMapping("{id}")
   public Product getProduct(@PathVariable Integer id){

      return service.selectById(id);
   }


   @GetMapping("category/{category}")
   public List<Product> selectCategoryList(@PathVariable String category){

      return service.selectByCategory(category);
   }


}
