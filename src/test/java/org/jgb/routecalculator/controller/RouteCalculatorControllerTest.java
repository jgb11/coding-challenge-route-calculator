package org.jgb.routecalculator.controller;

import org.jgb.routecalculator.model.RouteInfo;
import org.jgb.routecalculator.service.RouteCalculatorService;
import org.jgb.routecalculator.service.strategy.RouteStrategy;
import org.jgb.routecalculator.service.strategy.impl.ConnectionRouteStrategy;
import org.jgb.routecalculator.service.strategy.impl.TimeRouteStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author jgb
 * @since 14/02/19 10:49
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RouteCalculatorController.class, secure = false)
@TestPropertySource(locations = "classpath:bootstrap-tests.yml")
@ContextConfiguration(classes = {RouteCalculatorController.class, RefreshAutoConfiguration.class})
public class RouteCalculatorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RouteCalculatorService service;

    @MockBean
    private ConnectionRouteStrategy connectionRouteStrategy;

    @MockBean
    private TimeRouteStrategy timeRouteStrategy;

    @Before
    public void setUp() throws Exception {
        when(connectionRouteStrategy.compareRoutes(any(RouteInfo.class), any(RouteInfo.class))).thenCallRealMethod();
        when(timeRouteStrategy.compareRoutes(any(RouteInfo.class), any(RouteInfo.class))).thenCallRealMethod();
    }

    @Test
    public void testGetRouteInfoLessTimeReturnsOkResponse() throws Exception {
        when(service.calculateRoutes(any(), any(RouteStrategy.class)))
                .thenReturn(Collections.singletonList(new RouteInfo("City1", "City2")));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/calculator/City1/time").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetRouteInfoLessConnectionsReturnsOkResponse() throws Exception {
        when(service.calculateRoutes(any(), any(RouteStrategy.class)))
                .thenReturn(Collections.singletonList(new RouteInfo("City1", "City2")));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/calculator/City1/connections").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetRouteInfoReturnsOkResponse() throws Exception {
        when(service.calculateRoutes(any(), any(RouteStrategy.class)))
                .thenReturn(Collections.singletonList(new RouteInfo("City1", "City2")));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/calculator/City1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetRouteInfoLessTimeReturnsInternalServerErrorResponse() throws Exception {
        when(service.calculateRoutes(any(), any(RouteStrategy.class))).thenThrow(new RuntimeException("INTERNAL_SERVER_ERROR"));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/calculator/City1/time").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(""));
    }

    @Test
    public void testGetRouteInfoLessConnectionsReturnsInternalServerErrorResponse() throws Exception {
        when(service.calculateRoutes(any(), any(RouteStrategy.class))).thenThrow(new RuntimeException("INTERNAL_SERVER_ERROR"));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/calculator/City1/connections").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(""));
    }

    @Test
    public void testGetRouteInfoReturnsInternalServerErrorResponse() throws Exception {
        when(service.calculateRoutes(any(), any(RouteStrategy.class))).thenThrow(new RuntimeException("INTERNAL_SERVER_ERROR"));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/calculator/City1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(""));
    }
}