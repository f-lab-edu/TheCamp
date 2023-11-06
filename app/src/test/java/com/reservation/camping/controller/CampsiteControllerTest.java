package com.reservation.camping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.camping.entity.CampsiteInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        String reservationId = campsiteInfo.getReservationId();

        assertEquals("1", reservationId);
    }
}
