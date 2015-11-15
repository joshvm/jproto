package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DoubleType implements Type<Double> {

    @Override
    public int length() {
        return 8;
    }

    @Override
    public Class<Double> type() {
        return Double.class;
    }

    @Override
    public Double read(final DataInputStream in) throws IOException {
        return in.readDouble();
    }

    @Override
    public void write(final DataOutputStream out, final Double value) throws IOException {
        out.writeDouble(value);
    }
}
