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
import java.util.Set;
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
            final List<CityInfo> newConnectionCityInfoList = cityDataRestTemplateService.getCityList(lastConnectionCityInfo.getCityDestiny());
            calculateCandidateRoutes(cityOrigin, finalRoutesList, candidateRoute, newConnectionCityInfoList, lastConnectionCityInfo);
        });

        return applyStrategy(finalRoutesList, strategy);
    }

    private void calculateCandidateRoutes(String cityOrigin, List<RouteInfo> finalRoutesList, RouteInfo candidateRoute,
                                          List<CityInfo> newConnectionCityInfoList, CityInfo cityInfo) {
        if (newConnectionCityInfoList.isEmpty()) {
            return;
        }
        newConnectionCityInfoList.stream()
                .filter(connection -> !cityOrigin.equals(connection.getCityDestiny())
                        && connection.getDepartureTime().isAfter(cityInfo.getArrivalTime()))
                .forEach(connection -> {
                    final RouteInfo newCandidateRoute = new RouteInfo(candidateRoute, connection);
                    finalRoutesList.add(newCandidateRoute);
                    final List<CityInfo> newConnectionCityInfoListFromConnection =
                            cityDataRestTemplateService.getCityList(connection.getCityDestiny());
                    calculateCandidateRoutes(
                            cityOrigin, finalRoutesList, newCandidateRoute, newConnectionCityInfoListFromConnection, connection);
                });
    }

    private List<RouteInfo> applyStrategy(List<RouteInfo> finalRoutesList, RouteStrategy strategy) {
        final Set<String> destinyList = finalRoutesList.stream().map(RouteInfo::getCityDestiny).collect(Collectors.toSet());
        return destinyList.stream()
                .map(destiny -> {
                    final List<RouteInfo> destinyRoutes = finalRoutesList.stream()
                            .filter(routeInfo -> destiny.equals(routeInfo.getCityDestiny()))
                            .collect(Collectors.toList());
                    return destinyRoutes.size() == 1
                            ? destinyRoutes.get(0) : destinyRoutes.stream().reduce(strategy::compareRoutes).orElse(null);
                })
                .filter(routeInfo -> Optional.ofNullable(routeInfo).isPresent())
                .collect(Collectors.toList());
    }

    private CityInfo getLastConnectionCityInfo(RouteInfo route) {
        return route.getConnections().get(route.getConnections().size() - 1);
    }

}
