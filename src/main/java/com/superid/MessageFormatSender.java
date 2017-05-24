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

    /**
     *
     * @see RabbitTemplate#DEFAULT_EXCHANGE
     * @see RabbitTemplate#DEFAULT_ROUTING_KEY
     */
    public void send(MessageFormat messageFormat) {
        // if using DEFAULT_EXCHANGE, remind user
        if (rabbitTemplate.getExchange().equals("")) {
            logger.warn("Using default exchange, you may need setting exchange for your RabbitTemplate");
        }
        String destinationRoutingKey = messageFormat.getDestinationRoutingKey();
        if (destinationRoutingKey == null) {
            rabbitTemplate.convertAndSend(messageFormat.toJson());
        }
        rabbitTemplate.convertAndSend(destinationRoutingKey, messageFormat.toJson());
    }


}
