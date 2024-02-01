package com.example.prj3bepetshop.service;

import com.example.prj3bepetshop.domain.*;
import com.example.prj3bepetshop.mapper.OrderMapper;
import com.example.prj3bepetshop.mapper.ReviewFileMapper;
import com.example.prj3bepetshop.mapper.ReviewMapper;
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
public class reviewService {

    private final ReviewMapper mapper;

    private final ReviewFileMapper fileMapper;

    private final OrderMapper orderMapper;

    @Value("${aws.bucketName}")
    private String bucket;

    @Value("${image.file.prefix}")
    private String urlPrefix;

    private final S3Client s3;



    public boolean hasAceess(Integer no,Member login) {

        boolean admin =login.getAuth().stream()
                .map(n-> n.getManager())
                .anyMatch(a->a.equals("admin"));

        if (admin){
            return true;
        }


        Review review = mapper.selectByNo(no);

        return review.getWriter().equals(login.getEmail());
    }

        public boolean validFileNumber(List<Integer> removeFileIds, MultipartFile[] uploadFiles, Review review) {

        Integer fileCount = mapper.selectFileCount(review.getNo());

        if (removeFileIds != null){
            if(removeFileIds.size() == fileCount && uploadFiles==null){
                return false;
            }
        }

        return true;
    }

    public boolean reivewValidate(Review review, MultipartFile[] files) {

        if(review==null){
            return false;
        }

        if (review.getTitle() == null || review.getTitle().isBlank()){
            return false;
        }


        if(review.getInfo() == null || review.getInfo().isBlank()){
            return false;
        }



        return true;
    }

    public boolean save(Review review, MultipartFile[] files, Member login, Integer no) throws Exception {

      Order order =  orderMapper.selecbyNo(no);
        int cnt =0;

        if (order.getReviewStatus() == 0 ){
            review.setWriter(login.getEmail());

            cnt = mapper.save(review);

            if(files != null){
                for(int i=0; i< files.length; i++){
                    fileMapper.insert(review.getNo(), files[i].getOriginalFilename());
                    upload(review.getNo(),files[i]);
                }
            }
            orderMapper.updateStatus(no);
        }

        return cnt == 1;
    }

    private void upload(Integer reviewNo, MultipartFile file) throws IOException {
        String key = "prj3/review/" + reviewNo + "/" + file.getOriginalFilename();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }

    public List<Review> selectAll() {

        List<Review> list = mapper.selectAll();

        for (Review review : list) {
            List<ReviewFile> files = fileMapper.selectAllNamesById(review.getNo());

            for (ReviewFile file : files) {
                String url = urlPrefix + "prj3/review/" + review.getNo() + "/" + file.getFileName();
                file.setUrl(url);
            }
            review.setFiles(files);
        }



        return list;
    }

    public Review selectByNo(Integer no) {

        Review list = mapper.selectByNo(no);
        List<ReviewFile> files = fileMapper.selectNamesById(no);

        for (ReviewFile reviewFile : files) {
            String url = urlPrefix + "prj3/review/" + no + "/" + reviewFile.getFileName();
            reviewFile.setUrl(url);

        }

        list.setFiles(files);

        return list;

    }

    public boolean remove(Integer no) {

        deleteFile(no);

        return mapper.deleteByNo(no) ==1 ;
    }

    private void deleteFile(Integer no){
        List<ReviewFile> reviewFiles = fileMapper.selectNamesById(no);

        for (ReviewFile file : reviewFiles){
            String key = "prj3/review/" + no + "/" + file.getFileName();

            DeleteObjectRequest objectRequest =
                    DeleteObjectRequest
                            .builder()
                            .bucket(bucket)
                            .key(key)
                            .build();
            s3.deleteObject(objectRequest);
        }
        fileMapper.deleteFileByReviewNo(no);
    }

    public boolean update(Review review, Integer point, List<Integer> removeFileIds, MultipartFile[] files) throws IOException{

        if (removeFileIds != null) {
            for (Integer no : removeFileIds) {

                ReviewFile file = fileMapper.selectById(no);

                String key = "prj3/review/" + review.getNo() + "/" + file.getFileName();
                DeleteObjectRequest objectRequest = DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build();
                s3.deleteObject(objectRequest);

                fileMapper.deleteById(no);
            }
        }

        if(files != null){
            for(int i=0; i< files.length; i++){
                fileMapper.insert(review.getNo(), files[i].getOriginalFilename());
                upload(review.getNo(),files[i]);
            }
        }

        review.setPoint(point);


        return mapper.update(review) == 1;
    }

    public boolean writeHasAceess(Integer no, Member login) {
        Order order = orderMapper.selecbyNo(no);

        return order.getConsumer().equals(login.getEmail()) ;
    }
}
