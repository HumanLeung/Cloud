package com.company.ribbonrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HumanLeung
 * @create 2021/10/2 20:13
 */
@Configuration
public class RibbonRule {

    @Bean
    public IRule rule(){
        return new RandomRule();
    }

}
