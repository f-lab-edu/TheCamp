package com.reservation.camping.service;

import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.entity.CampsiteInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampsiteServiceTest {

    @Mock
    private Map<Long, CampsiteReservationDto> testDb;

    @InjectMocks // Mock을 주입할 객체를 지정
    private CampsiteService campsiteService;

    @Test
    void getCampsiteList() {
        // When
        List<CampsiteInfo> campsiteList = campsiteService.getCampsiteList();
    }

    @Test
    void addCampsite() {
        // Given
        CampsiteReservationDto campsiteReservationDto = createCampsiteReservationDto();
        when(testDb.put(anyLong(), any())).thenReturn(campsiteReservationDto);

        // When
        CampsiteInfo campsiteInfo = campsiteService.addCampsite(campsiteReservationDto);

        // Then
        assertEquals(campsiteReservationDto.getCampsiteName(), campsiteInfo.getReservationInfo().getCampsiteName());
        verify(testDb, times(1)).put(any(), any());
    }

    @Test
    void updateCampsite() {
        // Given
        CampsiteReservationDto createCampsiteReservationDto = createCampsiteReservationDto();
        CampsiteReservationDto updateCampsiteReservationDto = updateCampsiteReservationDto();
        when(testDb.get(anyLong())).thenReturn(createCampsiteReservationDto);

        // When
        CampsiteInfo updatedCampsiteInfo = campsiteService.updateCampsite(createCampsiteReservationDto.getReservationId(), updateCampsiteReservationDto);

        // Then
        assertNotNull(updatedCampsiteInfo);
        assertEquals(updateCampsiteReservationDto.getCampsiteName(), updatedCampsiteInfo.getReservationInfo().getCampsiteName());
        verify(testDb, times(1)).put(any(), any());
    }

    @Test
    void deleteCampsite() {
        // Given
        CampsiteReservationDto createCampsiteReservationDto = createCampsiteReservationDto();
        when(testDb.get(anyLong())).thenReturn(createCampsiteReservationDto);
        when(testDb.remove(anyLong())).thenReturn(null);

        // When
        Map<Long, CampsiteInfo> result = campsiteService.deleteCampsite(createCampsiteReservationDto.getReservationId());

        // Then
        assertNotNull(result);
        verify(testDb, times(1)).remove(createCampsiteReservationDto.getReservationId());
    }

    private CampsiteReservationDto createCampsiteReservationDto() {
        return CampsiteReservationDto.builder()
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();
    }

    private CampsiteReservationDto updateCampsiteReservationDto() {
        return CampsiteReservationDto.builder()
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션 UPDATE TEST~~")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();
    }
}
