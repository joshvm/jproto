package com.github.joshvm.jproto.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CharacterType implements Type<Character> {

    @Override
    public int length() {
        return 2;
    }

    @Override
    public Class<Character> type() {
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
