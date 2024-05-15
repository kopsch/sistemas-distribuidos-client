package com.sistemasdistribuidos.client;

import com.sistemasdistribuidos.client.controllers.ClientController;
import com.sistemasdistribuidos.client.services.ClientService;

import java.util.Scanner;

public class Client {
    protected static int defaultPort = 22222;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do servidor (o padrão é 127.0.0.1): ");
        String serverHostname = scanner.nextLine();

        if (serverHostname.isEmpty()) {
            serverHostname = "127.0.0.1";
        }

        System.out.print("Digite o número da porta do servidor (o padrão é 22222): ");
        Integer serverPort;

        try {
            serverPort = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Número de porta inválido. Usando a porta padrão 22222.");
            serverPort = defaultPort;
        }

        ClientService clientService = new ClientService(serverHostname, serverPort);
        ClientController clientController = new ClientController(clientService);
        clientController.start();
    }
}
