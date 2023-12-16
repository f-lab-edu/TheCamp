package com.reservation.camping.entity;

import lombok.Data;

@Data
public class ReservationInfo {
    private String campsiteName;        // 캠핑장 이름
    private String priceRange;  // 가격 범위
    private String telephone;   // 캠핑장 전화번호
    private String description; // 설명
}
