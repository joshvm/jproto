package com.github.joshvm.jproto.type;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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

    public static void add(final Type type, final String name){
        MAP.put(name, type);
    }

    public static void add(final Type type, final String... names){
        for(final String name : names)
            add(type, name);
    }

    public static void add(final InputStream in) throws Exception{
        final Document doc = Jsoup.parse(in, "UTF-8", "", Parser.xmlParser());
        for(final Element t : doc.getElementsByTag("type")){
            final Class<? extends Type> clazz = (Class<? extends Type>) Class.forName(t.attr("class"));
            final Type type = clazz.newInstance();
            for(final Element n : t.getElementsByTag("name"))
                add(type, n.ownText());
        }
    }

    public static void add(final File file) throws Exception{
        try(final FileInputStream in = new FileInputStream(file)){
            add(in);
        }
    }

    public static void add(final Path path) throws Exception{
        try(final InputStream in = Files.newInputStream(path, StandardOpenOption.READ)){
            add(in);
        }
    }

    public static boolean tryAdd(final InputStream in){
        try{
            add(in);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean tryAdd(final File file){
        try{
            add(file);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean tryAdd(final Path path){
        try{
            add(path);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static <W, R extends W> Type<W, R> get(final String name){
        return MAP.get(name);
    }
}
