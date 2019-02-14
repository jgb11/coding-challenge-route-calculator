package org.jgb.routecalculator.service.strategy;

import org.jgb.routecalculator.model.CityInfo;
import org.jgb.routecalculator.model.RouteInfo;
import org.jgb.routecalculator.service.strategy.impl.TimeRouteStrategy;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

/**
 * @author jgb
 * @since 14/02/19 10:37
 */
public class TimeRouteStrategyTest {

    private static final String CITY_1 = "City1";
    private static final String CITY_2 = "City2";
    private static final String CITY_3 = "City3";

    private RouteStrategy strategy = new TimeRouteStrategy();

    @Test
    public void testCompareRoutesReturnNewRouteAsBestRoute() {
        final RouteInfo currentRouteInfo = new RouteInfo(CITY_1, CITY_2);
        currentRouteInfo.getConnections().add(new CityInfo(CITY_1, CITY_3, LocalTime.parse("07:00:00"), LocalTime.parse("08:00:00")));
        currentRouteInfo.getConnections().add(new CityInfo(CITY_3, CITY_2, LocalTime.parse("08:00:01"), LocalTime.parse("09:00:00")));
        final RouteInfo newRouteInfo = new RouteInfo(CITY_1, CITY_2);
        newRouteInfo.getConnections().add(new CityInfo(CITY_1, CITY_2, LocalTime.parse("07:00:00"), LocalTime.parse("17:00:10")));

        final RouteInfo bestRouteInfo = strategy.compareRoutes(currentRouteInfo, newRouteInfo);

        assertEquals("Best route must be current route (less time)", currentRouteInfo, bestRouteInfo);
    }

    @Test
    public void testCompareRoutesReturnCurrentRouteAsBestRoute() {
        final RouteInfo currentRouteInfo = new RouteInfo(CITY_1, CITY_2);
        currentRouteInfo.getConnections().add(new CityInfo(CITY_1, CITY_2, LocalTime.parse("07:00:00"), LocalTime.parse("17:00:10")));
        final RouteInfo newRouteInfo = new RouteInfo(CITY_1, CITY_2);
        newRouteInfo.getConnections().add(new CityInfo(CITY_1, CITY_3, LocalTime.parse("07:00:00"), LocalTime.parse("08:00:00")));
        newRouteInfo.getConnections().add(new CityInfo(CITY_3, CITY_2, LocalTime.parse("08:00:01"), LocalTime.parse("09:00:00")));

        final RouteInfo bestRouteInfo = strategy.compareRoutes(currentRouteInfo, newRouteInfo);

        assertEquals("Best route must be new route (less time)", newRouteInfo, bestRouteInfo);
    }
}