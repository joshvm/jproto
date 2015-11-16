package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.SimpleType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShortStringType implements SimpleType<String> {

    @Override
    public int readLength() {
        return VARIABLE_SHORT;
    }

    @Override
    public Class<String> readType() {
        return String.class;
    }

    @Override
    public String read(final DataInputStream in) throws IOException {
        final int length = in.readShort();
        final StringBuilder bldr = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            bldr.append((char)in.readByte());
        return bldr.toString();
    }

    @Override
    public void write(final DataOutputStream out, final String value) throws IOException {
        out.writeShort(value.length());
        for(final char c : value.toCharArray())
            out.writeByte((byte)c);
    }
}
