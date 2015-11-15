package com.github.joshvm.jproto.pkt;

import com.github.joshvm.jproto.PacketStructure;
import com.github.joshvm.jproto.msg.Message;
import com.github.joshvm.jproto.types.Type;
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

    public <T> T read(final Type<T> type) throws IOException{
        if(in == null)
            startReading();
        return type.read(in);
    }

    public <T> T tryRead(final Type<T> type){
        try{
            return read(type);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public <T> Optional<T> tryReadOpt(final Type<T> type){
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
        final Message msg = new Message(structure);
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
