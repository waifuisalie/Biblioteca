package com.biblioteca;

public class MembroRegular extends Membro {
    private int limiteEmprestimos;
    private int periodoEmprestimoDias;

    public MembroRegular(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.limiteEmprestimos = 3;
        this.periodoEmprestimoDias = 14;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public int getPeriodoEmprestimoDias() {
        return periodoEmprestimoDias;
    }
}
