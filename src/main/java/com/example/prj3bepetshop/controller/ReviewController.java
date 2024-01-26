package com.example.prj3bepetshop.controller;

import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.domain.Review;
import com.example.prj3bepetshop.service.reviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final reviewService service;


    @PostMapping("add")
    public ResponseEntity add(
            Review review,
            @RequestParam(value = "no",required = false) Integer no,
            @RequestParam(value = "uploadFiles[]" ,required = false)MultipartFile[] files,
            @SessionAttribute(value = "login",required = false)Member login
            )throws Exception{
       if (login==null){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }

       if(!service.hasAceess(login)){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
       }

       if (!service.reivewValidate(review,files)){
           return ResponseEntity.badRequest().build();
       }

       if ((service.save(review,files,login,no))){
           return ResponseEntity.ok().build();
       }else {
           return ResponseEntity.internalServerError().build();
       }

    }
}
