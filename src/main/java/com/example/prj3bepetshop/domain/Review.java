package com.example.prj3bepetshop.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Review {

    private Integer no;

    private String title;

    private String writer;

    private String info;

    private Integer point;

    private List<ReviewFile> files;

    private LocalDateTime inserted;

}
