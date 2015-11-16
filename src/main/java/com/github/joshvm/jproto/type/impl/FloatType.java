package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.NumericType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FloatType implements NumericType<Float> {

    @Override
    public int readLength() {
        return 4;
    }

    @Override
    public Class<Float> readType() {
        return Float.class;
    }

    @Override
    public Float read(final DataInputStream in) throws IOException {
        return in.readFloat();
    }

    @Override
    public void write(final DataOutputStream out, final Number value) throws IOException {
        out.writeFloat(value.floatValue());
    }
}
