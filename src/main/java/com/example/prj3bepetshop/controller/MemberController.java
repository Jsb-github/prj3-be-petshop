package com.example.prj3bepetshop.controller;

import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService service;

    @PostMapping("signup")
    public ResponseEntity signup(@RequestBody Member member){

        if(service.validate(member)){
            if (service.add(member)){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.internalServerError().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody Member member, WebRequest request){
        if(service.login(member,request)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("login")
    public Member login (@SessionAttribute(value = "login", required = false) Member login){
        return login;
    }

    @PostMapping("logout")
    public void logout(HttpSession session){
        if (session != null){
            session.invalidate();
        }
    }
}
