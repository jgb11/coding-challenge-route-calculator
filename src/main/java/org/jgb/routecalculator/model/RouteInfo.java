package org.jgb.routecalculator.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jgb
 * @since 13/02/19 16:36
 */
public class RouteInfo {

    private String cityOrigin;
    private String cityDestiny;
    private List<CityInfo> connections = new ArrayList<>();

    public RouteInfo(String cityOrigin, String cityDestiny, CityInfo connection) {
        this.cityOrigin = cityOrigin;
        this.cityDestiny = cityDestiny;
        this.connections.add(connection);
    }

    public RouteInfo(String cityOrigin, String cityDestiny) {
        this.cityOrigin = cityOrigin;
        this.cityDestiny = cityDestiny;
    }

    public RouteInfo(RouteInfo route, CityInfo connection) {
        this.cityOrigin = route.getCityOrigin();
        this.cityDestiny = connection.getCityDestiny();
        this.connections = new ArrayList<>(route.getConnections());
        this.connections.add(connection);
    }

    public boolean addConnection(CityInfo connection) {
        this.connections.add(connection);
        return true;
    }

    public int getTotalConnections() {
        return connections.size();
    }

    public Long getTotalTime() {

        if (!this.connections.isEmpty()) {
            LocalTime startTime = this.connections.get(0).getDepartureTime();
            LocalTime finishTime = this.connections.get(this.connections.size() - 1).getArrivalTime();

            return startTime.until(finishTime, ChronoUnit.MINUTES);
        }

        return 0L;
    }

    public String getCityOrigin() {
        return cityOrigin;
    }

    public void setCityOrigin(String cityOrigin) {
        this.cityOrigin = cityOrigin;
    }

    public String getCityDestiny() {
        return cityDestiny;
    }

    public void setCityDestiny(String cityDestiny) {
        this.cityDestiny = cityDestiny;
    }

    public List<CityInfo> getConnections() {
        return connections;
    }

    public void setConnections(List<CityInfo> connections) {
        this.connections = new ArrayList<>(connections);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteInfo)) return false;
        RouteInfo routeInfo = (RouteInfo) o;
        return Objects.equals(cityOrigin, routeInfo.cityOrigin) &&
                Objects.equals(cityDestiny, routeInfo.cityDestiny) &&
                Objects.equals(connections, routeInfo.connections);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cityOrigin, cityDestiny, connections);
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "cityOrigin='" + cityOrigin + '\'' +
                ", cityDestiny='" + cityDestiny + '\'' +
                ", connections=" + connections +
                '}';
    }
}
