package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.NumericType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UnsignedIntegerType implements NumericType<Long> {

    @Override
    public int readLength() {
        return 4;
    }

    @Override
    public Class<Long> readType() {
        return Long.class;
    }

    @Override
    public Long read(final DataInputStream in) throws IOException {
        return in.readInt() & 0xFFFFFFL;
    }

    @Override
    public void write(final DataOutputStream out, final Number value) throws IOException {
        out.writeInt(value.intValue());
    }
}
