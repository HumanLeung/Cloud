package org.company.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.company.entity.Stage;
import org.company.repository.StageRepository;
import org.company.service.StageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Administrator
 */
@Component
public class TransactionMonitor {

    private final StageService stageService;
    private final StageRepository stageRepository;

    public TransactionMonitor(StageService stageService, StageRepository stageRepository) {
        this.stageService = stageService;
        this.stageRepository = stageRepository;
    }

    @RabbitListener(queues = "monitor-topic-queue",ackMode = "MANUAL")
    public void receiveMessage(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)  {
        String msg = new String(message.getBody());
        Stage stage = JSON.parseObject(msg,Stage.class);
        stageService.saveRecord(stage);
        try {
            channel.basicAck(deliveryTag,false);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("MonitorConsumer Monitor————> " + msg);
    }
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "monitor.finish", durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = "monitor.finish", type = ExchangeTypes.TOPIC), key = "finish"), ackMode = "MANUAL")
    public void receiveFinishedMsg(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag){
        System.out.println("consumerNoQueue: " + new String(message.getBody()));
        try {
            Optional<Stage> stageOptional = stageRepository.findByTId(new String(message.getBody()));
            if (stageOptional.isPresent()){
                Stage stage = stageOptional.get();
                stage.setWaiterCount(stage.getWaiterCount() - 1);
                stageRepository.save(stage);
            }
            channel.basicAck(deliveryTag,false);
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

}
