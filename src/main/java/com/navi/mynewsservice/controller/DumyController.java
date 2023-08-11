//package com.navi.mynewsservice.controller;
//import com.navi.mynewsservice.Contract.request.User;
//import com.navi.mynewsservice.dao.repo.ApiCallRecordRepo;
//import com.navi.mynewsservice.dao.schema.ApiCallRecord;
//import com.navi.mynewsservice.service.impl.NewsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//public class DumyController {
//
//    @Autowired
//    private NewsServiceImpl newsService;
//
//    @Autowired
//    private ApiCallRecordRepo apiCallRecordRepo;
//
//    @PostMapping("/add-user")
//    public ResponseEntity<?> addUserDetails(@RequestBody User user) {
//        try {
//            long startTime = System.currentTimeMillis();
//            String response = newsService.addUserDetails(
//                    user.getEmail(),
//                    user.getCountry(),
//                    user.getCategory(),
//                    user.getSources());
//            long endTime = System.currentTimeMillis();
//            long tt = endTime - startTime;
//
//            apiCallRecordRepo.save(new ApiCallRecord("/add-user", "POST", response,tt));
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            Long tt = 0L;
//            apiCallRecordRepo.save(new ApiCallRecord("/add-user", "POST", e.getMessage(),tt));
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @GetMapping("/news/{id}")
//    public ResponseEntity<?> getNewsById(@PathVariable String id,
//                                         @RequestParam(required = false) String count,
//                                         @RequestParam(required = false) String from,
//                                         @RequestParam(required = false) String to) {
//        try {
//            long startTime = System.currentTimeMillis();
//            List<String> response = newsService.getNewsById(id, count, from, to);
//            long endTime = System.currentTimeMillis();
//            long tt = endTime - startTime;
//
//            apiCallRecordRepo.save(new ApiCallRecord("/news/" + id, "GET",  response.toString(),tt));
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            Long tt = 0L;
//            apiCallRecordRepo.save(new ApiCallRecord("/news/" + id, "GET", e.getMessage(),tt));
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/sources/{id}")
//    public ResponseEntity<?> getSources(@PathVariable String id) {
//        try {
//            long startTime = System.currentTimeMillis();
//            List<String> response = newsService.getSources(id);
//
//            long endTime = System.currentTimeMillis();
//            long tt = endTime - startTime;
//
//            apiCallRecordRepo.save(new ApiCallRecord("/sources/" + id, "GET", response.toString(),tt));
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            Long tt = 0L;
//            apiCallRecordRepo.save(new ApiCallRecord("/sources/" + id, "GET", e.getMessage(),tt));
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
