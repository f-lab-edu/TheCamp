package com.reservation.camping.controller;

import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.entity.CampsiteInfo;
import com.reservation.camping.service.CampsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class CampsiteController {

    private CampsiteService campsiteService;

    @Autowired
    public void setCampsiteService(CampsiteService campsiteService) {
        this.campsiteService = campsiteService;
    }

    @GetMapping("/campsiteList")
    public CampsiteInfo getCampsiteList() {
        return campsiteService.getCampsiteList();
    }

    @PostMapping("/campsiteAdd")
    public Map<Long, CampsiteReservationDto> addCampsite(@RequestBody CampsiteReservationDto campsiteReservationDto) {
        return campsiteService.addCampsite(campsiteReservationDto);
    }

    @PutMapping("/campsiteUpdate/{reservationId}")
    public Map<Long, CampsiteReservationDto> updateCampsite(@PathVariable("reservationId") Long reservationId, @RequestBody CampsiteReservationDto campsiteReservationDto) {
        return campsiteService.updateCampsite(reservationId, campsiteReservationDto);
    }

    @DeleteMapping("/campsiteDelete/{reservationId}")
    public Map<Long, CampsiteReservationDto> deleteCampsite(@PathVariable("reservationId") Long reservationId) {
        return campsiteService.deleteCampsite(reservationId);
    }
}
