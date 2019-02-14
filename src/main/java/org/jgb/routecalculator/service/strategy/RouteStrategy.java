package org.jgb.routecalculator.service.strategy;

import org.jgb.routecalculator.model.RouteInfo;


public interface RouteStrategy {

    RouteInfo compareRoutes(RouteInfo currentRoute, RouteInfo routeToCompare);

}
