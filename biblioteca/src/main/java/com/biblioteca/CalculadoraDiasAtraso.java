package com.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CalculadoraDiasAtraso {

    public static int calcularDiasAtraso(String dataDevolucao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataDevolucaoFormatada = LocalDate.parse(dataDevolucao, formatter);
        LocalDate dataAtual = LocalDate.now();

        // Calcular a diferença em dias entre a data atual e a data de devolução
        long diasAtraso = (ChronoUnit.DAYS.between(dataDevolucaoFormatada, dataAtual));
        


        // Certificar-se de que a diferença seja um número positivo
        return Math.max(0, (int) diasAtraso);
    }
}
