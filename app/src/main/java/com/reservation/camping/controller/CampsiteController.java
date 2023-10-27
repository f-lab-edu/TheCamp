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
        List<String> gangwondo = new ArrayList<>();

        gangwondo.add("원주 그린애캠핑장");
        gangwondo.add("원주 산노리캠핑장");
        gangwondo.add("한스캠핑장");

        campsiteMap.put("캠핑장", gangwondo);

        return campsiteMap;
    }
}
