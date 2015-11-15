package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UnsignedByteType implements Type<Short> {

    @Override
    public int length() {
        return 1;
    }

    @Override
    public Class<Short> type() {
        return Short.class;
    }

    public Short read(final DataInputStream in) throws IOException {
        return (short)(in.readByte() & 0xFF);
    }

    @Override
    public void write(final DataOutputStream out, final Short value) throws IOException {
        out.writeByte(value.byteValue());
    }
}
