package com.biblioteca;

import java.util.ArrayList;
import java.util.List;

public abstract class Membro extends Pessoa implements CalculaMulta{
    private int numeroMembro;
    private String tipo;
    private List<Emprestimo> historicoEmprestimos;

    public Membro(String nome, int numeroMembro) {
        super(nome);
        this.numeroMembro = numeroMembro;
        this.historicoEmprestimos = new ArrayList<>();
    }

    public int getNumeroMembro() {
        return numeroMembro;
    }

    @Override
    public void cadastrar() {
        System.out.println("Membro cadastrado: " + getNome());
    }

    public String toCsvString() {
        return getNome() + "," + numeroMembro;
    }

    @Override
    public String toString() {
        return "Membro{" +
                "nome='" + getNome() + '\'' +
                ", numeroMembro=" + numeroMembro +
                '}';
    }
}
