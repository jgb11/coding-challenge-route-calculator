package org.jgb.routecalculator.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

import java.time.LocalTime;
import java.util.Objects;

/**
 * @author jgb
 * @since 12/02/19 19:37
 */
public class CityInfo {

    private String id;

    private String city;
    private String cityDestiny;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime departureTime;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime arrivalTime;

    public CityInfo() {
    }

    public CityInfo(String city, String cityDestiny, LocalTime departureTime, LocalTime arrivalTime) {
        this.city = city;
        this.cityDestiny = cityDestiny;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public String getCityDestiny() {
        return cityDestiny;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityInfo)) return false;
        CityInfo cityInfo1 = (CityInfo) o;
        return Objects.equals(city, cityInfo1.city) &&
                Objects.equals(cityDestiny, cityInfo1.cityDestiny) &&
                Objects.equals(departureTime, cityInfo1.departureTime) &&
                Objects.equals(arrivalTime, cityInfo1.arrivalTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(city, cityDestiny, departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "city='" + city + '\'' +
                ", cityDestiny='" + cityDestiny + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
