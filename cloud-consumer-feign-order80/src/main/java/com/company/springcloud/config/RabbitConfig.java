package com.company.springcloud.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
public class RabbitConfig {
    public static final String TOPIC_EXCHANGE_NAME = "topic-monitor-exchange";

    public static final String TOPIC_MONITOR_QUEUE = "monitor-topic-queue";

    @Bean("monitor")
    public TopicExchange monitorTopicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME,true,false);
    }

    @Bean
    public Queue topicQueue() {
        return new Queue(TOPIC_MONITOR_QUEUE,true);
    }

    @Bean
    public Binding monitorTopicBinding(){
        return BindingBuilder.bind(topicQueue()).to(monitorTopicExchange()).with("monitor");
    }
}
