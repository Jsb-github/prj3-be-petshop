package com.example.prj3bepetshop.service;

import com.example.prj3bepetshop.domain.Event;
import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    @Value("${aws.bucketName}")
    private String bucket;

    @Value("${image.file.prefix}")
    private String urlPrefix;

    private final S3Client s3;

    private final EventMapper mapper;


    public boolean hasAccess(Member login) {
        

        
        boolean admin =login.getAuth().stream()
                .map(n-> n.getManager())
                .allMatch(a->a.equals("admin"));
        
        if (!admin){
            return false;
        }
        return  admin;
    }

    public boolean eventValidate(Event event, MultipartFile[] file) {

        if (event.getTitle() == null || event.getTitle().isBlank()){
            return false;
        }

        if (file == null){
            return false;
        }

        return true;
    }

    public boolean save(Event event, MultipartFile[] file, Member login)throws Exception {

        event.setWriter(login.getEmail());
        int cnt =0;
        if (file != null){
            for (int i=0; i<file.length; i++){
                event.setFileName(file[i].getOriginalFilename());
                cnt = mapper.save(event);
                upload(event.getNo(),file[i]);
            }
        }
        return cnt == 1;
    }


    public List<Event> selectAll() {
        List<Event> list = mapper.selectAll();

        for (Event event : list){
            String url = urlPrefix + "prj3/event/"+ event.getNo()+"/"+ event.getFileName();
            event.setUrl(url);
        }

        return list;
    }

    public Event selectByNo(Integer no) {
        Event event = mapper.selectByEvent(no);

        String url = urlPrefix + "prj3/event/"+ event.getNo()+"/"+ event.getFileName();
        event.setUrl(url);

        return event;
    }

    public boolean remove(Integer no) {
        deleteFile(no);

        return mapper.deleteByNo(no);
    }


    private void deleteFile(Integer no){
    String fileName = mapper.selectByFileName(no);

        String key =  "prj3/event/"+ no +"/"+ fileName;

        DeleteObjectRequest objectRequest =
                DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build();

        s3.deleteObject(objectRequest);

    }


    public boolean edit(Event event, MultipartFile[] file) throws Exception {
        if (file != null){
            for (int i=0; i<file.length; i++){
                event.setFileName(file[i].getOriginalFilename());

                upload(event.getNo(),file[i]);
            }
        }
        return mapper.update(event) == 1;
    }

    private void upload(Integer no, MultipartFile file) throws IOException {
        String key = "prj3/event/" + no + "/" + file.getOriginalFilename() ;

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }
}
