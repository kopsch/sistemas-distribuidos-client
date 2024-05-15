package com.sistemasdistribuidos.client.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sistemasdistribuidos.client.services.ClientService;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientController {
    private final ClientService clientService;
    private String currentUserEmail;
    private String currentUserToken;
    private final Scanner scanner;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarCandidato() throws IOException {
        System.out.println("Digite o nome do candidato:");
        String nome = getNextLine();
        System.out.println("Digite o email do candidato:");
        String email = getNextLine();
        System.out.println("Digite a senha do candidato:");
        String senha = getNextLine();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "cadastrarCandidato");
        json.put("nome", nome);
        json.put("email", email);
        json.put("senha", senha);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);
    }

    public void loginCandidato() throws IOException {
        System.out.println("Digite o email:");
        String email = getNextLine();
        System.out.println("Digite a senha:");
        String senha = getNextLine();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "loginCandidato");
        json.put("email", email);
        json.put("senha", senha);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);

        ObjectMapper responseMapper = new ObjectMapper();
        JsonNode jsonResponse = responseMapper.readTree(response);
        if (jsonResponse.has("token")) {
            currentUserToken = jsonResponse.get("token").asText();
            currentUserEmail = email;
        }
    }

    public void visualizarCandidato() throws IOException {
        if (currentUserEmail == null) {
            System.out.println("Você precisa fazer login primeiro.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "visualizarCandidato");
        json.put("email", currentUserEmail);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);
    }

    public void atualizarCandidato() throws IOException {
        System.out.println("Digite o nome:");
        String nome = getNextLine();
        System.out.println("Digite o email:");
        String email = getNextLine();
        System.out.println("Digite a senha:");
        String senha = getNextLine();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "atualizarCandidato");
        json.put("nome", nome);
        json.put("email", email);
        json.put("senha", senha);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);
    }

    public void apagarCandidato() throws IOException {
        if (currentUserEmail == null) {
            System.out.println("Você precisa fazer login primeiro.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "apagarCandidato");
        json.put("email", currentUserEmail);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);
    }

    public void logout() throws IOException {
        if (currentUserToken == null) {
            System.out.println("Você não está logado.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "logout");
        json.put("token", currentUserToken);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);

        currentUserEmail = null;
        currentUserToken = null;
    }

    public void start() {
        try {
            clientService.connect();
            System.out.println("Conectado ao servidor.");

            while (true) {
                System.out.println("Escolha a operação:");
                System.out.println("1. Cadastrar Candidato");
                System.out.println("2. Login");
                System.out.println("3. Visualizar Perfil");
                System.out.println("4. Alterar Perfil");
                System.out.println("5. Apagar Perfil");
                System.out.println("6. Logout");
                System.out.println("7. Sair");
                System.out.print("Opção: ");
                String operationChoice = getNextLine();

                switch (operationChoice) {
                    case "1":
                        cadastrarCandidato();
                        break;
                    case "2":
                        loginCandidato();
                        break;
                    case "3":
                        visualizarCandidato();
                        break;
                    case "4":
                        atualizarCandidato();
                        break;
                    case "5":
                        apagarCandidato();
                        break;
                    case "6":
                        logout();
                        break;
                    case "7":
                        clientService.disconnect();
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private String getNextLine() {
        while (true) {
            try {
                if (scanner.hasNextLine()) {
                    return scanner.nextLine();
                } else {
                    throw new NoSuchElementException("No line found");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Nenhuma linha encontrada. Tente novamente:");
            }
        }
    }
}
