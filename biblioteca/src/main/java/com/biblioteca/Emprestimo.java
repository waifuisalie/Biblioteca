package com.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Emprestimo {
    private Membro membro;
    private Livro livro;
    private String dataDevolucao;

    public Emprestimo(Membro membro, Livro livro, String dataDevolucao) {
        this.membro = membro;
        this.livro = livro;
        this.dataDevolucao = dataDevolucao;
    }

    public Membro getMembro() {
        return membro;
    }

    public Livro getLivro() {
        return livro;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    // Método para calcular os dias de atraso
    public int calcularDiasAtraso() {
        if (dataDevolucao != null && !dataDevolucao.isEmpty()) {
            LocalDate dataAtual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataDevolucaoFormatada = LocalDate.parse(dataDevolucao, formatter);

            // Calcular a diferença em dias
            return (int) dataAtual.until(dataDevolucaoFormatada).getDays();
        }
        return 0; // Se não houver data de devolução, não há atraso
    }

    // Método para carregar a data de devolução do arquivo CSV
    public void carregarDataDevolucao() {
        List<String[]> emprestimos = CsvHandler.verificarECarregarArquivoCSV("emprestimos.csv");

        // Verificar se há empréstimos correspondentes e definir a data de devolução
        for (String[] emprestimo : emprestimos) {
            if (emprestimo.length >= 6 && emprestimo[1].trim().equalsIgnoreCase(membro.getNome()) &&
                    emprestimo[2].trim().equalsIgnoreCase(livro.getCodigo())) {
                dataDevolucao = emprestimo[5].trim();
                break;
            }
        }
    }


}
