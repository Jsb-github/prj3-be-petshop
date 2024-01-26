package com.example.prj3bepetshop.domain;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;

import java.time.LocalDateTime;

@Data
public class Notice {

    private Integer no;

    private String title;

    private String writer;

    private String info;

    private LocalDateTime inserted;

}
