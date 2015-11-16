package com.github.joshvm.jproto.pkt;

import com.github.joshvm.jproto.PacketStructure;
import com.github.joshvm.jproto.msg.Message;
import com.github.joshvm.jproto.type.Type;
import com.github.joshvm.jproto.value.Value;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Optional;

public class Packet {

    private final PacketStructure structure;
    private final int length;
    private final byte[] payload;

    private DataInputStream in;

    public Packet(final PacketStructure structure, final int length, final byte[] payload){
        this.structure = structure;
        this.length = length;
        this.payload = payload;
    }

    public PacketStructure structure(){
        return structure;
    }

    public int length(){
        return length;
    }

    public byte[] payload(){
        return payload;
    }

    public Packet startReading(){
        if(in != null)
            finishReading();
        in = new DataInputStream(new ByteArrayInputStream(payload));
        return this;
    }

    public <W, R extends W> R read(final Type<W, R> type) throws IOException{
        if(in == null)
            startReading();
        return type.read(in);
    }

    public <W, R extends W> R tryRead(final Type<W, R> type){
        try{
            return read(type);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public <W, R extends W> Optional<R> tryReadOpt(final Type<W, R> type){
        return Optional.ofNullable(tryRead(type));
    }

    public Packet finishReading(){
        try{
            if(in != null)
                in.close();
        }catch(Exception ex){}
        return this;
    }

    public Message message() throws IOException{
        startReading();
        final Message msg = new Message(this);
        for(final Value value : structure.values())
            value.read(in, msg);
        return msg;
    }

    public Message tryMessage(){
        try{
            return message();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
