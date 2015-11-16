package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.NumericType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IntegerType implements NumericType<Integer> {

    @Override
    public int readLength() {
        return 4;
    }

    @Override
    public Class<Integer> readType() {
        return Integer.class;
    }

    @Override
    public Integer read(final DataInputStream in) throws IOException {
        return in.readInt();
    }

    @Override
    public void write(final DataOutputStream out, final Number value) throws IOException {
        out.writeInt(value.intValue());
    }
}
