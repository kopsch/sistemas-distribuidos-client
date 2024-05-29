package com.sistemasdistribuidos.client.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientService {
    private final String serverHostname;
    private final int serverPort;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientService(String serverHostname, int serverPort) {
        this.serverHostname = serverHostname;
        this.serverPort = serverPort;
    }

    public void connect() throws IOException {
        this.socket = new Socket(serverHostname, serverPort);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void disconnect() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }

    public void sendRequest(String request) {
        System.out.println("Enviado ao Servidor:" + request);
        out.println(request);
    }

    public String getResponse() throws IOException {
        return in.readLine();
    }
}
