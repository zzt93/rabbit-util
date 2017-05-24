package com.superid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by zzt on 17/5/23.
 * <p>
 * Notice: if extends this class as receiver, not to set listener method
 */
public abstract class MessageFormatReceiver implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageFormatReceiver.class);

    /**
     * @see org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter#ORIGINAL_DEFAULT_LISTENER_METHOD
     */
    private void handleMessage(String msg) {
        MessageFormat messageFormat = null;
        try {
            messageFormat = MessageFormat.getMessageFormat(msg);
        } catch (IOException e) {
            logger.error("", e);
        }
        receiveMessage(messageFormat);
    }

    public void onMessage(Message message) {
        try {
            handleMessage(new String(message.getBody(), "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param message
     */
    public abstract void receiveMessage(MessageFormat message);

}
