package com.github.joshvm.jproto.buffer;

import com.github.joshvm.jproto.type.NumericType;
import com.github.joshvm.jproto.type.Type;
import com.github.joshvm.jproto.type.Types;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Buffer {

    protected final ByteArrayOutputStream baos;
    private DataOutputStream out;

    public Buffer(){
        baos = new ByteArrayOutputStream();

        out = new DataOutputStream(baos);
    }

    public int size(){
        return baos.size();
    }

    public <W, R extends W> Buffer write(final Type<W, R> type, final W value) throws IOException {
        type.write(out, value);
        return this;
    }

    public Buffer write(final String type, final Object value) throws IOException{
        write(Types.get(type), value);
        return this;
    }

    public <W, R extends W> Buffer tryWrite(final Type<W, R> type, final W value){
        try{
            return write(type, value);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Buffer tryWrite(final String type, final Object value){
        try{
            return write(type, value);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Buffer write(final byte[] bytes, final int off, final int len) throws IOException{
        out.write(bytes, off, len);
        return this;
    }

    public Buffer write(final byte[] bytes) throws IOException{
        return write(bytes, 0, bytes.length);
    }

    public Buffer tryWrite(final byte[] bytes, final int off, final int len){
        try{
            return write(bytes, off, len);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Buffer tryWrite(final byte[] bytes){
        try{
            return write(bytes);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Buffer write(final Buffer buffer, final boolean close) throws IOException{
        return write(buffer.bytes(close), 0, buffer.size());
    }

    public Buffer write(final Buffer buffer) throws IOException{
        return write(buffer.bytes(), 0, buffer.size());
    }

    public Buffer tryWrite(final Buffer buffer, final boolean close){
        try{
            return write(buffer, close);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Buffer tryWrite(final Buffer buffer){
        try{
            return write(buffer);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
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

    public static Buffer buffer(){
        return new Buffer();
    }

    public static Buffer buffer(final NumericType type, final Number opcode){
        final Buffer buffer = buffer();
        buffer.tryWrite(type, opcode);
        return buffer;
    }
}
