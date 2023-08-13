package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.model.repo.ApiCallRecordRepo;
import com.navi.mynewsservice.model.schema.ApiCallRecord;
import com.navi.mynewsservice.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class NewsController {
    @Autowired
    private NewsServiceImpl newsService;
    @Autowired
    private ApiCallRecordRepo apiCallRecordRepo;

    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable String id,
                                         @RequestParam(required = false) String count,
                                         @RequestParam(required = false) String from,
                                         @RequestParam(required = false) String to) {
        try{
             return new ResponseEntity<>(newsService.getNewsById(id, count,from,to),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/sources/{id}")
    public ResponseEntity<?> getSources(@PathVariable String id) {

            long startTime = System.currentTimeMillis();
            List<String> response = newsService.getSources(id);
            long endTime = System.currentTimeMillis();
            long tt = endTime - startTime;
            apiCallRecordRepo.save(new ApiCallRecord("/sources/", "GET + source ", "response.toString()", tt));
            return new ResponseEntity<>(response, HttpStatus.OK);

    }

}

