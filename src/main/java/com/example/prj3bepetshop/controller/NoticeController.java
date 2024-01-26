package com.example.prj3bepetshop.controller;

import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.domain.Notice;
import com.example.prj3bepetshop.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService service;


    @GetMapping("")
    public List<Notice> allNoticeList(){

        return service.selectAll();
    }



    @GetMapping("{no}")
    public Notice selectNoticeList(
            @PathVariable Integer no
    ){
        return service.selectByNo(no);
    }

    @PostMapping("add")
    public ResponseEntity<Object> add(
          @RequestBody Notice notice,
          @SessionAttribute Member login
    ){
        if (!service.hasAccess(login)){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        if (!service.NoticeValidate(notice)){
            return ResponseEntity.badRequest().build();
        }

        if (service.save(notice,login)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping("edit")
    public ResponseEntity edit(
        @RequestBody Notice notice,
        @SessionAttribute(value = "login",required = false)Member login
    ){
        if (login==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
        }

        if (!service.NoticeValidate(notice)){
            return  ResponseEntity.badRequest().build();
        }


        if (!service.hasAccess(login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        if (service.update(notice,login)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("remove/{no}")
    public ResponseEntity remove(
            @PathVariable Integer no,
            @SessionAttribute(value = "login", required = false) Member login
    ){
        if (login==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        if(!service.hasAccess(login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        if (service.remove(no)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }

    }
}
