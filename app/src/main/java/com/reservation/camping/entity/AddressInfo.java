package com.reservation.camping.entity;

import lombok.Builder;
import lombok.Data;

@Data
public class AddressInfo {
    private String addressName;        // 전체 도로명 주소
    private String region1DepthName;  // 지역명1(ex : 시, 도...)
    private String region2DepthName;  // 지역명2(ex : 군...)
}
