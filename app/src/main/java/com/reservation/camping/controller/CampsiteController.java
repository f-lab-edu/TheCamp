package com.reservation.camping.controller;

import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.entity.AddressInfo;
import com.reservation.camping.entity.CampsiteInfo;
import com.reservation.camping.entity.ReservationInfo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/booking")
public class CampsiteController {
    private static Map<Long, CampsiteReservationDto> testDb = new HashMap<>();    // 임시 DB

    @GetMapping("/campsiteList")
    public CampsiteInfo getCampsiteList() {
        AddressInfo addressInfo = new AddressInfo();
        CampsiteInfo campsiteInfo = new CampsiteInfo();
        ReservationInfo reservationInfo = new ReservationInfo();

        addressInfo.setAddressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9");
        addressInfo.setRegion1DepthName("강원도");
        addressInfo.setRegion2DepthName("영월군");

        reservationInfo.setName("영월 법흥계곡얼음골펜션");
        reservationInfo.setPriceRange("40000-40000");
        reservationInfo.setTelephone("033-374-8095");
        reservationInfo.setDescription("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다");

        campsiteInfo.setReservationId(1);
        campsiteInfo.setAddressInfo(addressInfo);
        campsiteInfo.setReservationInfo(reservationInfo);

        return campsiteInfo;
    }

    @PostMapping("/campsiteAdd")
    public Map<String, Object> addCampsite() {
        Map<String, Object> response = new HashMap<>();

        CampsiteReservationDto campsiteReservation = CampsiteReservationDto.builder()
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();

        response.put("response", "캠핑장 등록 성공");
        response.put("campsite", campsiteReservation);

        return response;
    }

//    @PutMapping("/campsiteUpdate/{reservationId}")
    @PutMapping("/campsiteUpdate")
    public Map<String, Object> updateCampsite() {
        Map<String, Object> response = new HashMap<>();

        CampsiteReservationDto campsiteReservation = CampsiteReservationDto.builder()
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9 UPDATE")
                .region1DepthName("강원도 UPDATE")
                .region2DepthName("영월군 UPDATE")
                .campsiteName("영월 법흥계곡얼음골펜션 UPDATE")
                .priceRange("40000-40000 UPDATE")
                .telephone("033-1111-2222 UPDATE")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다 UPDATE")
                .build();

        response.put("response", "캠핑장 수정 성공");
        response.put("campsite", campsiteReservation);

        return response;
    }

//    @DeleteMapping("/campsiteDelete/{reservationId}")
    @DeleteMapping("/campsiteDelete")
    public Map<String, Object> deleteCampsite() {
        Map<String, Object> response = new HashMap<>();

        response.put("response", "캠핑장 삭제 성공");
        return response;
    }
}
