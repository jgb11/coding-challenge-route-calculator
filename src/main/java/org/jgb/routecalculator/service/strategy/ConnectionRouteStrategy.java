package org.jgb.routecalculator.service.strategy;

import org.jgb.routecalculator.model.RouteInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static org.jgb.routecalculator.service.strategy.ConnectionRouteStrategy.CONNECTION_ROUTE_STRATEGY;

@Qualifier(CONNECTION_ROUTE_STRATEGY)
@Component
public class ConnectionRouteStrategy implements RouteStrategy {

    public static final String CONNECTION_ROUTE_STRATEGY = "ConnectionRouteStrategy";

    @Override
    public RouteInfo compareRoutes(RouteInfo currentRoute, RouteInfo routeToCompare) {
        if (currentRoute.getTotalConnections() < routeToCompare.getTotalConnections()) {
            return currentRoute;
        }
        return routeToCompare;
    }
}
