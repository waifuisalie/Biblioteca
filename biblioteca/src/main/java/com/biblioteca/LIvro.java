package com.biblioteca;

// Classe para representar Livros
class Livro extends ItemBiblioteca {
    private String autor;
    private boolean disponivel;

    public Livro(String titulo, String codigo, String autor) {
        super(titulo, codigo);
        this.autor = autor;
        this.disponivel = true;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponivel() {
        return disponivel;
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
                ", disponivel=" + disponivel +
                '}';
    }
}