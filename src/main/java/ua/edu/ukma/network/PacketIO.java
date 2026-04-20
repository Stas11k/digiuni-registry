package ua.edu.ukma.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class PacketIO {
    private static final int MAX_PACKET_SIZE = 1024 * 1024; // 1 MB

    private PacketIO() {
    }

    public static void writePacket(DataOutputStream out, String json) throws IOException {
        byte[] payload = json.getBytes(StandardCharsets.UTF_8);
        out.writeInt(payload.length);
        out.write(payload);
        out.flush();
    }

    public static String readPacket(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length < 0 || length > MAX_PACKET_SIZE) {
            throw new IOException("Bad packet length: " + length);
        }
        byte[] payload = new byte[length];
        in.readFully(payload);
        return new String(payload, StandardCharsets.UTF_8);
    }
}