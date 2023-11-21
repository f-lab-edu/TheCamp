package com.reservation.camping.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.camping.entity.CampsiteInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CampsiteController.class)
class CampsiteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

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

        MvcResult result = mockMvc.perform(post("/booking/campsiteAdd"))
                .andExpect(status().isOk())
                .andReturn();
        Map<String, Object> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<HashMap<String, Object>>() {});

        assertEquals("캠핑장 등록 성공", response.get("response"));
        assertNotNull(response.get("campsite"));
    }

    @Test
    void updateCampsite() throws Exception {

        MvcResult result = mockMvc.perform(put("/booking/campsiteUpdate"))
                .andExpect(status().isOk())
                .andReturn();
        Map<String, Object> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<HashMap<String, Object>>() {});

        assertEquals("캠핑장 수정 성공", response.get("response"));
        assertNotNull(response.get("campsite"));
    }

    @Test
    void deleteCampsite() throws Exception {

        MvcResult result = mockMvc.perform(delete("/booking/campsiteDelete"))
                .andExpect(status().isOk())
                .andReturn();
        Map<String, Object> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<HashMap<String, Object>>() {});

        assertEquals("캠핑장 삭제 성공", response.get("response"));
    }
}
