package com.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CalculadoraDiasAtraso {

    // calcula dias de atraso com base na data de devolução
    public static int calcularDiasAtraso(String dataDevolucao) {
        // formatar a data de devolução
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataDevolucaoFormatada = LocalDate.parse(dataDevolucao, formatter);

        // obter data atual
        LocalDate dataAtual = LocalDate.now();

        // Calcula a diferença em dias entre a data atual e a data de devolução
        long diasAtraso = (ChronoUnit.DAYS.between(dataDevolucaoFormatada, dataAtual));
        
        // Certificar-se de que a diferença seja um número positivo
        return Math.max(0, (int) diasAtraso);
    }
}
