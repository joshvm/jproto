package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IntegerType implements Type<Integer> {

    @Override
    public int length() {
        return 4;
    }

    @Override
    public Class<Integer> type() {
        return Integer.class;
    }

    @Override
    public Integer read(final DataInputStream in) throws IOException {
        return in.readInt();
    }

    @Override
    public void write(final DataOutputStream out, final Integer value) throws IOException {
        out.writeInt(value);
    }
}
