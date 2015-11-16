package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.NumericType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DoubleType implements NumericType<Double> {

    @Override
    public int readLength() {
        return 8;
    }

    @Override
    public Class<Double> readType() {
        return Double.class;
    }

    @Override
    public Double read(final DataInputStream in) throws IOException {
        return in.readDouble();
    }

    @Override
    public void write(final DataOutputStream out, final Number value) throws IOException {
        out.writeDouble(value.doubleValue());
    }
}
