package com.biblioteca;

// Classe para representar Empréstimos
class Emprestimo {
    private Membro membro;
    private Livro livro;

    public Emprestimo(Membro membro, Livro livro) {
        this.membro = membro;
        this.livro = livro;
    }

    public Membro getMembro() {
        return membro;
    }

    public Livro getLivro() {
        return livro;
    }
}
