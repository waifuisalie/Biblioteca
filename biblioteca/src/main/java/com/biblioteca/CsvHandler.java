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

    public static List<String[]> lerDados(String nomeArquivo) {
        try (CSVReader reader = new CSVReader(new FileReader(nomeArquivo))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace(); // Trate ou registre exceções conforme necessário
        }
        return null;
    }
}

