package org.jgb.routecalculator.service.impl;

import org.jgb.routecalculator.model.CityInfo;
import org.jgb.routecalculator.service.CityDataRestTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author jgb
 * @since 14/02/19 8:36
 */
@RunWith(MockitoJUnitRunner.class)
public class CityDataRestTemplateServiceImplTest {

    @InjectMocks
    private CityDataRestTemplateService service = new CityDataRestTemplateServiceImpl();

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        final Map cityInfoHashMap = new LinkedHashMap();
        cityInfoHashMap.put("id", "1");
        cityInfoHashMap.put("city", "City1");
        cityInfoHashMap.put("cityDestiny", "City2");
        cityInfoHashMap.put("departureTime", LocalTime.NOON.toString());
        cityInfoHashMap.put("arrivalTime", LocalTime.MIDNIGHT.toString());
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(Collections.singletonList(cityInfoHashMap));
    }

    @Test
    public void testGetCityListReturnCorrectObjectTypeInsideList() {
        final List response = service.getCityList("City1");
        response.forEach(o -> assertThat("All objects inside response list must be CityInfo", o, instanceOf(CityInfo.class)));
    }
}