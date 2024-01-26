package com.example.prj3bepetshop.domain;

import lombok.Data;

@Data
public class Product {

    private Integer no;

    private String category;

    private String title;

    private  Integer price;

    private  Integer total;

    private  Integer  quantity;

    private Integer reviewStatus;

    private  String fileName;

    private  String url;

}
