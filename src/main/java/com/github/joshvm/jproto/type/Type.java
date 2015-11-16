package com.github.joshvm.jproto.type;

import com.github.joshvm.jproto.type.impl.BooleanType;
import com.github.joshvm.jproto.type.impl.ByteStringType;
import com.github.joshvm.jproto.type.impl.ByteType;
import com.github.joshvm.jproto.type.impl.CharacterType;
import com.github.joshvm.jproto.type.impl.DoubleType;
import com.github.joshvm.jproto.type.impl.FloatType;
import com.github.joshvm.jproto.type.impl.IntegerStringType;
import com.github.joshvm.jproto.type.impl.IntegerType;
import com.github.joshvm.jproto.type.impl.LongType;
import com.github.joshvm.jproto.type.impl.ShortStringType;
import com.github.joshvm.jproto.type.impl.ShortType;
import com.github.joshvm.jproto.type.impl.UnsignedByteType;
import com.github.joshvm.jproto.type.impl.UnsignedIntegerType;
import com.github.joshvm.jproto.type.impl.UnsignedShortType;
import com.github.joshvm.jproto.util.ProtocolConstants;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Type<W, R extends W> extends ProtocolConstants{

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

    int readLength();

    default int writeLength(){
        return readLength();
    }

    Class<R> readType();

    Class<W> writeType();

    R read(final DataInputStream in) throws IOException;

    default void write(final DataOutputStream out, final W value) throws IOException{
        throw new AbstractMethodError(String.format("%s is not a writable type!", getClass().getName()));
    }

    default boolean tryWrite(final DataOutputStream out, final W value){
        try{
            write(out, value);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    default R read(final byte[] bytes) throws IOException{
        try(final DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))){
            return read(dis);
        }
    }

    default R tryRead(final DataInputStream in){
        try{
            return read(in);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    default R tryRead(final byte[] bytes){
        try{
            return read(bytes);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
