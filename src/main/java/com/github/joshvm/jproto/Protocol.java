package com.github.joshvm.jproto;

import com.github.joshvm.jproto.pkt.Packet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class Protocol {

    private final Map<Integer, PacketStructure> map;

    public Protocol(){
        map = new HashMap<>();
    }

    public Map<Integer, PacketStructure> map(){
        return map;
    }

    public void add(final PacketStructure struct){
        map.put(struct.opcode(), struct);
    }

    public PacketStructure get(final int opcode){
        return map.get(opcode);
    }

    public Packet read(final int opcode, final DataInputStream in) throws IOException{
        return get(opcode).read(in);
    }

    public Packet tryRead(final int opcode, final DataInputStream in){
        return get(opcode).tryRead(in);
    }

    public static Protocol parse(final InputStream in) throws IOException {
        final Protocol protocol = new Protocol();
        final Document doc = Jsoup.parse(in, "UTF-8", "", Parser.xmlParser());
        doc.getElementsByTag("packet").stream()
                .map(PacketStructure::parse)
                .forEach(protocol::add);
        return protocol;
    }

    public static Protocol parse(final Path path) throws IOException{
        try(final InputStream in = Files.newInputStream(path, StandardOpenOption.READ)){
            return parse(in);
        }
    }

    public static Protocol parse(final File file) throws IOException{
        return parse(file.toPath());
    }
}
