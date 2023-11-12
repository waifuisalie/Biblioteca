package com.biblioteca;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class VerificadorExistencia {
    public static boolean registroJaCadastrado(String nomeArquivo, int columnIndex, String valor) {
        // Verificar se o arquivo CSV existe antes de tentar lê-lo
        verificarECriarArquivoCSV(nomeArquivo);

        List<String[]> dados = CsvHandler.lerDados(nomeArquivo);

        for (String[] registro : dados) {
            String valorCadastrado = registro[columnIndex];

            // Comparar o valor ignorando maiúsculas/minúsculas
            if (valorCadastrado.equalsIgnoreCase(valor)) {
                return true;
            }
        }
        return false;
    }

    private static void verificarECriarArquivoCSV(String nomeArquivo) {
        if (!Files.exists(Paths.get(nomeArquivo))) {
            try {
                Files.createFile(Paths.get(nomeArquivo));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao criar arquivo CSV.");
            }
        }
    }
}

