package com.biblioteca;

import java.util.List;

// Classe para representar Empréstimos
class Emprestimo {
    private Membro membro;
    private Livro livro;
    private String dataDevolucao;

    // Adicionando construtor para definir a data de devolução
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

    // Método auxiliar para carregar dados do arquivo CSV
    private List<String[]> verificarECarregarArquivoCSV(String nomeArquivo) {
        if (VerificadorArquivo.verificarExistenciaArquivo(nomeArquivo)) {
            return CsvHandler.lerDados(nomeArquivo);
        } else {
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
            return List.of();
        }
    }

    // Método para carregar a data de devolução do arquivo CSV
    public void carregarDataDevolucao() {
        List<String[]> emprestimos = verificarECarregarArquivoCSV("emprestimos.csv");

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

