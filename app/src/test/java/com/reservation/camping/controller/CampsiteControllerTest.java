package com.reservation.camping.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.entity.AddressInfo;
import com.reservation.camping.entity.CampsiteInfo;
import com.reservation.camping.entity.ReservationInfo;
import com.reservation.camping.service.CampsiteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CampsiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    private Map<Long, CampsiteReservationDto> testDb;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("GET 방식 테스트")
    void getCampsiteList() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/booking/campsiteList"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        CampsiteInfo campsiteInfo = objectMapper.readValue(response.getContentAsString(), CampsiteInfo.class);
        Long reservationId = campsiteInfo.getReservationId();

        assertEquals(1, reservationId);
    }

    @Test
    @DisplayName("campsite 등록 테스트")
    void addCampsite() throws Exception {
        CampsiteReservationDto campsiteReservation = CampsiteReservationDto.builder()
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();

        String content = new ObjectMapper().writeValueAsString(campsiteReservation);

        MvcResult result = mockMvc.perform(post("/booking/campsiteAdd")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());

        testDb = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<HashMap<Long, CampsiteReservationDto>>() {});

        assertEquals("영월 법흥계곡얼음골펜션", testDb.get(0L).getCampsiteName());
        assertNotNull(testDb.get(0L));
    }

    @Test
    @DisplayName("campsite 수정 성공 테스트")
    void updateSuccessCampsite() throws Exception {
        CampsiteReservationDto addCampsiteReservation = CampsiteReservationDto.builder() // add dto
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();

        CampsiteReservationDto updateCampsiteReservation = CampsiteReservationDto.builder() // update dto
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션 UPDATE")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();

        String addContent = new ObjectMapper().writeValueAsString(addCampsiteReservation);
        String updateContent = new ObjectMapper().writeValueAsString(updateCampsiteReservation);

        mockMvc.perform(post("/booking/campsiteAdd")
                        .content(addContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result = mockMvc.perform(put("/booking/campsiteUpdate/0")
                        .content(updateContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        testDb = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<HashMap<Long, CampsiteReservationDto>>() {});

        assertEquals("영월 법흥계곡얼음골펜션 UPDATE", testDb.get(0L).getCampsiteName());
        assertNotNull(testDb.get(0L));
    }

    @Test
    @DisplayName("campsite 수정 실패 테스트")
    void updateFailedCampsite() throws Exception {
        CampsiteReservationDto campsiteReservation = CampsiteReservationDto.builder()
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션 UPDATE")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();

        String updateContent = new ObjectMapper().writeValueAsString(campsiteReservation);

        MvcResult addResult = mockMvc.perform(post("/booking/campsiteAdd")
                        .content(updateContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result = mockMvc.perform(put("/booking/campsiteUpdate/1")
                        .content(updateContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        // IllegalArgumentException이 발생했는지 검증
        Exception resolvedException = result.getResolvedException();
        assertNotNull(resolvedException);
        assertEquals(IllegalArgumentException.class, resolvedException.getClass());
        assertEquals("Invalid Reservation ID", resolvedException.getMessage());
    }

    @Test
    @DisplayName("campsite 삭제 성공 테스트")
    void deleteSuccessCampsite() throws Exception {
        CampsiteReservationDto addCampsiteReservation = CampsiteReservationDto.builder() // add dto
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();

        String addContent = new ObjectMapper().writeValueAsString(addCampsiteReservation);

        MvcResult deleteResult = mockMvc.perform(delete("/booking/campsiteDelete/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        testDb = objectMapper.readValue(deleteResult.getResponse().getContentAsString(), new TypeReference<HashMap<Long, CampsiteReservationDto>>() {});

        assertNull(testDb.get(0L));
    }

    @Test
    @DisplayName("campsite 삭제 실패 테스트")
    void deleteFailedCampsite() throws Exception {
        MvcResult result = mockMvc.perform(delete("/booking/campsiteDelete/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }



//    private CampsiteInfo createCampsiteInfo() {
//        AddressInfo addressInfo = new AddressInfo();
//        CampsiteInfo campsiteInfo = new CampsiteInfo();
//        ReservationInfo reservationInfo = new ReservationInfo();
//
//        addressInfo.setAddressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9");
//        addressInfo.setRegion1DepthName("강원도");
//        addressInfo.setRegion2DepthName("영월군");
//
//        reservationInfo.setName("영월 법흥계곡얼음골펜션");
//        reservationInfo.setPriceRange("40000-40000");
//        reservationInfo.setTelephone("033-374-8095");
//        reservationInfo.setDescription("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다");
//
//        campsiteInfo.setReservationId(1);
//        campsiteInfo.setAddressInfo(addressInfo);
//        campsiteInfo.setReservationInfo(reservationInfo);
//
//        return campsiteInfo;
//    }
}
