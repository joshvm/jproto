package com.github.joshvm.jproto.tests;

import com.github.joshvm.jproto.Protocol;
import com.github.joshvm.jproto.buffer.Buffer;
import com.github.joshvm.jproto.msg.Message;
import com.github.joshvm.jproto.type.Types;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Color;
import java.nio.file.Paths;
import java.util.Iterator;

public class ProtocolTest {

    private static Protocol protocol;

    @BeforeClass
    public static void init() {
        Types.tryAdd(Paths.get("types.xml"));
        protocol = Protocol.tryParse(Paths.get("test.xml"));
    }

    @Test
    public void test1(){
        final int id = 200;
        final Iterator<Message> messages = protocol.tryReadMessages(buildTest1Buffer(id)).iterator();
        Message m = messages.next();
        Assert.assertEquals(id, m.getInt("person.id"));
        m = messages.next();
        Assert.assertEquals(id, m.getInt("person.id"));
        m = messages.next();
        Assert.assertEquals("address line 1", m.getString("person.address1"));
        Assert.assertEquals("address line 2", m.getString("person.address2"));
        m = messages.next();
        Assert.assertEquals("email@email.email", m.getString("person.email"));
        m = messages.next();
        Assert.assertEquals(50, m.getShort("person.age"));
        m = messages.next();
        Assert.assertEquals(Color.PINK, m.get("person.color"));
    }

    public static byte[] buildTest1Buffer(final int id){
        return Buffer.buffer()
                .tryWrite(addPerson(id))
                .tryWrite(deletePerson(id))
                .tryWrite(setAddress(id, "address line 1", "address line 2"))
                .tryWrite(setEmail(id, "email@email.email"))
                .tryWrite(setAge(id, 50))
                .tryWrite(setColor(id, Color.PINK))
                .bytes();
    }

    public static byte[] addPerson(final int id){
        return protocol.newBuffer(1)
                .tryWrite("ushort", id)
                .bytes();
    }

    public static byte[] deletePerson(final int id){
        return protocol.newBuffer(2)
                .tryWrite("ushort", id)
                .bytes();
    }

    public static byte[] setAddress(final int id, final String address1, final String address2){
        return protocol.newVarShortPacketBuffer(3)
                .tryWrite("ushort", id)
                .tryWrite("byte_string", address1)
                .tryWrite("byte_string", address2)
                .bytes();
    }

    public static byte[] setEmail(final int id, final String email){
        return protocol.newVarShortPacketBuffer(4)
                .tryWrite("ushort", id)
                .tryWrite("byte_string", email)
                .bytes();
    }

    public static byte[] setAge(final int id, final int age){
        return protocol.newBuffer(5)
                .tryWrite("ushort", id)
                .tryWrite("ubyte", age)
                .bytes();
    }

    public static byte[] setColor(final int id, final Color color){
        return protocol.newBuffer(6)
                .tryWrite("ushort", id)
                .tryWrite("color", color)
                .bytes();
    }
}
