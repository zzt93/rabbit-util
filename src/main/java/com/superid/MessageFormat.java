package com.superid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by zzt on 17/5/23.
 */
public class MessageFormat {

    private static final transient String REPLY_ROUTING_KEY = "replyRoutingKey";
    private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    @Expose
    private Long id;
    @Expose
    private HashMap<String, Object> payload = new HashMap<String, Object>();

    public MessageFormat() {
    }

    public MessageFormat(String replyRoutingKey) {
        setReplyRoutingKey(replyRoutingKey);
    }

    public static MessageFormat getMessageFormat(String json) throws IOException {
        return gson.fromJson(json, MessageFormat.class);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HashMap<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(HashMap<String, Object> payload) {
        this.payload = payload;
    }

    public Object getPayload(String key) {
        return payload.get(key);
    }

    public MessageFormat addPayload(String key, Object o) {
        payload.put(key, o);
        return this;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "MessageFormat{" +
                "id=" + id +
                ", payload=" + payload +
                '}';
    }

    String getReplyRoutingKey() {
        return (String) payload.get(REPLY_ROUTING_KEY);
    }

    private void setReplyRoutingKey(String destinationRoutingKey) {
        payload.put(REPLY_ROUTING_KEY, destinationRoutingKey);
    }
}
