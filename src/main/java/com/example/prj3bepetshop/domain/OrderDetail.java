package com.example.prj3bepetshop.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDetail {

    private Integer no;

    private String consumer;

    private Integer totalPrice;

    private LocalDateTime purchaseDate;

}
