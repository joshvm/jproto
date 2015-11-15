package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FloatType implements Type<Float> {

    @Override
    public int length() {
        return 4;
    }

    @Override
    public Class<Float> type() {
        return Float.class;
    }

    @Override
    public Float read(final DataInputStream in) throws IOException {
        return in.readFloat();
    }

    @Override
    public void write(final DataOutputStream out, final Float value) throws IOException {
        out.writeFloat(value);
    }
}
