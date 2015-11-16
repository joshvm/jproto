package com.github.joshvm.jproto.value;

import com.github.joshvm.jproto.msg.Message;
import com.github.joshvm.jproto.type.Type;
import com.github.joshvm.jproto.type.Types;
import org.jsoup.nodes.Element;

import java.io.DataInputStream;
import java.io.IOException;

public class Value<W, R extends W> {

    protected final String name;
    protected final Type<W, R> type;

    public Value(final String name, final Type<W, R> type){
        this.name = name;
        this.type = type;
    }

    public String name(){
        return name;
    }

    public Type<W, R> type(){
        return type;
    }

    public void read(final DataInputStream in, final Message msg) throws IOException{
        msg.put(name, type.read(in));
    }

    public boolean tryRead(final DataInputStream in, final Message msg){
        try{
            read(in, msg);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static <W, R extends W> Value<W, R> parse(final Element e){
        final String name = e.ownText();
        final String typeStr = e.attr("type");
        final Type<W, R> type = Types.get(typeStr);
        return type != null ? new Value<>(name, type) : ArrayValue.parse(name, typeStr);
    }

}
