package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteType implements Type<Byte> {

    @Override
    public int length() {
        return 1;
    }

    @Override
    public Class<Byte> type() {
        return Byte.class;
    }

    @Override
    public Byte read(final DataInputStream in) throws IOException {
        return in.readByte();
    }

    @Override
    public void write(final DataOutputStream out, final Byte value) throws IOException {
        out.writeByte(value);
    }
}
