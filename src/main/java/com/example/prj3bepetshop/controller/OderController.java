package com.example.prj3bepetshop.controller;


import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.domain.Order;
import com.example.prj3bepetshop.domain.Product;
import com.example.prj3bepetshop.service.OderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OderController {

    private final OderService service;

    @PostMapping("")
    public ResponseEntity addOrder(
            @RequestBody Order Order,
            @SessionAttribute(value = "login",required = false) Member login
    ){
        if(login==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }

        if(service.save(Order,login)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }

    }


    @GetMapping("{email}")
    public List<Order> selectOrderList(@PathVariable String email){

        return service.selectByOrder(email);
    }


}
