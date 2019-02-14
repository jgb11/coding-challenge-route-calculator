package org.jgb.routecalculator.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgb.routecalculator.model.CityInfo;
import org.jgb.routecalculator.service.CityDataRestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author jgb
 * @since 13/02/19 21:47
 */
@Service("cityDataRestTemplateService")
public class CityDataRestTemplateServiceImpl implements CityDataRestTemplateService {

    @Value("${city.data.host}")
    private String host;

    @Value("${city.data.port}")
    private String port;

    @Value("${city.data.path}")
    private String path;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<CityInfo> getCityList(String city) {
        final List objects = restTemplate.getForObject("http://" + host + ":" + port + "/" + path + "/" + city, List.class);

        return mapper.convertValue(objects, new TypeReference<List<CityInfo>>() { });
    }
}
