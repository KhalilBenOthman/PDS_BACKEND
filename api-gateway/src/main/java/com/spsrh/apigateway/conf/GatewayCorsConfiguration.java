package com.spsrh.apigateway.conf;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOriginPattern("*"); // Support patterns in CORS origins
        corsConfig.addAllowedMethod("*");       // Allow all HTTP methods
        corsConfig.addAllowedHeader("*");       // Allow all headers
        corsConfig.setAllowCredentials(true);   // Allow credentials (cookies, headers)

        corsConfig.addExposedHeader("Access-Control-Allow-Origin");
        corsConfig.addExposedHeader("Access-Control-Allow-Methods");
        corsConfig.addExposedHeader("Access-Control-Allow-Headers");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
    
    @Bean
    public GlobalFilter logRequestFilter() {
        return (exchange, chain) -> {
            System.out.println("Request: " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getURI());
            return chain.filter(exchange);
        };
    }
}


