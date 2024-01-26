package com.example.prj3bepetshop.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {

    private Integer no;
    private String title;
    private String writer;
    private String fileName;
    private String url;
    private LocalDateTime inserted;


}
