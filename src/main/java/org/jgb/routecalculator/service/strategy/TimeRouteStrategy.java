package org.jgb.routecalculator.service.strategy;

import org.jgb.routecalculator.model.RouteInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static org.jgb.routecalculator.service.strategy.TimeRouteStrategy.TIME_ROUTE_STRATEGY;

@Qualifier(TIME_ROUTE_STRATEGY)
@Component
public class TimeRouteStrategy implements RouteStrategy {

    public static final String TIME_ROUTE_STRATEGY = "TimeRouteStrategy";

    @Override
    public RouteInfo compareRoutes(RouteInfo currentRoute, RouteInfo routeToCompare) {
        if (currentRoute.getTotalTime() < routeToCompare.getTotalTime()) {
            return currentRoute;
        }
        return routeToCompare;
    }

}
