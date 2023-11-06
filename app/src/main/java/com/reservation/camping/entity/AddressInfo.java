package com.reservation.camping.entity;

import lombok.Data;

@Data
public class AddressInfo {
    private String address_name;        // 전체 도로명 주소
    private String region_1depth_name;  // 지역명1(ex : 시, 도...)
    private String region_2depth_name;  // 지역명2(ex : 군...)
}
