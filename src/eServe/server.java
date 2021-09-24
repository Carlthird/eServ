/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eServe;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carla
 */
public class server {

    public static void start(int p, boolean v) throws IOException {
        System.out.println("Server Started");
        ServerSocket server = new ServerSocket(p);
        while (true) {
            try (Socket socket = server.accept()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                StringBuilder requestBuilder = new StringBuilder(); // get request
                String line;
                while (!(line = reader.readLine()).isBlank()) {
                    requestBuilder.append(line + "\r\n");
                }
                String request = requestBuilder.toString();

                String[] requestsLines = request.split("\r\n"); // Parse Request
                String[] requestLine = requestsLines[0].split(" ");
                String method = requestLine[0];
                String path = requestLine[1];
                file.ver = requestLine[2];
                //String host = requestsLines[1].split(" ")[1];

                /*List<String> headers = new ArrayList<>();
                for (int h = 2; h < requestsLines.length; h++) {
                    String header = requestsLines[h];
                    headers.add(header);
                }*/
                if (v) {
                    System.out.println("connection");
                    System.out.println("Version: " + file.ver);
                    System.out.println("Path: " + path);
                    System.out.println("Method: " + method);
                }
                //String accessLog = String.format("Client %s, method %s, path %s, version %s, host %s, headers %s", socket.toString(), method, path, file.ver, host, headers.toString());
                try (OutputStream response = socket.getOutputStream()) {
                    response.write(file.response(method, path));
                }
            }
        }
    }
}
