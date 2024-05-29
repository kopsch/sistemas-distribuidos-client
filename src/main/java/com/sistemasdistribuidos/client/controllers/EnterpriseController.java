package com.sistemasdistribuidos.client.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sistemasdistribuidos.client.services.ClientService;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EnterpriseController {
    private final ClientService clientService;
    private String currentEnterpriseEmail;
    private String currentEnterpriseToken;
    private final Scanner scanner;

    public EnterpriseController(ClientService clientService) {
        this.clientService = clientService;
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarEmpresa() throws IOException {
        System.out.println("Digite a razão social da empresa:");
        String razaoSocial = getNextLine();
        System.out.println("Digite o email da empresa:");
        String email = getNextLine();
        System.out.println("Digite o CNPJ da empresa:");
        String cnpj = getNextLine();
        System.out.println("Digite a descrição da empresa:");
        String descricao = getNextLine();
        System.out.println("Digite o ramo da empresa:");
        String ramo = getNextLine();
        System.out.println("Digite a senha da empresa:");
        String senha = getNextLine();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "cadastrarEmpresa");
        json.put("razaoSocial", razaoSocial);
        json.put("email", email);
        json.put("cnpj", cnpj);
        json.put("descricao", descricao);
        json.put("ramo", ramo);
        json.put("senha", senha);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);
    }

    public void loginEmpresa() throws IOException {
        System.out.println("Digite o email:");
        String email = getNextLine();
        System.out.println("Digite a senha:");
        String senha = getNextLine();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "loginEmpresa");
        json.put("email", email);
        json.put("senha", senha);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);

        ObjectMapper responseMapper = new ObjectMapper();
        JsonNode jsonResponse = responseMapper.readTree(response);
        if (jsonResponse.has("token")) {
            currentEnterpriseToken = jsonResponse.get("token").asText();
            currentEnterpriseEmail = email;
        }
    }

    public void visualizarEmpresa() throws IOException {
        if (currentEnterpriseEmail == null) {
            System.out.println("Você precisa fazer login primeiro.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "visualizarEmpresa");
        json.put("email", currentEnterpriseEmail);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);
    }

    public void atualizarEmpresa() throws IOException {
        System.out.println("Digite a razão social:");
        String razaoSocial = getNextLine();
        System.out.println("Digite o email:");
        String email = getNextLine();
        System.out.println("Digite o CNPJ:");
        String cnpj = getNextLine();
        System.out.println("Digite a descrição:");
        String descricao = getNextLine();
        System.out.println("Digite o ramo:");
        String ramo = getNextLine();
        System.out.println("Digite a senha:");
        String senha = getNextLine();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "atualizarEmpresa");
        json.put("razaoSocial", razaoSocial);
        json.put("email", email);
        json.put("cnpj", cnpj);
        json.put("descricao", descricao);
        json.put("ramo", ramo);
        json.put("senha", senha);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);
    }

    public void apagarEmpresa() throws IOException {
        if (currentEnterpriseEmail == null) {
            System.out.println("Você precisa fazer login primeiro.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "apagarEmpresa");
        json.put("email", currentEnterpriseEmail);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);
    }

    public void logout() throws IOException {
        if (currentEnterpriseToken == null) {
            System.out.println("Você não está logado.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("operacao", "logout");
        json.put("token", currentEnterpriseToken);

        clientService.sendRequest(json.toString());
        String response = clientService.getResponse();
        System.out.println("Resposta do servidor: " + response);

        currentEnterpriseEmail = null;
        currentEnterpriseToken = null;
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
