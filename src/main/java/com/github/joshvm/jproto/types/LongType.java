package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LongType implements Type<Long> {

    @Override
    public int length() {
        return 8;
    }

    @Override
    public Class<Long> type() {
        return Long.class;
    }

    @Override
    public Long read(final DataInputStream in) throws IOException {
        return in.readLong();
    }

    @Override
    public void write(final DataOutputStream out, final Long value) throws IOException {
        out.writeLong(value);
    }
}
