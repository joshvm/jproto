package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.SimpleType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BooleanType implements SimpleType<Boolean> {

    @Override
    public int readLength() {
        return 1;
    }

    @Override
    public Class<Boolean> readType() {
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
