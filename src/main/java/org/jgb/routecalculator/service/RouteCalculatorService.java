package org.jgb.routecalculator.service;

import org.jgb.routecalculator.model.RouteInfo;
import org.jgb.routecalculator.service.strategy.RouteStrategy;

import java.util.List;

/**
 * @author jgb
 * @since 13/02/19 17:11
 */
public interface RouteCalculatorService {
    List<RouteInfo> calculateRoutes(String city, RouteStrategy routeStrategy);
}
