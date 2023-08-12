package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.model.repo.ApiCallRecordRepo;
import com.navi.mynewsservice.Contract.response.ApiCallRecordDTO;
import com.navi.mynewsservice.Contract.response.ApiCost;
import com.navi.mynewsservice.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class InfoController {

    @Autowired
    private NewsServiceImpl newsService;
    @Autowired
    private ApiCallRecordRepo apiCallRecordRepo;

    @GetMapping("/report")
    public ResponseEntity<List<ApiCallRecordDTO>> report() {
        List<Object[]> queryResult = apiCallRecordRepo.findDistinctRequestsCountAndAvgTime();
        List<ApiCallRecordDTO> response = queryResult.stream()
                .map(result -> new ApiCallRecordDTO(
                        (String) result[0],             // request
                        Optional.ofNullable((Long) result[1]),  // callCount
                        Optional.ofNullable((Double) result[2]))) // avgTimeTaken
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/bill")

    public List<ApiCost> getCostApiCalls(){
        List<Object[]> results = apiCallRecordRepo.findDistinctRequestTypesWithCallCountAndCost();
        List<ApiCost> apiDistinctCallsCost = new ArrayList<>();
        for (Object[] result : results) {

            String endpoint = (String) result[0];
            long count = (long) result[1];

            if(endpoint.equals("/add-user")) {
                count = count * 5;
            } else if (endpoint.equals("/top-headlines")) {
                count = count * 8;
            } else if (endpoint.equals("/top-headlines/count")) {
                count = count * 11;
            }else if(endpoint.equals("/top-headlines/from/to")){
                count = count * 15;
            }else if(endpoint.equals("/top-headlines/sources")){
                count = count * 20;
            }else if(endpoint.equals("/top-headlines/count")){
                count = count * 10;
            }else if(endpoint.equals("/top-headlines/from/to/count/source")){
                count = count * 7;
            }
            ApiCost newApiDistinctCallCost = new ApiCost(endpoint, count);
            apiDistinctCallsCost.add(newApiDistinctCallCost);

        }
        return apiDistinctCallsCost;
    }
}
