package com.example.prj3bepetshop.service;

import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.domain.Notice;
import com.example.prj3bepetshop.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeMapper mapper;

    public boolean hasAccess(Member login) {


        boolean admin =login.getAuth().stream()
                .map(n-> n.getManager())
                .anyMatch(a->a.equals("admin"));

        if (!admin){
            return false;
        }
        return  admin;
    }

    public boolean NoticeValidate(Notice notice) {

        if (notice.getTitle() == null || notice.getTitle().isBlank()){
            return false;
        }

        if (notice.getInfo() == null || notice.getInfo().isBlank()){
            return false;
        }

        return true;
    }

    public boolean save(Notice notice, Member meber) {
        notice.setWriter(meber.getEmail());

        int cnt = mapper.save(notice);

        return  cnt==1;

    }

    public List<Notice> selectAll() {
        List<Notice> list = mapper.selectAll();

        return list;
    }

    public Notice selectByNo(Integer no) {

        return mapper.selectByNo(no);
    }

    public boolean update(Notice notice, Member login) {

        return mapper.update(notice) ==1;
    }

    public boolean remove(Integer no) {

        return mapper.deleteByNo(no)==1;
    }
}
