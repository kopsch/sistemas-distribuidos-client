package com.sistemasdistribuidos.client.controllers;

import com.sistemasdistribuidos.client.services.ClientService;

import java.io.IOException;
import java.util.Scanner;

public class ClientController {
    private final ClientService clientService;
    private final Scanner scanner;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        try {
            clientService.connect();
            System.out.println("Conectado ao servidor.");

            while (true) {
                System.out.println("Escolha o tipo de usuário:");
                System.out.println("1. Candidato");
                System.out.println("2. Empresa");
                System.out.println("3. Sair");
                System.out.print("Opção: ");
                String userTypeChoice = getNextLine();

                switch (userTypeChoice) {
                    case "1":
                        startCandidato();
                        break;
                    case "2":
                        startEmpresa();
                        break;
                    case "3":
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

    private void startCandidato() throws IOException {
        CandidateController candidateController = new CandidateController(clientService);
        while (true) {
            System.out.println("Escolha a operação:");
            System.out.println("1. Cadastrar Candidato");
            System.out.println("2. Login");
            System.out.println("3. Visualizar Perfil");
            System.out.println("4. Alterar Perfil");
            System.out.println("5. Apagar Perfil");
            System.out.println("6. Logout");
            System.out.println("7. Voltar");
            System.out.print("Opção: ");
            String operationChoice = getNextLine();

            switch (operationChoice) {
                case "1":
                    candidateController.cadastrarCandidato();
                    break;
                case "2":
                    candidateController.loginCandidato();
                    break;
                case "3":
                    candidateController.visualizarCandidato();
                    break;
                case "4":
                    candidateController.atualizarCandidato();
                    break;
                case "5":
                    candidateController.apagarCandidato();
                    break;
                case "6":
                    candidateController.logout();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private void startEmpresa() throws IOException {
        EnterpriseController enterpriseController = new EnterpriseController(clientService);
        while (true) {
            System.out.println("Escolha a operação:");
            System.out.println("1. Cadastrar Empresa");
            System.out.println("2. Login");
            System.out.println("3. Visualizar Perfil");
            System.out.println("4. Alterar Perfil");
            System.out.println("5. Apagar Perfil");
            System.out.println("6. Logout");
            System.out.println("7. Voltar");
            System.out.print("Opção: ");
            String operationChoice = getNextLine();

            switch (operationChoice) {
                case "1":
                    enterpriseController.cadastrarEmpresa();
                    break;
                case "2":
                    enterpriseController.loginEmpresa();
                    break;
                case "3":
                    enterpriseController.visualizarEmpresa();
                    break;
                case "4":
                    enterpriseController.atualizarEmpresa();
                    break;
                case "5":
                    enterpriseController.apagarEmpresa();
                    break;
                case "6":
                    enterpriseController.logout();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private String getNextLine() {
        while (true) {
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            } else {
                System.out.println("Nenhuma linha encontrada. Tente novamente:");
            }
        }
    }
}
