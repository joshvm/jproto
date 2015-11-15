package com.github.joshvm.jproto.msg;

import com.github.joshvm.jproto.PacketStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Message {

    private final PacketStructure structure;
    private final Map<String, Object> map;

    public Message(final PacketStructure structure){
        this.structure = structure;

        map = new HashMap<>();
    }

    public PacketStructure structure(){
        return structure;
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

    @Override
    public String toString() {
        return "Message(" + map.entrySet().stream()
                .map(e -> String.format("%s=%s", e.getKey(), e.getValue()))
                .collect(Collectors.joining(", ")) + ")";
    }
}
