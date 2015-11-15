package com.github.joshvm.jproto.types;

import com.github.joshvm.jproto.util.ProtocolConstants;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public interface Type<T> extends ProtocolConstants{

    BooleanType BOOLEAN = new BooleanType();
    ByteType BYTE = new ByteType();
    UnsignedByteType UNSIGNED_BYTE = new UnsignedByteType();
    ShortType SHORT = new ShortType();
    UnsignedShortType UNSIGNED_SHORT = new UnsignedShortType();
    CharacterType CHARACTER = new CharacterType();
    IntegerType INTEGER = new IntegerType();
    UnsignedIntegerType UNSIGNED_INTEGER = new UnsignedIntegerType();
    FloatType FLOAT = new FloatType();
    LongType LONG = new LongType();
    DoubleType DOUBLE = new DoubleType();
    ByteStringType BYTE_STRING = new ByteStringType();
    ShortStringType SHORT_STRING = new ShortStringType();
    IntegerStringType INTEGER_STRING = new IntegerStringType();

    int length();

    Class<T> type();

    T read(final DataInputStream in) throws IOException;

    default void write(final DataOutputStream out, final T value) throws IOException{
        throw new AbstractMethodError(String.format("%s is not a writable type!", getClass().getName()));
    }

    default T read(final byte[] bytes) throws IOException{
        try(final DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))){
            return read(dis);
        }
    }

    default T read(final ByteBuffer buffer) throws IOException{
        if(buffer.hasArray())
            return read(buffer.array());
        final byte[] bytes = new byte[buffer.capacity()];
        for(int i = 0; i < bytes.length; i++)
            bytes[i] = buffer.get(i);
        return read(bytes);
    }

    default T tryRead(final DataInputStream in){
        try{
            return read(in);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    default T tryRead(final byte[] bytes){
        try{
            return read(bytes);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    default T tryRead(final ByteBuffer buffer){
        try{
            return read(buffer);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
