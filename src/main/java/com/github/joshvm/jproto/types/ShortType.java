package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShortType implements Type<Short> {

    @Override
    public int length() {
        return 2;
    }

    @Override
    public Class<Short> type() {
        return Short.class;
    }

    @Override
    public Short read(final DataInputStream in) throws IOException {
        return in.readShort();
    }

    @Override
    public void write(final DataOutputStream out, final Short value) throws IOException {
        out.writeShort(value);
    }
}
