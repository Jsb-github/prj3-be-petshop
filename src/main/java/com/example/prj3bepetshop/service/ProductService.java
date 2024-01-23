package com.example.prj3bepetshop.service;

import com.example.prj3bepetshop.domain.Product;
import com.example.prj3bepetshop.mapper.PetProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final PetProductMapper mapper;

    private final S3Client s3;


    @Value("${image.file.prefix}")
    private String urlPrefix;
    @Value("${aws.bucketName}")
    private String bucket;

    public  List<Product> selectAll() {
        List<Product> list = mapper.selectAll();

        for(Product product :list){
            String url = urlPrefix + "prj3/petProduct/"+ product.getCategory()+"/"+ product.getFileName();
            product.setUrl(url);
        }

        return list;
    }

    public List<Product> selectByCategory(String category) {
        List<Product> list = mapper.selectByCategory(category);

        for(Product product :list){
            String url = urlPrefix + "prj3/petProduct/"+ product.getCategory()+"/"+ product.getFileName();
            product.setUrl(url);
        }

        return list;
    }

    public Product selectById(Integer id) {
        Product product = mapper.selectByProduct(id);

        String url = urlPrefix + "prj3/petProduct/"+ product.getCategory()+"/"+ product.getFileName();
        product.setUrl(url);

        return product;
    }
}
