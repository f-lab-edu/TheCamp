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
    void addCampsite() {
        // Given
        CampsiteReservationDto campsiteReservationDto = createCampsiteReservationDto();

        // When
        when(testDb.put(anyLong(), any())).thenReturn(campsiteReservationDto);

        CampsiteInfo campsiteInfo = campsiteService.addCampsite(campsiteReservationDto);

        assertEquals(campsiteReservationDto.getCampsiteName(), campsiteInfo.getReservationInfo().getCampsiteName());

        verify(testDb, times(1)).put(any(), any());
    }

    @Test
    void updateCampsite() { // 성공
        // Given
        CampsiteReservationDto createCampsiteReservationDto = createCampsiteReservationDto();
        CampsiteReservationDto updateCampsiteReservationDto = updateCampsiteReservationDto();

        when(testDb.get(anyLong())).thenReturn(createCampsiteReservationDto);

        // When
        CampsiteInfo updatedCampsiteInfo = campsiteService.updateCampsite(createCampsiteReservationDto.getReservationId(), updateCampsiteReservationDto);

        System.out.println("test :" + updatedCampsiteInfo.getReservationInfo().getCampsiteName() + updateCampsiteReservationDto.getCampsiteName());

        assertNotNull(updatedCampsiteInfo);
        assertEquals(updateCampsiteReservationDto.getCampsiteName(), updatedCampsiteInfo.getReservationInfo().getCampsiteName());
        verify(testDb, times(1)).put(any(), any());
    }

//    @Test
//    void updateCampsite() { // 해당 방법은 실패
//        // Given
//        CampsiteReservationDto createCampsiteReservationDto = createCampsiteReservationDto();
//        CampsiteReservationDto updateCampsiteReservationDto = updateCampsiteReservationDto();
//
//        // When
//        when(testDb.put(anyLong(), any())).thenReturn(createCampsiteReservationDto);
//        when(testDb.get(anyLong())).thenReturn(createCampsiteReservationDto); // Mock 객체에 get 메서드에 대한 동작을 설정
//
//        CampsiteInfo createCampsiteInfo = campsiteService.addCampsite(createCampsiteReservationDto);
//        createCampsiteInfo.getReservationInfo().setCampsiteName("영월 법흥계곡얼음골펜션 UPDATE TEST~~");
//
//        // 테스트 코드에서 Mock 객체에 예상되는 동작을 설정
//        when(campsiteService.updateCampsite(anyLong(), any())).thenReturn(createCampsiteInfo);
//
//    }

//    @Test
//    void updateCampsite() {
//        // Given
//        CampsiteReservationDto createCampsiteReservationDto = createCampsiteReservationDto();
//        CampsiteReservationDto updateCampsiteReservationDto = updateCampsiteReservationDto();
//
//        // When
//        when(testDb.put(anyLong(), any())).thenReturn(createCampsiteReservationDto);
//
//        CampsiteInfo createCampsiteInfo = campsiteService.addCampsite(createCampsiteReservationDto);
//        createCampsiteInfo.getReservationInfo().setCampsiteName("영월 법흥계곡얼음골펜션 UPDATE TEST~~");
//
//        // 테스트 코드에서 null을 전달
//        when(campsiteService.updateCampsite(anyLong(), updateCampsiteReservationDto)).thenReturn(createCampsiteInfo);
//    }

    @Test
    void updateCampsite1() {
        // Given
        CampsiteReservationDto createCampsiteReservationDto = createCampsiteReservationDto();
        CampsiteReservationDto updateCampsiteReservationDto = updateCampsiteReservationDto();

        // When
        when(testDb.get(anyLong())).thenReturn(createCampsiteReservationDto);

        CampsiteInfo createCampsiteInfo = campsiteService.addCampsite(createCampsiteReservationDto);
        createCampsiteInfo.getReservationInfo().setCampsiteName("영월 법흥계곡얼음골펜션 UPDATE TEST~~");

        // 테스트 코드에서 null을 전달
        when(campsiteService.updateCampsite(anyLong(), updateCampsiteReservationDto)).thenReturn(createCampsiteInfo);

        assertEquals(createCampsiteInfo.getReservationInfo().getCampsiteName(), updateCampsiteReservationDto.getCampsiteName());
        verify(testDb, times(1)).put(any(), any());
    }

    private CampsiteReservationDto createCampsiteReservationDto() {
        return CampsiteReservationDto.builder() // add dto
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
        return CampsiteReservationDto.builder() // add dto
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
