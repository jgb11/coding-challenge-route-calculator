package org.jgb.routecalculator.service;

import org.jgb.routecalculator.model.CityInfo;

import java.util.List;

/**
 * @author jgb
 * @since 13/02/19 21:53
 */
public interface CityDataRestTemplateService {
    List<CityInfo> getCityList(String city);
}
