package com.biblioteca;

public class MembroEstudante extends Membro {
    private int limiteEmprestimos;
    private int periodoEmprestimoDias;

    public MembroEstudante(String nome, int numeroMembro) {
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

