package com.github.joshvm.jproto.value;

import com.github.joshvm.jproto.msg.Message;
import com.github.joshvm.jproto.types.Type;
import com.github.joshvm.jproto.types.Types;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayValue<T> extends Value<T> {

    public interface Reference{}

    public static class LiteralReference implements Reference{

        private final int value;

        public LiteralReference(final int value){
            this.value = value;
        }

        public int value(){
            return value;
        }
    }

    public static class VariableReference implements Reference{

        private final String name;

        public VariableReference(final String name){
            this.name = name;
        }

        public String name(){
            return name;
        }
    }

    private static final Pattern ARRAY_PATTERN = Pattern.compile("([\\w\\d_]+)\\[([\\w\\d_]+)\\]");

    private final Reference lengthRef;

    public ArrayValue(final String name, final Type<T> type, final Reference lengthRef){
        super(name, type);
        this.lengthRef = lengthRef;
    }

    public Reference lengthRef(){
        return lengthRef;
    }

    protected int length(final Message msg){
        int length = 0;
        if(lengthRef instanceof LiteralReference)
            length = ((LiteralReference)lengthRef).value;
        else if(lengthRef instanceof VariableReference)
            length = msg.get(((VariableReference)lengthRef).name);
        return length;
    }

    public void read(final DataInputStream in, final Message msg) throws IOException {
        final int length = length(msg);
        final T[] array = (T[]) Array.newInstance(type.type(), length);
        for(int i = 0; i < length; i++)
            array[i] = type.read(in);
        msg.put(name, array);
    }

    public static <T> ArrayValue<T> parse(final String name, final String text){
        final Matcher matcher = ARRAY_PATTERN.matcher(text);
        if(!matcher.matches())
            return null;
        final Type<T> type = Types.get(matcher.group(1));
        final String lenStr = matcher.group(2);
        final Reference len = lenStr.matches("\\d{1,8}") ? new LiteralReference(Integer.parseInt(lenStr)) : new VariableReference(lenStr);
        return new ArrayValue<>(name, type, len);
    }
}
