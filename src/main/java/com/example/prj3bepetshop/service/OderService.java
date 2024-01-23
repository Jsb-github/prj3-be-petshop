package com.example.prj3bepetshop.service;

import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.domain.Order;
import com.example.prj3bepetshop.domain.OrderDetail;
import com.example.prj3bepetshop.domain.Product;
import com.example.prj3bepetshop.mapper.OrderMapper;
import com.example.prj3bepetshop.mapper.PetProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OderService {

    private final OrderMapper mapper;

    private final PetProductMapper petProductMapper;


    private final S3Client s3;


    @Value("${image.file.prefix}")
    private String urlPrefix;
    @Value("${aws.bucketName}")
    private String bucket;
    public boolean save(Order order, Member login) {

        OrderDetail detail = new OrderDetail();
        detail.setConsumer(login.getEmail());
        detail.setTotalPrice(order.getTotalPrice());
        mapper.saveDetail(detail);

        for (Product product : order.getProducts()) {
            order.setConsumer(login.getEmail());
            order.setProductNo(product.getNo());
            order.setQuantity(product.getQuantity());
            order.setProductName(product.getTitle());
            order.setTotal(product.getTotal());
            order.setDetailNo(detail.getNo());
            mapper.saveGoods(order);
        }

        return true;
    }

    public List<Order> selectByOrder(String email) {

        List<Order> orderList = mapper.selectByDetail(email);

        for(Order order : orderList){
               List<Product>  products = petProductMapper.selectById(order.getNo());
               for (Product product :products){
                   String url = urlPrefix + "prj3/petProduct/"+ product.getCategory() +"/"+ product.getFileName();
                   order.setProducts(products);
                   product.setUrl(url);
                   order.setCategory(product.getCategory());
               }
            }

     return orderList;

    }
}
