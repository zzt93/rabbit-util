package com.superid;

import org.junit.Test;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * Created by zzt on 17/6/19.
 */
public class MessageFormatSenderTest {

    @Test
    public void main() {
        // set up connection
        CachingConnectionFactory cf = new CachingConnectionFactory("192.168.1.100", 5672);
        cf.setUsername("timer");
        cf.setPassword("timer");
        cf.setVirtualHost("timer_host");

        // set up the queue, exchange, binding on the broker
        RabbitAdmin admin = new RabbitAdmin(cf);
        Queue queue = new Queue("timerServer");
        admin.declareQueue(queue);
        TopicExchange exchange = new TopicExchange("timerExchange");
        admin.declareExchange(exchange);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("timer.*.add"));

        // set up the listener and container
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(cf);

        MessageListenerAdapter adapter = new MessageListenerAdapter(new Object() {
            public void handleMessage(String msg) {
                System.out.println(msg);
            }
        });
        container.setMessageListener(adapter);
        container.setQueueNames("timerServer");
        container.start();

        RabbitTemplate template = new RabbitTemplate(cf);
        template.setExchange("timerExchange");

        MessageFormatSender sender = new MessageFormatSender(template);
    }
}