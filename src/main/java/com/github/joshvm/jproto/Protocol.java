package com.github.joshvm.jproto;

import com.github.joshvm.jproto.buffer.Buffer;
import com.github.joshvm.jproto.msg.Message;
import com.github.joshvm.jproto.pkt.Packet;
import com.github.joshvm.jproto.pkt.PacketBuffer;
import com.github.joshvm.jproto.type.NumericType;
import com.github.joshvm.jproto.type.Type;
import com.github.joshvm.jproto.util.ProtocolConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Protocol implements ProtocolConstants{

    private final Map<Integer, PacketStructure> map;

    private NumericType<? extends Number> opcodeType;

    public Protocol(){
        map = new HashMap<>();

        opcodeType = Type.UNSIGNED_BYTE;
    }

    public Map<Integer, PacketStructure> map(){
        return map;
    }

    public NumericType<? extends Number> opcodeType(){
        return opcodeType;
    }

    public void opcodeType(final NumericType<? extends Number> opcodeType){
        this.opcodeType = opcodeType;
    }

    public Buffer newBuffer(final Number opcode){
        return Buffer.buffer(opcodeType, opcode);
    }

    public PacketBuffer newPacketBuffer(final Number opcode, final int length){
        return PacketBuffer.buffer(opcodeType, opcode, length);
    }

    public PacketBuffer newPacketBuffer(final Number opcode){
        return newPacketBuffer(opcode, get(opcode.intValue()).length());
    }

    public PacketBuffer newVarBytePacketBuffer(final Number opcode){
        return newPacketBuffer(opcode, VARIABLE_BYTE);
    }

    public PacketBuffer newVarShortPacketBuffer(final Number opcode){
        return newPacketBuffer(opcode, VARIABLE_SHORT);
    }

    public PacketBuffer newVarIntPacketBuffer(final Number opcode){
        return newPacketBuffer(opcode, VARIABLE_INT);
    }

    public void add(final PacketStructure struct){
        map.put(struct.opcode(), struct);
    }

    public PacketStructure get(final int opcode){
        return map.get(opcode);
    }

    public Packet read(final int opcode, final DataInputStream in) throws IOException{
        return get(opcode).read(in);
    }

    public Packet tryRead(final int opcode, final DataInputStream in){
        return get(opcode).tryRead(in);
    }

    public Packet read(final int opcode, final byte[] bytes) throws IOException{
        return get(opcode).read(bytes);
    }

    public Packet tryRead(final int opcode, final byte[] bytes){
        try{
            return read(opcode, bytes);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Message readMessage(final int opcode, final DataInputStream in) throws IOException{
        return read(opcode, in).message();
    }

    public Message tryReadMessage(final int opcode, final DataInputStream in){
        try{
            return readMessage(opcode, in);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Message readMessage(final int opcode, final byte[] bytes) throws IOException{
        return read(opcode, bytes).message();
    }

    public Message tryReadMessage(final int opcode, final byte[] bytes){
        try{
            return readMessage(opcode, bytes);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Packet read(final DataInputStream in) throws IOException{
        final Number opcode = opcodeType.read(in);
        return read(opcode.intValue(), in);
    }

    public Packet tryRead(final DataInputStream in){
        try{
            return read(in);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Packet read(final byte[] bytes) throws IOException{
        try(final DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))){
            return read(in);
        }
    }

    public Packet tryRead(final byte[] bytes){
        try{
            return read(bytes);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Message readMessage(final DataInputStream in) throws IOException{
        return read(in).message();
    }

    public Message tryReadMessage(final DataInputStream in){
        try{
            return readMessage(in);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Message readMessage(final byte[] bytes) throws IOException{
        return read(bytes).message();
    }

    public Message tryReadMessage(final byte[] bytes){
        try{
            return readMessage(bytes);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Packet> readAll(final DataInputStream in) throws IOException{
        final List<Packet> packets = new ArrayList<>();
        while(in.available() > 0)
            packets.add(read(in));
        return packets;
    }

    public List<Packet> tryReadAll(final DataInputStream in){
        try{
            return readAll(in);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Packet> readAll(final byte[] bytes) throws IOException{
        try(final DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))){
            return readAll(in);
        }
    }

    public List<Packet> tryReadAll(final byte[] bytes){
        try{
            return readAll(bytes);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Message> readMessages(final DataInputStream in) throws IOException{
        final List<Message> messages = new ArrayList<>();
        while(in.available() > 0){
            messages.add(readMessage(in));
        }
        return messages;
    }

    public List<Message> tryReadMessages(final DataInputStream in){
        try{
            return readMessages(in);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Message> readMessages(final byte[] bytes) throws IOException{
        try(final DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))){
            return readMessages(in);
        }
    }

    public List<Message> tryReadMessages(final byte[] bytes){
        try{
            return readMessages(bytes);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Protocol parse(final InputStream in) throws IOException {
        final Protocol protocol = new Protocol();
        final Document doc = Jsoup.parse(in, "UTF-8", "", Parser.xmlParser());
        doc.getElementsByTag("packet").stream()
                .map(PacketStructure::parse)
                .forEach(protocol::add);
        return protocol;
    }

    public static Protocol parse(final Path path) throws IOException{
        try(final InputStream in = Files.newInputStream(path, StandardOpenOption.READ)){
            return parse(in);
        }
    }

    public static Protocol parse(final File file) throws IOException{
        return parse(file.toPath());
    }

    public static Protocol tryParse(final InputStream in){
        try{
            return parse(in);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Protocol tryParse(final Path path){
        try{
            return parse(path);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Protocol tryParse(final File file){
        try{
            return parse(file);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
