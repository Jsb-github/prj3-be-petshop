package com.example.prj3bepetshop.service;

import com.example.prj3bepetshop.domain.Auth;
import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper mapper;
    public boolean validate(Member member) {

        if (member.getEmail().isBlank()){
            return false;
        }

        if (member.getPassword().isBlank()){
            return false;
        }

      Integer checkEmail =  mapper.checkEmail(member.getEmail());

        System.out.println(checkEmail);

        if (checkEmail != null){
            return false;
        }

        return true;
    }

    public boolean add(Member member) {
        return mapper.add(member) == 1;
    }

    public boolean login(Member member, WebRequest request) {

        Member dbmember = mapper.selectEmail(member.getEmail());

        if(dbmember != null){
            if(dbmember.getPassword().equals(member.getPassword())){
                List<Auth> auth = mapper.selectAuthById(member.getEmail());
                dbmember.setAuth(auth);

                dbmember.setPassword("");
                request.setAttribute("login",dbmember, RequestAttributes.SCOPE_SESSION);
                return true;
            }


        }

        return false;
    }

    public boolean hasAccess(String email, Member login) {
        if(isAdmin(login)){
            return true;
        }
        return login.getEmail().equals(email);
    }

    public boolean isAdmin(Member login){
        if(login.getAuth() !=null){
            return login.getAuth()
                    .stream()
                    .map(e -> e.getMemberId())
                    .anyMatch(n->n.equals("admin"));
        }
        return false;
    }




}
