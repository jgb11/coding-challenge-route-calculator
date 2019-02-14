package org.jgb.routecalculator.service.impl;

import org.jgb.routecalculator.model.CityInfo;
import org.jgb.routecalculator.model.RouteInfo;
import org.jgb.routecalculator.service.CityDataRestTemplateService;
import org.jgb.routecalculator.service.RouteCalculatorService;
import org.jgb.routecalculator.service.strategy.RouteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jgb
 * @since 13/02/19 17:13
 */
@Service("routeCalculatorService")
public class RouteCalculatorServiceImpl implements RouteCalculatorService {

    @Autowired
    private CityDataRestTemplateService cityDataRestTemplateService;

    @Override
    public List<RouteInfo> calculateRoutes(String cityOrigin, RouteStrategy strategy) {

        final List<CityInfo> cityInfoList = cityDataRestTemplateService.getCityList(cityOrigin);

        final List<RouteInfo> candidateRoutesList = cityInfoList.stream()
                .map(cityInfo -> new RouteInfo(cityInfo.getCity(), cityInfo.getCityDestiny(), cityInfo))
                .collect(Collectors.toList());

        return calculateFinalRouteList(cityOrigin, strategy, candidateRoutesList);
    }

    private List<RouteInfo> calculateFinalRouteList(String cityOrigin, RouteStrategy strategy, List<RouteInfo> candidateRoutesList) {
        final List<RouteInfo> finalRoutesList = new ArrayList<>(candidateRoutesList);
        candidateRoutesList.forEach(candidateRoute -> {
            final CityInfo lastConnectionCityInfo = getLastConnectionCityInfo(candidateRoute);
            final List<CityInfo> newConnectionCityInfoList =
                    cityDataRestTemplateService.getCityList(lastConnectionCityInfo.getCityDestiny());

            newConnectionCityInfoList.forEach(connection -> {
                if (!cityOrigin.equals(connection.getCityDestiny())) {
                    final Optional<RouteInfo> existingDestinyRouteInfo = candidateRoutesList.stream()
                            .filter(ri -> ri.getCityDestiny().equals(connection.getCityDestiny()))
                            .findFirst();

                    RouteInfo newRouteInfo = new RouteInfo(candidateRoute, connection);
                    if (existingDestinyRouteInfo.isPresent()) {
                        finalRoutesList.remove(existingDestinyRouteInfo.get());
                        newRouteInfo = strategy.compareRoutes(existingDestinyRouteInfo.get(), newRouteInfo);
                    }
                    finalRoutesList.add(newRouteInfo);
                }
            });
        });
        return finalRoutesList;
    }

    private CityInfo getLastConnectionCityInfo(RouteInfo route) {
        return route.getConnections().get(route.getConnections().size() - 1);
    }

}
