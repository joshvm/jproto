package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.NumericType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UnsignedShortType implements NumericType<Integer> {

    @Override
    public int readLength() {
        return 2;
    }

    @Override
    public Class<Integer> readType() {
        return Integer.class;
    }

    @Override
    public Integer read(final DataInputStream in) throws IOException {
        return in.readShort() & 0xFFFF;
    }

    @Override
    public void write(final DataOutputStream out, final Number value) throws IOException {
        out.writeShort(value.shortValue());
    }
}
