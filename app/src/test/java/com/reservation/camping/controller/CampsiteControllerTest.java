package com.reservation.camping.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.entity.CampsiteInfo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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

    Long reservationId = 0L;

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

        List<CampsiteInfo> campsiteInfos = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<CampsiteInfo>>(){});

        Long reservationId = campsiteInfos.get(0).getReservationId();

        assertEquals(0, reservationId);
    }

    @Test
    @DisplayName("campsite 등록 테스트")
    void addCampsite() throws Exception {
        String content = null;

        CampsiteReservationDto campsiteReservation = CampsiteReservationDto.builder()
                .addressName("강원도 영월군 무릉도원면 무릉법흥로 1078-9")
                .region1DepthName("강원도")
                .region2DepthName("영월군")
                .campsiteName("영월 법흥계곡얼음골펜션")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();

        content = new ObjectMapper().writeValueAsString(campsiteReservation);

        MvcResult result = mockMvc.perform(post("/booking/campsiteAdd")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());

        CampsiteInfo campsiteInfo = objectMapper.readValue(result.getResponse().getContentAsString(), CampsiteInfo.class);

        String campsiteName = campsiteInfo.getReservationInfo().getCampsiteName();

        assertEquals("영월 법흥계곡얼음골펜션", campsiteName);
    }

    @Test
    @DisplayName("campsite 수정 성공 테스트")
    void updateSuccessCampsite() throws Exception {
        String addContent = null;
        String updateContent = null;

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
                .campsiteName("영월 법흥계곡얼음골펜션 UPDATE TEST~~")
                .priceRange("40000-40000")
                .telephone("033-1111-2222")
                .description("법흥계곡에 위치한 아름다운 추억이 함께하는곳 계곡과 숲을 만끽할수있는 얼음골펜션입니다")
                .build();

        addContent = new ObjectMapper().writeValueAsString(addCampsiteReservation);
        updateContent = new ObjectMapper().writeValueAsString(updateCampsiteReservation);

        MvcResult addResult = mockMvc.perform(post("/booking/campsiteAdd")
                        .content(addContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampsiteInfo addCampsiteInfo = objectMapper.readValue(addResult.getResponse().getContentAsString(), CampsiteInfo.class);

        reservationId = addCampsiteInfo.getReservationId(); // 예약ID

        MvcResult updateResult = mockMvc.perform(put("/booking/campsiteUpdate/" + reservationId)
                        .content(updateContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampsiteInfo updateCampsiteInfo = objectMapper.readValue(updateResult.getResponse().getContentAsString(), CampsiteInfo.class);

        String campsiteName = updateCampsiteInfo.getReservationInfo().getCampsiteName();

        assertEquals("영월 법흥계곡얼음골펜션 UPDATE TEST~~", campsiteName);
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

        mockMvc.perform(put("/booking/campsiteUpdate/1")
                        .content(updateContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // 400 Error 확인
                .andReturn();
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

        MvcResult addResult = mockMvc.perform(post("/booking/campsiteAdd")
                        .content(addContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampsiteInfo addCampsiteInfo = objectMapper.readValue(addResult.getResponse().getContentAsString(), CampsiteInfo.class);

        reservationId = addCampsiteInfo.getReservationId(); // 예약ID

        mockMvc.perform(delete("/booking/campsiteDelete/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("campsite 삭제 실패 테스트")
    void deleteFailedCampsite() throws Exception {
        mockMvc.perform(delete("/booking/campsiteDelete/123123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // 400 Error 확인
                .andReturn();
    }
}
