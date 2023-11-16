package com.biblioteca;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvHandler {
    public static void escreverDados(String nomeArquivo, List<String[]> dados) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(nomeArquivo, true))) {
            writer.writeAll(dados);
        } catch (IOException e) {
            e.printStackTrace(); // Trate ou registre exceções conforme necessário
        }
    }

    // Método sobrecarregado para aceitar uma lista de objetos e um conversor para converter objetos em strings
    public static <T> void escreverDados(String nomeArquivo, List<T> objetos, ConversorObjetoParaStrings<T> conversor) {
        List<String[]> dados = converterObjetosParaDados(objetos, conversor);
        escreverDados(nomeArquivo, dados);
    }

    private static <T> List<String[]> converterObjetosParaDados(List<T> objetos, ConversorObjetoParaStrings<T> conversor) {
        return conversor.converterParaDados(objetos);
    }

    // Interface funcional para um conversor de objeto para strings
    public interface ConversorObjetoParaStrings<T> {
        List<String[]> converterParaDados(List<T> objetos);
    }

    public static List<String[]> lerDados(String nomeArquivo) {
        try (CSVReader reader = new CSVReader(new FileReader(nomeArquivo))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace(); // Trate ou registre exceções conforme necessário
        }
        return null;
    }

    
    public static void escreverLinha(String nomeArquivo, String linha) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String obterTipoMembro(String nomeMembro, String nomeArquivo) {
        List<String[]> dados = lerDados(nomeArquivo);

        for (String[] linha : dados) {
            if (linha.length > 1 && nomeMembro.equalsIgnoreCase(linha[0])) {
                return linha[2]; // Assume que o tipo de membro está na terceira coluna
            }
        }
        return null; // Membro não encontrado ou tipo de membro não especificado
    }

    public static void atualizarLinha(String nomeArquivo, String chave, String novaLinha) {
        try {
            List<String[]> linhas = lerDados(nomeArquivo);
            if (linhas != null) {
                for (int i = 0; i < linhas.size(); i++) {
                    String[] linha = linhas.get(i);
                    if (linha.length > 1 && linha[1].equalsIgnoreCase(chave)) {
                        // Substituir a linha antiga pela nova
                        linhas.set(i, novaLinha.split(","));
                        break;
                    }
                }
                escreverLinhas(nomeArquivo, linhas);
            } else {
                System.out.println("Lista de linhas nula ao tentar atualizar.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Trate ou registre exceções conforme necessário
        }
    }
    
    
    public static void escreverLinhas(String nomeArquivo, List<String[]> linhas) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(nomeArquivo))) {
            writer.writeAll(linhas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Livro> lerLivros(String nomeArquivo) {
        List<Livro> livros = new ArrayList<>();

        List<String[]> dados = lerDados(nomeArquivo);
        for (String[] linha : dados) {
            Livro livro = new Livro(
                linha[0],  // Título
                linha[1],  // Código
                linha[2],  // Autor
            Integer.parseInt(linha[3])  // Ano
            );
        livro.setDisponivel(Boolean.parseBoolean(linha[4]));
        livros.add(livro);
        }

        return livros;
    }
    public static List<String[]> verificarECarregarArquivoCSV(String nomeArquivo) {
        if (verificarExistenciaArquivo(nomeArquivo)) {
            return lerDados(nomeArquivo);
        } else {
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
            return Collections.emptyList();
        }
    }

    public static boolean verificarExistenciaArquivo(String nomeArquivo) {
        Path path = Paths.get(nomeArquivo);
        return Files.exists(path);
    }

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
    
    // n sei aonde pode ser usado
    public static void verificarECriarArquivoCSV(String nomeArquivo) {
        if (verificarExistenciaArquivo(nomeArquivo)) {
            System.out.println("O arquivo já existe.");
            // Adicione aqui a lógica específica, se necessário, quando o arquivo já existir.
        } else {
            System.out.println("O arquivo não existe. Será criado.");
            // Adicione aqui a lógica específica para criar o arquivo, se ele não existir.
            criarArquivo(nomeArquivo);
        }
    }


    public static void criarArquivo(String nomeArquivo) {
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