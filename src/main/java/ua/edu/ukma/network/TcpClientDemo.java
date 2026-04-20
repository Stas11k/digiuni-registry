package ua.edu.ukma.network;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Map;

public class TcpClientDemo {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try (Socket socket = new Socket("localhost", 5555);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            send(out, in, mapper, new Request("LOGIN", "admin", "admin", null, Map.of()));
            System.out.println("\nFACULTY LIST");
            send(out, in, mapper, new Request("FACULTY_LIST", null, null, null, Map.of()));
            System.out.println("\nDEPARTMENT LIST");
            send(out, in, mapper, new Request("DEPARTMENT_LIST", null, null, null, Map.of()));
            System.out.println("\nSPECIALTY LIST");
            send(out, in, mapper, new Request("SPECIALTY_LIST", null, null, null, Map.of()));
            System.out.println("\nSTUDENT LIST");
            send(out, in, mapper, new Request("STUDENT_LIST", null, null, null, Map.of()));
            System.out.println("\nTEACHER LIST");
            send(out, in, mapper, new Request("TEACHER_LIST", null, null, null, Map.of()));
            System.out.println("\nFIND STUDENT BY NAME");
            send(out, in, mapper, new Request("STUDENT_FIND_BY_NAME", null, null, null, Map.of("query", "Koval")));
            System.out.println("\nFIND TEACHER BY NAME");
            send(out, in, mapper, new Request("TEACHER_FIND_BY_NAME", null, null, null, Map.of("query", "Petrenko")));
            System.out.println("\nDELETE STUDENT (example id=2)");
            send(out, in, mapper, new Request("STUDENT_DELETE", null, null, null, Map.of("id", "2")));
            System.out.println("\nDELETE TEACHER (example id=1)");
            send(out, in, mapper, new Request("TEACHER_DELETE", null, null, null, Map.of("id", "1")));
            System.out.println("\nDELETE SPECIALTY (example id=1)");
            send(out, in, mapper, new Request("SPECIALTY_DELETE", null, null, null, Map.of("id", "1")));
            System.out.println("\nDELETE DEPARTMENT (example id=7)");
            send(out, in, mapper, new Request("DEPARTMENT_DELETE", null, null, null, Map.of("id", "7")));
            System.out.println("\nDELETE FACULTY (example id=7)");
            send(out, in, mapper, new Request("FACULTY_DELETE", null, null, null, Map.of("id", "7")));
            System.out.println("\nFACULTY LIST AFTER DELETE");
            send(out, in, mapper, new Request("FACULTY_LIST", null, null, null, Map.of()));
            System.out.println("\nDEPARTMENT LIST AFTER DELETE");
            send(out, in, mapper, new Request("DEPARTMENT_LIST", null, null, null, Map.of()));
            System.out.println("\nSPECIALTY LIST AFTER DELETE");
            send(out, in, mapper, new Request("SPECIALTY_LIST", null, null, null, Map.of()));
            System.out.println("\nSTUDENT LIST AFTER DELETE");
            send(out, in, mapper, new Request("STUDENT_LIST", null, null, null, Map.of()));
            System.out.println("\nTEACHER LIST AFTER DELETE");
            send(out, in, mapper, new Request("TEACHER_LIST", null, null, null, Map.of()));
            send(out, in, mapper, new Request("LOGOUT", null, null, null, Map.of()));
        } catch (ConnectException e) {
            System.out.println("Cannot connect to server. Start ServerMain first.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void send(DataOutputStream out, DataInputStream in, ObjectMapper mapper, Request request) throws Exception {
        PacketIO.writePacket(out, mapper.writeValueAsString(request));
        System.out.println(PacketIO.readPacket(in));
    }
}