package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.dao.RequestTypeCallCountDTO;
import com.navi.mynewsservice.dao.repo.ApiCallRecordRepo;
import com.navi.mynewsservice.dao.schema.ApiCallRecord;
import com.navi.mynewsservice.dao.schema.ApiCallRecordDTO;
import com.navi.mynewsservice.dao.schema.ApiCost;
import com.navi.mynewsservice.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class NewsController {


    @Autowired
    private NewsServiceImpl newsService;

    @Autowired
    private ApiCallRecordRepo apiCallRecordRepo;

    @PostMapping("/add-user")
    public ResponseEntity<?> addUserDetails(@RequestBody User user) {
        try {
            long startTime = System.currentTimeMillis();
            String response = newsService.addUserDetails(
                    user.getEmail(),
                    user.getCountry(),
                    user.getCategory(),
                    user.getSources());
            long endTime = System.currentTimeMillis();
            long tt = endTime - startTime;

            apiCallRecordRepo.save(new ApiCallRecord("/add-user", "POST", response, tt));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Long tt = 0L;
            apiCallRecordRepo.save(new ApiCallRecord("/add-user", "POST", e.getMessage(), tt));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



        @GetMapping("/news/{id}")
    public List<String> getNewsById(@PathVariable String id,
                                         @RequestParam(required = false) String count,
                                         @RequestParam(required = false) String from,
                                         @RequestParam(required = false) String to) {
        try{
            long startTime = System.currentTimeMillis();

            long endTime = System.currentTimeMillis();
            long tt = endTime - startTime;
            List<String> rr = newsService.getNewsById(id, count,from,to);
            apiCallRecordRepo.save(new ApiCallRecord("/news/" + id, "GET + id", "response", tt));
            return rr;
        }
        catch(Exception e){
            return new ArrayList<>();
        }
    }

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
//            apiCallRecordRepo.save(new ApiCallRecord("/news/" + id, "GET", response.toString(), tt));
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            Long tt = 0L;
//            apiCallRecordRepo.save(new ApiCallRecord("/news/" + id, "GET", e.getMessage(), tt));
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/sources/{id}")
    public ResponseEntity<?> getSources(@PathVariable String id) {
        try {
            long startTime = System.currentTimeMillis();
            List<String> response = newsService.getSources(id);

            long endTime = System.currentTimeMillis();
            long tt = endTime - startTime;

            apiCallRecordRepo.save(new ApiCallRecord("/sources/" + id, "GET + source ", response.toString(), tt));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Long tt = 0L;
            apiCallRecordRepo.save(new ApiCallRecord("/sources/" + id, "GET + source", e.getMessage(), tt));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/report")
    public ResponseEntity<List<RequestTypeCallCountDTO>> report() {
        List<RequestTypeCallCountDTO> queryResult = apiCallRecordRepo.findDistinctRequestTypesWithCallCountAndAvgTime();

        List<RequestTypeCallCountDTO> response = queryResult.stream()
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/bill")
    public ResponseEntity<List<ApiCost>> cost() {
        List<ApiCost> queryResult = apiCallRecordRepo.findDistinctRequestTypesWithCallCountAndAvgTimeAndCost();

        List<ApiCost> response = queryResult.stream()
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

















//    @Autowired
//    private NewsServiceImpl newsService;
//
//    @PostMapping ("/add-user")
//    public ResponseEntity<?> addUserDetails(@RequestBody User user) {
//        try{
//            return new ResponseEntity<>(
//                    newsService.addUserDetails(
//                            user.getEmail(),
//                            user.getCountry(),
//                            user.getCategory(),
//                            user.getSources()), HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(
//                    e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @GetMapping("/news/{id}")
//    public ResponseEntity<?> getNewsById(@PathVariable String id,
//                                         @RequestParam(required = false) String count,
//                                         @RequestParam(required = false) String from,
//                                         @RequestParam(required = false) String to) {
//        try{
//            return new ResponseEntity<>(
//                    newsService.getNewsById(id, count,from,to),
//                    HttpStatus.OK);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/sources/{id}")
//    public ResponseEntity<?> getNewsById(@PathVariable String id) {
//        try{
//            return new ResponseEntity<>(newsService.getSources(id),
//                    HttpStatus.OK);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        }
//    }
//
//}
