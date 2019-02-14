package org.jgb.routecalculator.service.impl;

import org.jgb.routecalculator.model.CityInfo;
import org.jgb.routecalculator.model.RouteInfo;
import org.jgb.routecalculator.service.CityDataRestTemplateService;
import org.jgb.routecalculator.service.RouteCalculatorService;
import org.jgb.routecalculator.service.strategy.ConnectionRouteStrategy;
import org.jgb.routecalculator.service.strategy.RouteStrategy;
import org.jgb.routecalculator.service.strategy.TimeRouteStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.jgb.routecalculator.service.strategy.ConnectionRouteStrategy.CONNECTION_ROUTE_STRATEGY;
import static org.jgb.routecalculator.service.strategy.TimeRouteStrategy.TIME_ROUTE_STRATEGY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author jgb
 * @since 14/02/19 8:52
 */
@RunWith(MockitoJUnitRunner.class)
public class RouteCalculatorServiceImplTest {

    private static final String CITY_1 = "City1";
    private static final String CITY_2 = "City2";
    private static final String CITY_3 = "City3";
    private static final String CITY_4 = "City4";

    @InjectMocks
    private RouteCalculatorService service = new RouteCalculatorServiceImpl();

    @Mock
    private CityDataRestTemplateService cityDataRestTemplateService;

    @Before
    public void setUp() throws Exception {
        when(cityDataRestTemplateService.getCityList(CITY_1)).thenReturn(Arrays.asList(
                new CityInfo(CITY_1, CITY_2, LocalTime.parse("07:00:00"), LocalTime.parse("17:00:00")),
                new CityInfo(CITY_1, CITY_3, LocalTime.parse("08:00:00"), LocalTime.parse("09:40:00"))
        ));
        when(cityDataRestTemplateService.getCityList(CITY_3)).thenReturn(Arrays.asList(
                new CityInfo(CITY_3, CITY_2, LocalTime.parse("10:00:00"), LocalTime.parse("12:30:00")),
                new CityInfo(CITY_3, CITY_4, LocalTime.parse("15:00:00"), LocalTime.parse("16:00:00"))
        ));
//        when(cityDataRestTemplateService.getCityList(CITY_4)).thenReturn(Collections.singletonList(
//                new CityInfo(CITY_4, CITY_1, LocalTime.parse("18:00:00"), LocalTime.parse("19:00:00"))
//        ));
    }

    @Test
    public void testCalculateRoutesWithTimeStrategyReturnCorrectRoutes() {
        final List<RouteInfo> routeInfoList = service.calculateRoutes(CITY_1, new TimeRouteStrategy());
        assertEquals("calculateRoutes should generate 3 routes with current mock data", 3, routeInfoList.size());
        routeInfoList.forEach(routeInfo -> assertEquals("Routes city origin must be City1", CITY_1, routeInfo.getCityOrigin()));
        assertEquals("Route 1 city destiny must be City3", CITY_3, routeInfoList.get(0).getCityDestiny());
        assertEquals("Route 1 total connections must be 1", 1, routeInfoList.get(0).getTotalConnections());
        assertEquals("Route 1 total time must be 100 minutes", (Long) 100L, routeInfoList.get(0).getTotalTime());
        assertEquals("Route 2 city destiny must be City2", CITY_2, routeInfoList.get(1).getCityDestiny());
        assertEquals("Route 2 total connections must be 2", 2, routeInfoList.get(1).getTotalConnections());
        assertEquals("Route 2 total time must be 270 minutes", (Long) 270L, routeInfoList.get(1).getTotalTime());
        assertEquals("Route 3 city destiny must be City4", CITY_4, routeInfoList.get(2).getCityDestiny());
        assertEquals("Route 3 total connections must be 2", 2, routeInfoList.get(2).getTotalConnections());
        assertEquals("Route 3 total time must be 480 minutes", (Long) 480L, routeInfoList.get(2).getTotalTime());
    }

    @Test
    public void testCalculateRoutesWithConnectionStrategyReturnCorrectRoutes() {
        final List<RouteInfo> routeInfoList = service.calculateRoutes(CITY_1, new ConnectionRouteStrategy());
        assertEquals("calculateRoutes should generate 3 routes with current mock data", 3, routeInfoList.size());
        routeInfoList.forEach(routeInfo -> assertEquals("Routes city origin must be City1", CITY_1, routeInfo.getCityOrigin()));
        assertEquals("Route 1 city destiny must be City3", CITY_3, routeInfoList.get(0).getCityDestiny());
        assertEquals("Route 1 total connections must be 1", 1, routeInfoList.get(0).getTotalConnections());
        assertEquals("Route 1 total time must be 100 minutes", (Long) 100L, routeInfoList.get(0).getTotalTime());
        assertEquals("Route 2 city destiny must be City2", CITY_2, routeInfoList.get(1).getCityDestiny());
        assertEquals("Route 2 total connections must be 1", 1, routeInfoList.get(1).getTotalConnections());
        assertEquals("Route 2 total time must be 600 minutes", (Long) 600L, routeInfoList.get(1).getTotalTime());
        assertEquals("Route 3 city destiny must be City4", CITY_4, routeInfoList.get(2).getCityDestiny());
        assertEquals("Route 3 total connections must be 2", 2, routeInfoList.get(2).getTotalConnections());
        assertEquals("Route 3 total time must be 480 minutes", (Long) 480L, routeInfoList.get(2).getTotalTime());
    }
}