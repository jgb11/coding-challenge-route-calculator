package org.jgb.routecalculator.controller;

import org.jgb.routecalculator.model.CityInfo;
import org.jgb.routecalculator.model.RouteInfo;
import org.jgb.routecalculator.service.CityDataRestTemplateService;
import org.jgb.routecalculator.service.RouteCalculatorService;
import org.jgb.routecalculator.service.impl.CityDataRestTemplateServiceImpl;
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
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.jgb.routecalculator.service.strategy.ConnectionRouteStrategy.CONNECTION_ROUTE_STRATEGY;
import static org.jgb.routecalculator.service.strategy.TimeRouteStrategy.TIME_ROUTE_STRATEGY;

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
        final List<RouteInfo> routeInfoList = service.calculateRoutes(city, timeRouteStrategy);
        return new ResponseEntity<>(routeInfoList, HttpStatus.OK);
    }

    @GetMapping(path = "/{city}/connections", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<RouteInfo>> getRouteInfoLessConnections(@PathVariable("city") String city) {
        final List<RouteInfo> routeInfo = service.calculateRoutes(city, connectionRouteStrategy);
        return new ResponseEntity<>(routeInfo, HttpStatus.OK);
    }
}
