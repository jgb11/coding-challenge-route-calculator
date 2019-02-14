package org.jgb.routecalculator.controller;

import org.jgb.routecalculator.model.RouteInfo;
import org.jgb.routecalculator.service.RouteCalculatorService;
import org.jgb.routecalculator.service.strategy.RouteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jgb.routecalculator.service.strategy.impl.ConnectionRouteStrategy.CONNECTION_ROUTE_STRATEGY;
import static org.jgb.routecalculator.service.strategy.impl.TimeRouteStrategy.TIME_ROUTE_STRATEGY;

/**
 * @author jgb
 * @since 13/02/19 13:35
 */
@RefreshScope
@RestController
@RequestMapping(path = "/api/v1/calculator")
public class RouteCalculatorController {

    @Autowired
    private RouteCalculatorService service;

    @Autowired
    @Qualifier(CONNECTION_ROUTE_STRATEGY)
    private RouteStrategy connectionRouteStrategy;

    @Autowired
    @Qualifier(TIME_ROUTE_STRATEGY)
    private RouteStrategy timeRouteStrategy;

    @GetMapping(path = "/{city}/time", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<RouteInfo>> getRouteInfoLessTime(@PathVariable("city") String city) {
        try {
            final List<RouteInfo> routeInfoList = service.calculateRoutes(city, timeRouteStrategy);
            return new ResponseEntity<>(routeInfoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{city}/connections", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<RouteInfo>> getRouteInfoLessConnections(@PathVariable("city") String city) {
        try {
            final List<RouteInfo> routeInfoList = service.calculateRoutes(city, connectionRouteStrategy);
            return new ResponseEntity<>(routeInfoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{city}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, List<RouteInfo>>> getRouteInfo(@PathVariable("city") String city) {
        try {
            final List<RouteInfo> routeInfoConnectionList = service.calculateRoutes(city, connectionRouteStrategy);
            final List<RouteInfo> routeInfoTimeList = service.calculateRoutes(city, timeRouteStrategy);
            final Map<String, List<RouteInfo>> routeInfoMap = new HashMap<>();
            routeInfoMap.put(TIME_ROUTE_STRATEGY, routeInfoTimeList);
            routeInfoMap.put(CONNECTION_ROUTE_STRATEGY, routeInfoConnectionList);
            return new ResponseEntity<>(routeInfoMap, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
