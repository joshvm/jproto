package com.github.joshvm.jproto.util;

import com.github.joshvm.jproto.types.Type;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataBuffer {

    private final ByteArrayOutputStream baos;
    private DataOutputStream out;

    public DataBuffer(){
        baos = new ByteArrayOutputStream();

        out = new DataOutputStream(baos);
    }

    public <T> DataBuffer write(final Type<T> type, final T value) throws IOException {
        type.write(out, value);
        return this;
    }

    public <T> DataBuffer tryWrite(final Type<T> type, final T value){
        try{
            write(type, value);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return this;
    }

    public void close(){
        if(out == null)
            return;
        try{
            out.close();
            out = null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public byte[] bytes(final boolean close){
        if(close)
            close();
        return baos.toByteArray();
    }

    public byte[] bytes(){
        return bytes(true);
    }

    public static DataBuffer buffer(){
        return new DataBuffer();
    }
}
