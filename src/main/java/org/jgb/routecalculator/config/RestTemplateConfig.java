package org.jgb.routecalculator.config;

import org.jgb.routecalculator.config.interceptor.BasicAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author jgb
 * @since 13/02/19 17:16
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "adminPass"));

        return restTemplate;
    }
}
