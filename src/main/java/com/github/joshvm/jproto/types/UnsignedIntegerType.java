package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UnsignedIntegerType implements Type<Long> {

    @Override
    public int length() {
        return 4;
    }

    @Override
    public Class<Long> type() {
        return Long.class;
    }

    @Override
    public Long read(final DataInputStream in) throws IOException {
        return in.readInt() & 0xFFFFFFL;
    }

    @Override
    public void write(final DataOutputStream out, final Long value) throws IOException {
        out.writeInt(value.intValue());
    }
}
