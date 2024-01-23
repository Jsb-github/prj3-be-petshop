package com.example.prj3bepetshop.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Member {

    private String email;

    private String password;

    private LocalDateTime inserted;

    private List<Auth> auth;


    public boolean isAdmin(){
        if (auth==null){
            auth.stream().map(a->a.getMemberId())
                    .anyMatch(n->n.equals("adim"));
        }
        return false;
    }


}
