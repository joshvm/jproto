package com.github.joshvm.jproto.pkt;

import com.github.joshvm.jproto.buffer.Buffer;
import com.github.joshvm.jproto.type.NumericType;
import com.github.joshvm.jproto.util.ProtocolConstants;

public class PacketBuffer extends Buffer implements ProtocolConstants{

    private final NumericType<? extends Number> opcodeType;
    private final Number opcode;
    private final int length;

    public PacketBuffer(final NumericType<? extends Number> opcodeType, final Number opcode, final int length){
        this.opcodeType = opcodeType;
        this.opcode = opcode;
        this.length = length;
    }

    public byte[] bytes(final boolean closePayload){
        final Buffer out = Buffer.buffer();
        out.tryWrite(opcodeType, opcode);
        switch(length){
            case VARIABLE_BYTE:
                out.tryWrite("ubyte", size());
                break;
            case VARIABLE_SHORT:
                out.tryWrite("ushort", size());
                break;
            case VARIABLE_INT:
                out.tryWrite("uint", size());
                break;
        }
        out.tryWrite(baos.toByteArray());
        return out.bytes();
    }

    public static PacketBuffer buffer(final NumericType<? extends Number> opcodeType, final Number opcode, final int length){
        return new PacketBuffer(opcodeType, opcode, length);
    }

    public static PacketBuffer varByteBuilder(final NumericType<? extends Number> opcodeType, final Number opcode){
        return buffer(opcodeType, opcode, VARIABLE_BYTE);
    }

    public static PacketBuffer varShortBuilder(final NumericType<? extends Number> opcodeType, final Number opcode){
        return buffer(opcodeType, opcode, VARIABLE_SHORT);
    }

    public static PacketBuffer varIntBuilder(final NumericType<? extends Number> opcodeType, final Number opcode){
        return buffer(opcodeType, opcode, VARIABLE_INT);
    }

}
