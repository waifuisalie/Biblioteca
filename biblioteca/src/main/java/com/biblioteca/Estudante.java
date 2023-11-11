package com.biblioteca;

public class Estudante extends Membro {
    private int limiteEmprestimos;
    private int periodoEmprestimoDias;

    public Estudante(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.limiteEmprestimos = 4;
        this.periodoEmprestimoDias = 21;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public int getPeriodoEmprestimoDias() {
        return periodoEmprestimoDias;
    }
}

