package com.reservation.camping.controller;

import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.entity.AddressInfo;
import com.reservation.camping.entity.CampsiteInfo;
import com.reservation.camping.entity.ReservationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@RequiredArgsConstructor
@RestController
@RequestMapping("/booking")
public class CampsiteController {
    private static Long reservationId = 0L;
    private final Map<Long, CampsiteReservationDto> testDb;    // 임시 DB

    // 어차피 Test 때만 tetDb를 사용하면 굳이 아래 코드는 필요 없지 않나?..  -> Test Code에서 필요하기 때문에 필요하다!
    public CampsiteController() {
        this(new HashMap<>());
    }
    @Autowired
    public CampsiteController(Map<Long, CampsiteReservationDto> testDb) {
        this.testDb = testDb;
    }

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
    public Map<Long, CampsiteReservationDto> addCampsite(@RequestBody CampsiteReservationDto campsiteReservationDto) {
        if (testDb.isEmpty()) {
            campsiteReservationDto.setReservationId(reservationId);
            testDb.put(campsiteReservationDto.getReservationId(), campsiteReservationDto);
        } else {
            campsiteReservationDto.setReservationId(++reservationId);
            testDb.put(campsiteReservationDto.getReservationId(), campsiteReservationDto);
        }
        return testDb;
    }

    @PutMapping("/campsiteUpdate/{reservationId}")
    public Map<Long, CampsiteReservationDto> updateCampsite(@PathVariable("reservationId") Long reservationId, @RequestBody CampsiteReservationDto campsiteReservationDto) {
        if(null == testDb.get(reservationId)) {
            throw new NullPointerException();
        } else {
            testDb.put(reservationId, campsiteReservationDto);
        }
        return testDb;
    }

    @DeleteMapping("/campsiteDelete/{reservationId}")
    public Map<Long, CampsiteReservationDto> deleteCampsite(@PathVariable("reservationId") Long reservationId) {
        if(null == testDb.get(reservationId)) {
            throw new NullPointerException();
        } else {
            testDb.remove(reservationId);
        }
        return testDb;
    }
}
