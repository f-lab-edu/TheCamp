package com.reservation.camping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class CampsiteController {

    @GetMapping("/campsiteList")
    public Map<String, List<String>> getCampsiteName() {
        Map<String, List<String>> campsiteMap = new HashMap<>();
        List<String> campsiteList = new ArrayList<>();

        campsiteList.add("원주 그린애캠핑장");
        campsiteList.add("원주 산노리캠핑장");
        campsiteList.add("한스캠핑장");

        campsiteMap.put("campsiteList", campsiteList);

        return campsiteMap;
    }

    //    /booking/campsite/3598
}
