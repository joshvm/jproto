package com.github.joshvm.jproto;

import com.github.joshvm.jproto.pkt.Packet;
import com.github.joshvm.jproto.util.ProtocolConstants;
import com.github.joshvm.jproto.value.Value;
import org.jsoup.nodes.Element;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacketStructure implements ProtocolConstants{

    private final int opcode;
    private final int length;

    private final List<Value> values;

    public PacketStructure(final int opcode, final int length, final List<Value> values){
        this.opcode = opcode;
        this.length = length;
        this.values = values;
    }

    public PacketStructure(final int opcode, final int length){
        this(opcode, length, new ArrayList<>());
    }

    public int opcode(){
        return opcode;
    }

    public int length(){
        return length;
    }

    public List<Value> values(){
        return values;
    }

    public Packet read(final DataInputStream in) throws IOException {
        int length = this.length;
        switch(length){
            case VARIABLE_BYTE:
                length = in.readInt();
                break;
            case VARIABLE_SHORT:
                length = in.readShort();
                break;
            case VARIABLE_MEDIUM:
                length = ((in.readByte() << 16) & 0xFF) + ((in.readByte() << 8) & 0xFF) + (in.readByte() + 0xFF);
                break;
            case VARIABLE_INTEGER:
                length = in.readInt();
                break;
        }
        final byte[] payload = new byte[length];
        in.readFully(payload);
        return new Packet(this, length, payload);
    }

    public Packet tryRead(final DataInputStream in){
        try{
            return read(in);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static int calculateLength(final List<Value> values){
        int min = 0;
        int sum = 0;
        for(final Value v : values){
            final int length = v.type().length();
            sum += length;
            if(length < min)
                min = length;
        }
        return min < 0 ? min : sum;
    }

    public static int parseLength(final String str){
        return Integer.parseInt(str.replaceAll("var_?byte", "-1")
                .replaceAll("var_?short", "-2")
                .replaceAll("var_?(med|medium)", "-3")
                .replaceAll("var_?(int|integer)", "-4"));
    }

    public static PacketStructure parse(final Element e){
        final int opcode = Integer.parseInt(e.attr("opcode"));
        int length = Integer.MIN_VALUE;
        if(e.hasAttr("length"))
            length = parseLength(e.attr("length"));
        final List<Value> values = e.getElementsByTag("value").stream()
                .map(Value::parse)
                .collect(Collectors.toList());
        if(length == Integer.MIN_VALUE)
            length = calculateLength(values);
        return new PacketStructure(opcode, length, values);
    }
}
