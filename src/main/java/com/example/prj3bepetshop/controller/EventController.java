package com.example.prj3bepetshop.controller;

import com.example.prj3bepetshop.domain.Event;
import com.example.prj3bepetshop.domain.Member;
import com.example.prj3bepetshop.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {

    private final EventService service;



    @GetMapping("")
    public List<Event> allEventList(){
        return service.selectAll();
    }

    @GetMapping("{no}")
    public Event getEvent(@PathVariable Integer no){
        return service.selectByNo(no);
    }


    @PostMapping("add")
    public ResponseEntity add(
            Event event,
            @RequestParam(value = "file[]",required = false) MultipartFile[] file,
            @SessionAttribute(value = "login",required = false)Member login
            )throws Exception{

        if (login==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

      if (!service.hasAccess(login)){
          return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }

      if (!service.eventValidate(event,file)){
          return ResponseEntity.badRequest().build();
      }

      if (service.save(event,file,login)){
          return ResponseEntity.ok().build();
      }else {
          return ResponseEntity.internalServerError().build();
      }
    }

    @DeleteMapping("remove/{no}")
    public ResponseEntity remove(
            @PathVariable Integer no,
            @SessionAttribute(value = "login",required = false) Member login
    ){

        if (login==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!service.hasAccess(login)){
            return ResponseEntity.badRequest().build();
        }

        if (service.remove(no)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }

    }

    @PutMapping("edit")
    public ResponseEntity edit(
             Event event,
            @RequestParam(value = "file[]",required = false) MultipartFile[] file,
            @SessionAttribute(value = "login",required = false)Member login
    ) throws  Exception{

        if(login == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!service.hasAccess(login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!service.eventValidate(event,file)){
            return ResponseEntity.badRequest().build();
        }

        if (service.edit(event,file)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }

    }


}
