package com.reservation.camping.controller;

import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.dto.ErrorResponseDto;
import com.reservation.camping.entity.CampsiteInfo;
import com.reservation.camping.service.CampsiteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class CampsiteController {

    private final CampsiteService campsiteService;

    @GetMapping("/campsiteList")
    public List<CampsiteInfo> getCampsiteList() {
        return campsiteService.getCampsiteList();
    }

    @PostMapping("/campsiteAdd")
    public CampsiteInfo addCampsite(@RequestBody CampsiteReservationDto campsiteReservationDto) {
        return campsiteService.addCampsite(campsiteReservationDto);
    }

    @PutMapping("/campsiteUpdate/{reservationId}")
    public ResponseEntity<Object> updateCampsite(@PathVariable("reservationId") Long reservationId, @RequestBody CampsiteReservationDto campsiteReservationDto) {
        try {
            CampsiteInfo campsiteInfo = campsiteService.updateCampsite(reservationId, campsiteReservationDto);
            return ResponseEntity.ok(campsiteInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto("Invalid input"));
        }
    }

    @DeleteMapping("/campsiteDelete/{reservationId}")
    public ResponseEntity<Object> deleteCampsite(@PathVariable Long reservationId) {
        try {
            Map<Long, CampsiteInfo> result = campsiteService.deleteCampsite(reservationId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto("Invalid input"));
        }
    }
}
