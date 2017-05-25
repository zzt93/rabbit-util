package com.superid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;

/**
 * Created by zzt on 17/5/24.
 */
public class MessageFormatSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageFormatSender.class);

    private RabbitTemplate rabbitTemplate;

    public MessageFormatSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String msg) {
        MessageFormat messageFormat;
        try {
            messageFormat = MessageFormat.getMessageFormat(msg);
        } catch (IOException e) {
            logger.error("", e);
            return;
        }
        send(messageFormat);
    }

    public void send(MessageFormat messageFormat) {
        send(rabbitTemplate.getRoutingKey(), messageFormat);
    }

    /**
     * @see RabbitTemplate#DEFAULT_EXCHANGE
     * @see RabbitTemplate#DEFAULT_ROUTING_KEY
     */
    public void send(String routingKey, MessageFormat messageFormat) {
        // if using DEFAULT_ROUTING_KEY, remind user
        if (routingKey.equals("")) {
            logger.warn("Using default routing key, you need routing key for your RabbitTemplate");
        }
        if (rabbitTemplate.getExchange().equals("")) {
            logger.warn("Using default routing key, you may need setting exchange for your RabbitTemplate");
        }
        String destinationRoutingKey = messageFormat.getDestinationRoutingKey();
        if (destinationRoutingKey == null) {
            rabbitTemplate.convertAndSend(messageFormat.toJson());
        }
        rabbitTemplate.convertAndSend(destinationRoutingKey, messageFormat.toJson());
    }


}
