package com.company.springcloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@Slf4j
@Scope("prototype")
public class MonitorCallBack implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback{
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("回调数据：{}", correlationData);
        log.info("确认结果：{}", ack);
        log.info("返回原因：{}", cause);
    }

    @Override
    public void returnedMessage(Message message, int replyCode,  @NonNull String replyText,
                                @NonNull String exchange, @NonNull String routingKey) {
        log.info("发送消息：{}", message.toString());
        log.info("结果状态码：{}", replyCode);
        log.info("结果状态信息：{}", replyText);
        log.info("交换机：{}", exchange);
        log.info("路由key：{}", routingKey);
    }
}
