package com.github.joshvm.jproto.type;

public interface SimpleType<T> extends Type<T, T>{

    @Override
    default Class<T> writeType(){
        return readType();
    }
}
