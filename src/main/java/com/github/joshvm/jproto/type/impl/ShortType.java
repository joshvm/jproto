package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.NumericType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShortType implements NumericType<Short> {

    @Override
    public int readLength() {
        return 2;
    }

    @Override
    public Class<Short> readType() {
        return Short.class;
    }

    @Override
    public Short read(final DataInputStream in) throws IOException {
        return in.readShort();
    }

    @Override
    public void write(final DataOutputStream out, final Number value) throws IOException {
        out.writeShort(value.shortValue());
    }
}
