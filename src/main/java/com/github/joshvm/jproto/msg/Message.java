package com.github.joshvm.jproto.msg;

import com.github.joshvm.jproto.pkt.Packet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Message {

    private final Packet packet;
    private final Map<String, Object> map;

    public Message(final Packet packet){
        this.packet = packet;

        map = new HashMap<>();
    }

    public Packet packet(){
        return packet;
    }

    public Message put(final String key, final Object value){
        map.put(key, value);
        return this;
    }

    public <T> T get(final String key){
        return (T) map.get(key);
    }

    public <T> T get(final String key, final T defaultValue){
        return (T) map.getOrDefault(key, defaultValue);
    }

    public <T> T get(final String key, final Class<T> clazz){
        return clazz.cast(get(key));
    }

    public <T> T get(final String key, final Class<T> clazz, final T defaultValue){
        return clazz.cast(map.getOrDefault(key, defaultValue));
    }

    public <T> Optional<T> opt(final String key){
        return Optional.ofNullable(get(key));
    }

    public <T> Optional<T> opt(final String key, final Class<T> clazz){
        return Optional.ofNullable(get(key, clazz));
    }

    public boolean getBoolean(final String key, final boolean defaultValue){
        return get(key, defaultValue);
    }

    public boolean getBoolean(final String key){
        return getBoolean(key, false);
    }

    public int getInt(final String key, final int defaultValue){
        return get(key, defaultValue);
    }

    public int getInt(final String key){
        return getInt(key, 0);
    }

    public short getShort(final String key, final short defaultValue){
        return get(key, defaultValue);
    }

    public short getShort(final String key){
        return getShort(key, (short)0);
    }

    public double getDouble(final String key, final double defaultValue){
        return get(key, defaultValue);
    }

    public double getDouble(final String key){
        return getDouble(key, 0);
    }

    public String getString(final String key, final String defaultValue){
        return get(key, defaultValue);
    }

    public String getString(final String key){
        return getString(key, null);
    }

    public Number getNumber(final String key, final Number defaultValue){
        return get(key, defaultValue);
    }

    public Number getNumber(final String key){
        return get(key, 0);
    }

    @Override
    public String toString() {
        return "Message(" + map.entrySet().stream()
                .map(e -> String.format("%s=%s", e.getKey(), e.getValue()))
                .collect(Collectors.joining(", ")) + ")";
    }
}
