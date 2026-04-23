package ua.edu.ukma.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
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
        try (socket;
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            logger.info("Handling client: {}", socket.getRemoteSocketAddress());
            socket.setSoTimeout(60_000);

            while (true) {
                String requestJson = PacketIO.readPacket(in);
                logger.info("Received from {}: {}", socket.getRemoteSocketAddress(), requestJson);
                Request request = mapper.readValue(requestJson, Request.class);
                Response response = processor.process(request, session);
                String responseJson = mapper.writeValueAsString(response);
                logger.info("Sending to {}: {}", socket.getRemoteSocketAddress(), responseJson);
                PacketIO.writePacket(out, responseJson);
            }
        } catch (EOFException e) {
            logger.info("Client disconnected: {}", socket.getRemoteSocketAddress());
        } catch (IOException e) {
            logger.error("I/O error with client {}: {}", socket.getRemoteSocketAddress(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error with client {}", socket.getRemoteSocketAddress(), e);
        }
    }
}