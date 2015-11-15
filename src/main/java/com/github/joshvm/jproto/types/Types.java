package com.github.joshvm.jproto.types;

import java.util.HashMap;
import java.util.Map;

public final class Types {

    private static final Map<String, Type> MAP = new HashMap<>();

    static{
        registerDefaults();
    }

    private Types(){}

    private static void registerDefaults(){
        add(Type.BOOLEAN, "bool", "boolean");
        add(Type.BYTE, "byte", "i8");
        add(Type.UNSIGNED_BYTE, "ubyte", "u8");
        add(Type.SHORT, "short", "i16");
        add(Type.UNSIGNED_SHORT, "ushort", "u16");
        add(Type.CHARACTER, "char");
        add(Type.INTEGER, "int", "i32");
        add(Type.UNSIGNED_INTEGER, "uint", "u32");
        add(Type.FLOAT, "float", "f32");
        add(Type.LONG, "long", "i64");
        add(Type.DOUBLE, "double", "f64");
        add(Type.BYTE_STRING, "bytestring", "i8string", "byte_string", "i8_string");
        add(Type.SHORT_STRING, "shortstring", "i16string", "short_string", "i16_string");
        add(Type.INTEGER_STRING, "intstring", "i32string", "int_string", "i32_string", "integerstring", "integer_string");
    }

    public static void add(final Type type, final String first, final String... other){
        MAP.put(first, type);
        for(final String o : other)
            MAP.put(o, type);
    }

    public static <T> Type<T> get(final String name){
        return MAP.get(name);
    }
}
