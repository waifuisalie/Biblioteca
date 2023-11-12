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

    @Override
    public void emprestar() {
        if (disponivel) {
            System.out.println("Livro emprestado: " + getTitulo());
            disponivel = false;
        } else {
            System.out.println("Livro indisponível para empréstimo: " + getTitulo());
        }
    }

    @Override
    public void devolver() {
        System.out.println("Livro devolvido: " + getTitulo());
        disponivel = true;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + getTitulo() + '\'' +
                ", autor='" + autor + '\'' +
                ", ano=" + ano +
                ", disponivel=" + disponivel +
                '}';
    }

    public List<String[]> obterDadosParaCSV() {
        List<String[]> dados = new ArrayList<>();
        dados.add(new String[]{getTitulo(), getCodigo(), autor, Integer.toString(ano), Boolean.toString(disponivel)});
        return dados;
    }
}
