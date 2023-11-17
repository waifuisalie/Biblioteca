package com.biblioteca;

import java.util.ArrayList;
import java.util.List;

// Classe para representar Livros
class Livro extends ItemBiblioteca {
    private String autor;
    private int ano; // Adicionando o ano
    private boolean disponivel;

    public Livro(String titulo, String codigo, String autor, int ano) {
        super(titulo, codigo);
        this.autor = autor;
        this.ano = ano;
        this.disponivel = true;
    }

    public String getAutor() {
        return autor;
    }

    public int getAno() {
        return ano;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void definirComoIndisponivel() {
        this.disponivel = false;
    }

    // método para obter os dados do Livro para serem escritos em arquivo csv
    public List<String[]> obterDadosParaCSV() {
        List<String[]> dados = new ArrayList<>();
        dados.add(new String[]{getTitulo(), getCodigo(), autor, Integer.toString(ano), Boolean.toString(disponivel)});
        return dados;
    }

    // método estático para obter um livro pelo código
    public static Livro obterLivroPorCodigo(String codigo) {
    List<String[]> dadosLivros = CsvHandler.verificarECarregarArquivoCSV("livros.csv");

    for (String[] livro : dadosLivros) {
        if (livro.length >= 2 && livro[1].trim().equalsIgnoreCase(codigo)) {
            return new Livro(livro[0].trim(), livro[1].trim(), livro[2].trim(), Integer.parseInt(livro[3].trim()));
        }
    }
    return null; // Retorna null se o livro não for encontrado
}
}
