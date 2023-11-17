package com.reservation.camping.dto;

import lombok.Data;

@Data
public class CampsiteReservationDto {
    private long reservationId;       // 예약ID
    private String addressName;         // 전체 도로명 주소
    private String region1DepthName;    // 지역명1(ex : 시, 도...)
    private String region2DepthName;    // 지역명2(ex : 군...)
    private String campsiteName;        // 캠핑장 이름
    private String priceRange;          // 가격 범위
    private String telephone;           // 전화번호
    private String description;         // 설명
}

