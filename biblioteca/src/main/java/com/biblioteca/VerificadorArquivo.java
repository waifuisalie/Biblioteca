package com.biblioteca;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class VerificadorArquivo {

    public static void main(String[] args) {
        String nomeArquivo = "livros.csv";

        if (verificarExistenciaArquivo(nomeArquivo)) {
            System.out.println("O arquivo já existe.");
            // Adicione aqui a lógica específica, se necessário, quando o arquivo já existir.
        } else {
            System.out.println("O arquivo não existe. Será criado.");
            // Adicione aqui a lógica específica para criar o arquivo, se ele não existir.
            criarArquivo(nomeArquivo);
        }
    }

    private static boolean verificarExistenciaArquivo(String nomeArquivo) {
        Path path = Paths.get(nomeArquivo);
        return Files.exists(path);
    }

    private static void criarArquivo(String nomeArquivo) {
        Path path = Paths.get(nomeArquivo);
        try {
            Files.createFile(path);
            System.out.println("Arquivo criado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao criar o arquivo.");
        }
    }
}
