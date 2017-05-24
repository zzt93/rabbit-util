package com.superid;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by zzt on 17/5/23.
 */
public class MessageFormat {

    private static Gson gson = new Gson();
    private String destinationRoutingKey;
    private long id;
    private HashMap<String, Object> payload = new HashMap<String, Object>();

    public static MessageFormat getMessageFormat(String json) throws IOException {
        return gson.fromJson(json, MessageFormat.class);
    }

    public MessageFormat() {
    }

    public MessageFormat(String destinationRoutingKey) {
        this.destinationRoutingKey = destinationRoutingKey;
    }

    public String getDestinationRoutingKey() {
        return destinationRoutingKey;
    }

    public void setDestinationRoutingKey(String destinationRoutingKey) {
        this.destinationRoutingKey = destinationRoutingKey;
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
                "destinationRoutingKey='" + destinationRoutingKey + '\'' +
                ", id=" + id +
                ", payload=" + payload +
                '}';
    }
}
