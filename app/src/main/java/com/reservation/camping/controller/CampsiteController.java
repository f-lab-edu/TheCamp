package com.reservation.camping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.camping.entity.AddressInfo;
import com.reservation.camping.entity.CampsiteInfo;
import com.reservation.camping.entity.ReservationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class CampsiteController {

    @GetMapping("/campsiteList")
    public CampsiteInfo getCampsiteList() {
        AddressInfo addressInfo = new AddressInfo();
        CampsiteInfo campsiteInfo = new CampsiteInfo();
        ReservationInfo reservationInfo = new ReservationInfo();

        addressInfo.setAddress_name("강원도 영월군 무릉도원면 무릉법흥로 1078-9");
        addressInfo.setRegion_1depth_name("강원도");
        addressInfo.setRegion_2depth_name("영월군");

        reservationInfo.setName("영월 법흥계곡얼음골펜션");
        reservationInfo.setPriceRange("40000-40000");
        reservationInfo.setTelephone("033-374-8095");
        reservationInfo.setDescription("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다");

        campsiteInfo.setReservationId("1");
        campsiteInfo.setAddressInfo(addressInfo);
        campsiteInfo.setReservationInfo(reservationInfo);

        return campsiteInfo;
    }
}
