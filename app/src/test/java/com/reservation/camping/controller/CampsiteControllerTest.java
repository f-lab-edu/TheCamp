package com.reservation.camping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.camping.dto.CampsiteReservationDto;
import com.reservation.camping.entity.CampsiteInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CampsiteController.class)
class CampsiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getCampsiteList() throws Exception {
        // 기본 응답 테스트
        mockMvc.perform(get("/booking/campsiteList"))
                .andExpect(status().isOk())
                .andDo(print());

        // 변환 테스트
        MockHttpServletResponse response = mockMvc.perform(get("/booking/campsiteList"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        CampsiteInfo campsiteInfo = objectMapper.readValue(response.getContentAsString(), CampsiteInfo.class);

        Long reservationId = campsiteInfo.getReservationId();

        assertEquals(1, reservationId);
    }

    @Test
    void addCampsite() throws Exception {
        // given
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

        // when
        ResultActions resultActions = mockMvc.perform(post("/booking/campsiteAdd")
                .contentType(MediaType.APPLICATION_JSON) // APPLICATION_JSON_UTF8를 명시하지 않아도 브라우저에서 잡아주기에 APPLICATION_JSON으로 충분하다.
                .content(content));

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
