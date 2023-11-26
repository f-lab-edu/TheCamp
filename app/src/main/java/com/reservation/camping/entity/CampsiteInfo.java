package com.reservation.camping.entity;

import lombok.Data;

@Data
public class CampsiteInfo {
    private long reservationId;           // 예약ID
    private AddressInfo addressInfo;
    private ReservationInfo reservationInfo;
}
