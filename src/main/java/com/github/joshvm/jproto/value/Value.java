package com.github.joshvm.jproto.value;

import com.github.joshvm.jproto.msg.Message;
import com.github.joshvm.jproto.types.Type;
import com.github.joshvm.jproto.types.Types;
import org.jsoup.nodes.Element;

import java.io.DataInputStream;
import java.io.IOException;

public class Value<T> {

    protected final String name;
    protected final Type<T> type;

    public Value(final String name, final Type<T> type){
        this.name = name;
        this.type = type;
    }

    public String name(){
        return name;
    }

    public Type<T> type(){
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

    public static <T> Value<T> parse(final Element e){
        final String name = e.ownText();
        final String typeStr = e.attr("type");
        final Type<T> type = Types.get(typeStr);
        return type != null ? new Value<>(name, type) : ArrayValue.parse(name, typeStr);
    }

}
