import java.util.Map;
import java.util.Scanner;

public class Principal {

    package com.meuapp;

import com.meuapp.api.ApiClient;
import com.meuapp.models.ExchangeRateResponse;

import java.util.Map;
import java.util.Scanner;

    public class Main {
        public static <ExchangeRateResponse> void main(String[] args) {
            com.meuapp.api.ApiClient api = new com.meuapp.api.ApiClient();
            Scanner scanner = new Scanner(System.in);
            String[] moedas = {"USD", "BRL", "ARS", "BOB", "CLP", "COP"};

            while (true) {
                System.out.println("\nConversor de Moedas");
                for (int i = 0; i < moedas.length; i++) {
                    System.out.println((i + 1) + " - " + moedas[i]);
                }
                System.out.println("0 - Sair");
                System.out.print("Escolha a moeda de origem: ");
                int origemIndex = scanner.nextInt();
                if (origemIndex == 0) break;

                System.out.print("Escolha a moeda de destino: ");
                int destinoIndex = scanner.nextInt();
                if (destinoIndex == 0) break;

                System.out.print("Digite o valor: ");
                double valor = scanner.nextDouble();

                String origem = moedas[origemIndex - 1];
                String destino = moedas[destinoIndex - 1];

                ExchangeRateResponse resposta = api.getRates(origem);
                Map<String, Double> taxas = resposta.clone();
                double taxa = taxas.get(destino);
                double convertido = valor * taxa;

                System.out.printf("Resultado: %.2f %s = %.2f %s\n", valor, origem, convertido, destino);
            }

            System.out.println("Programa encerrado.");
        }
    }
}
