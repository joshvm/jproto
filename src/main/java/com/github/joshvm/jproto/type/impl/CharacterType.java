package com.github.joshvm.jproto.type.impl;

import com.github.joshvm.jproto.type.SimpleType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CharacterType implements SimpleType<Character> {

    @Override
    public int readLength() {
        return 2;
    }

    @Override
    public Class<Character> readType() {
        return Character.class;
    }

    @Override
    public Character read(final DataInputStream in) throws IOException {
        return in.readChar();
    }

    @Override
    public void write(final DataOutputStream out, final Character value) throws IOException {
        out.writeChar(value);
    }
}
