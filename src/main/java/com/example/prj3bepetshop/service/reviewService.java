package com.example.prj3bepetshop.service;

import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.domain.Review;
import com.example.prj3bepetshop.mapper.reviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class reviewService {

    private final reviewMapper mapper;

    public boolean hasAceess(Member login) {
        return true;
    }

    public boolean reivewValidate(Review review, MultipartFile[] files) {
        return true;
    }

    public boolean save(Review review, MultipartFile[] files, Member login, Integer no) {
        return true;
    }
}
