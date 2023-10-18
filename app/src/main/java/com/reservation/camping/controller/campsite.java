package com.reservation.camping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class campsite {

    @GetMapping("/campsite/name")
    public Map<String, List<String>> test() {
        Map<String, List<String>> testMap = new HashMap<>();
        List<String> gangwondo = new ArrayList<>();

        gangwondo.add("원주 그린애캠핑장");
        gangwondo.add("원주 산노리캠핑장");
        gangwondo.add("한스캠핑장");

        testMap.put("원주", gangwondo);

        return testMap;
    }
}
