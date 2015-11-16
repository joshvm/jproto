package com.github.joshvm.jproto.type;

public interface NumericType<R extends Number> extends Type<Number, R>{

    @Override
    default Class<Number> writeType(){
        return Number.class;
    }
}
