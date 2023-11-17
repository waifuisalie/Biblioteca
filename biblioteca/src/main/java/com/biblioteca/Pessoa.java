package com.biblioteca;

// Classe base para pessoas (Membros)
abstract class Pessoa {
    private String nome;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
    // Método abstrato
    public abstract void cadastrar();
}