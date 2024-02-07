package com.company.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */

@Configuration
public class GateWayConfig {

    @Bean
    public RouteLocator customeRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
       RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
       routes.route("path_route_baidu",r -> r.path("/").uri("https://quanmin.baidu.com/sv?source=share-h5&pd=qm_share_search&vid=4989699985130461867")).build();
       return routes.build();
    }
}
