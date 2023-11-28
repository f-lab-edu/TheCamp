package com.reservation.camping.service;

import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.entity.CampsiteInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface CampsiteService {

    public CampsiteInfo getCampsiteList();

    public Map<Long, CampsiteReservationDto> addCampsite(@RequestBody CampsiteReservationDto campsiteReservationDto);

    public Map<Long, CampsiteReservationDto> updateCampsite(@PathVariable("reservationId") Long reservationId, @RequestBody CampsiteReservationDto campsiteReservationDto);

    public Map<Long, CampsiteReservationDto> deleteCampsite(@PathVariable("reservationId") Long reservationId);


}
