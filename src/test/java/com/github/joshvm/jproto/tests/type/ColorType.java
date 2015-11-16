package com.github.joshvm.jproto.tests.type;

import com.github.joshvm.jproto.type.SimpleType;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ColorType implements SimpleType<Color> {

    @Override
    public Class<Color> readType() {
        return Color.class;
    }

    @Override
    public int readLength() {
        return 4;
    }

    @Override
    public Color read(final DataInputStream in) throws IOException {
        return new Color(in.readInt());
    }

    @Override
    public void write(final DataOutputStream out, final Color value) throws IOException {
        out.writeInt(value.getRGB());
    }
}
