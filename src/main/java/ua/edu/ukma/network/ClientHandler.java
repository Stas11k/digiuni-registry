package ua.edu.ukma.network;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final CommandProcessor processor;
    private final ObjectMapper mapper;
    private final SessionContext session = new SessionContext();

    public ClientHandler(Socket socket, CommandProcessor processor, ObjectMapper mapper) {
        this.socket = socket;
        this.processor = processor;
        this.mapper = mapper;
    }

    @Override
    public void run() {
        try (socket; DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            System.out.println("Handling client: " + socket.getRemoteSocketAddress());
            socket.setSoTimeout(60_000);

            while (true) {
                String requestJson = PacketIO.readPacket(in);
                System.out.println("Received: " + requestJson);
                Request request = mapper.readValue(requestJson, Request.class);
                Response response = processor.process(request, session);
                String responseJson = mapper.writeValueAsString(response);
                System.out.println("Sending: " + responseJson);
                PacketIO.writePacket(out, responseJson);
            }
        } catch (EOFException e) {
            System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
        } catch (IOException e) {
            System.out.println("I/O error with client " + socket.getRemoteSocketAddress() + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error with client " + socket.getRemoteSocketAddress());
            e.printStackTrace();
        }
    }
}