package com.biblioteca;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
}

