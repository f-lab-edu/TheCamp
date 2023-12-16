package com.reservation.camping.service;

import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.entity.AddressInfo;
import com.reservation.camping.entity.CampsiteInfo;
import com.reservation.camping.entity.ReservationInfo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class CampsiteService {

    private Long reservationId = 0L;

    private final Map<Long, CampsiteInfo> testDb;    // 임시 DB

    @Autowired
    public CampsiteService() {
        this(new HashMap<>());
    }

    public List<CampsiteInfo> getCampsiteList() {
        return testDb.values().stream().toList();
    }

    public CampsiteInfo addCampsite(CampsiteReservationDto campsiteReservationDto) {
        Long reservationId = this.reservationId++;

        CampsiteInfo campsiteInfo = new CampsiteInfo();
        AddressInfo addressInfo = new AddressInfo();
        ReservationInfo reservationInfo = new ReservationInfo();

        addressInfo.setAddressName(campsiteReservationDto.getAddressName());
        addressInfo.setRegion1DepthName(campsiteReservationDto.getRegion1DepthName());
        addressInfo.setRegion2DepthName(campsiteReservationDto.getRegion2DepthName());

        reservationInfo.setDescription(campsiteReservationDto.getDescription());
        reservationInfo.setTelephone(campsiteReservationDto.getTelephone());
        reservationInfo.setPriceRange(campsiteReservationDto.getPriceRange());
        reservationInfo.setCampsiteName(campsiteReservationDto.getCampsiteName());

        campsiteInfo.setReservationId(reservationId);
        campsiteInfo.setAddressInfo(addressInfo);
        campsiteInfo.setReservationInfo(reservationInfo);

        testDb.put(campsiteReservationDto.getReservationId(), campsiteInfo);

        return campsiteInfo;
    }

//    public CampsiteInfo updateCampsite(Long reservationId, CampsiteReservationDto campsiteReservationDto) {
//        if(null == testDb.get(reservationId)) {
//            throw new IllegalArgumentException("Invalid Reservation ID");
//        } else {
//            testDb.put(reservationId, campsiteReservationDto);
//        }
//        return testDb;
//    }

//    public Map<Long, CampsiteReservationDto> deleteCampsite(Long reservationId) {
//        if(null == testDb.get(reservationId)) {
//            throw new IllegalArgumentException("Invalid Reservation ID");
//        } else {
//            testDb.remove(reservationId);
//        }
//        return testDb;
//    }

//    public Map<Long, CampsiteReservationDto> updateCampsite(Long reservationId, CampsiteReservationDto campsiteReservationDto) {
//        if(null == testDb.get(reservationId)) {
//            throw new IllegalArgumentException("Invalid Reservation ID");
//        } else {
//            testDb.put(reservationId, campsiteReservationDto);
//        }
//        return testDb;
//    }
//
//    public Map<Long, CampsiteReservationDto> deleteCampsite(Long reservationId) {
//        if(null == testDb.get(reservationId)) {
//            throw new IllegalArgumentException("Invalid Reservation ID");
//        } else {
//            testDb.remove(reservationId);
//        }
//        return testDb;
//    }

}
