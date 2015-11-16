package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.NumericType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UnsignedByteType implements NumericType<Short> {

    @Override
    public int readLength() {
        return 1;
    }

    @Override
    public Class<Short> readType() {
        return Short.class;
    }

    public Short read(final DataInputStream in) throws IOException {
        return (short)(in.readByte() & 0xFF);
    }

    @Override
    public void write(final DataOutputStream out, final Number value) throws IOException {
        out.writeByte(value.byteValue());
    }
}
