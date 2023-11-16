package com.biblioteca;

// Classe base para itens da biblioteca (Livros e Membros)
abstract class ItemBiblioteca {
    private String titulo;
    private String codigo;

    public ItemBiblioteca(String titulo, String codigo) {
        this.titulo = titulo;
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCodigo() {
        return codigo;
    }
}










