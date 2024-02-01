package com.example.prj3bepetshop.controller;

import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.domain.Notice;
import com.example.prj3bepetshop.domain.Review;
import com.example.prj3bepetshop.service.reviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final reviewService service;




    @GetMapping("")
    public List<Review> allNoticeList(){

        return service.selectAll();
    }


    @GetMapping("{no}")
    public Review selectReview(
            @PathVariable Integer no
    ){
        return service.selectByNo(no);
    }


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

       if (!service.writeHasAceess(no,login)){
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


    @PutMapping("edit")
    public ResponseEntity edit(
            Review review,
            @RequestParam(value = "point" , required = false)Integer point,
            @RequestParam(value = "removeFileIds[]" , required = false)List<Integer> removeFileIds,
            @RequestParam(value = "uploadFiles[]" , required = false)MultipartFile[] files,
            @SessionAttribute(value = "login" , required = false) Member login
            )throws IOException{
        if(login == null){
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        if(!service.hasAceess(review.getNo(), login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if(!service.validFileNumber(removeFileIds,files,review)){
            return ResponseEntity.badRequest().build();
        }

        if (!service.reivewValidate(review,files)){
            return ResponseEntity.badRequest().build();
        }

        if (service.update(review,point,removeFileIds,files)){
            return  ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("remove/{no}")
    public ResponseEntity remove(
            @PathVariable Integer no,
            @SessionAttribute(value = "login",required = false)Member login
    ) {

       
        if (login == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        if (!service.hasAceess(no,login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        if (service.remove(no)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }

    }



}
