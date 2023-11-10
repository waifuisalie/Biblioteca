package com.biblioteca;

// Classe para representar Membros

import java.util.ArrayList;
import java.util.List;

class Membro extends Pessoa {
    private int numeroMembro;
    private List<Emprestimo> historicoEmprestimos;

    public Membro(String nome, int numeroMembro) {
        super(nome);
        this.numeroMembro = numeroMembro;
        this.historicoEmprestimos = new ArrayList<>();
    }

    public int getNumeroMembro() {
        return numeroMembro;
    }

    public List<Emprestimo> getHistoricoEmprestimos() {
        return historicoEmprestimos;
    }

    @Override
    public void cadastrar() {
        System.out.println("Membro cadastrado: " + getNome());
    }

    public void realizarEmprestimo(Livro livro) {
        if (livro.isDisponivel()) {
            Emprestimo emprestimo = new Emprestimo(this, livro);
            historicoEmprestimos.add(emprestimo);
            livro.emprestar();
            System.out.println("Empréstimo realizado para " + getNome() + ": " + livro.getTitulo());
        } else {
            System.out.println("Livro indisponível para empréstimo: " + livro.getTitulo());
        }
    }

    public void realizarDevolucao(Emprestimo emprestimo) {
        emprestimo.getLivro().devolver();
        historicoEmprestimos.remove(emprestimo);
        System.out.println("Devolução realizada por " + getNome() + ": " + emprestimo.getLivro().getTitulo());
    }

    @Override
    public String toString() {
        return "Membro{" +
                "nome='" + getNome() + '\'' +
                ", numeroMembro=" + numeroMembro +
                '}';
    }
}