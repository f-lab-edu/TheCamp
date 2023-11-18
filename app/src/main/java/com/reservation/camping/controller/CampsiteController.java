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

    private static Long reservationId = 0L;
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
    public CampsiteReservationDto addCampsite(@RequestBody CampsiteReservationDto campsiteReservationDto) {
        CampsiteReservationDto campsiteReservation = CampsiteReservationDto.builder()
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();

        return campsiteReservation;
    }

    @PutMapping("/campsiteUpdate/{reservationId}")
    public Map<Long, CampsiteReservationDto> updateCampsite(@PathVariable("reservationId") Long reservationId) {
        CampsiteReservationDto campsiteReservationDto = testDb.get(reservationId);
        campsiteReservationDto.setCampsiteName("영월 법흥계곡얼음골펜션 update");
        testDb.put(reservationId, campsiteReservationDto);
        return testDb;
    }

    @DeleteMapping("/campsiteDelete/{reservationId}")
    public Map<Long, CampsiteReservationDto> deleteCampsite(@PathVariable("reservationId") Long reservationId) {
        testDb.remove(reservationId);
        return testDb;
    }
}
