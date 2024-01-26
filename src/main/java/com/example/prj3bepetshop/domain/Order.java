package com.example.prj3bepetshop.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {

    private Integer no;

    private String consumer;

    private Integer productNo;

    private String productName;

    private String category;

    private List<Product> products;

    private Integer quantity;

    private  Integer total;

    private Integer detailNo;

    private Integer reviewStatus;

    private Integer totalPrice;

    private  String url;

    private LocalDateTime purchaseDate;


}
