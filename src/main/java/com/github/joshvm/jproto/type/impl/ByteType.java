package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.NumericType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteType implements NumericType<Byte> {

    @Override
    public int readLength() {
        return 1;
    }

    @Override
    public Class<Byte> readType() {
        return Byte.class;
    }

    @Override
    public Byte read(final DataInputStream in) throws IOException {
        return in.readByte();
    }

    @Override
    public void write(final DataOutputStream out, final Number value) throws IOException {
        out.writeByte(value.byteValue());
    }
}
