package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BooleanType implements Type<Boolean> {

    @Override
    public int length() {
        return 1;
    }

    @Override
    public Class<Boolean> type() {
        return Boolean.class;
    }

    @Override
    public Boolean read(final DataInputStream in) throws IOException {
        return in.readByte() != 0;
    }

    @Override
    public void write(final DataOutputStream out, final Boolean value) throws IOException {
        out.writeBoolean(value);
    }
}
