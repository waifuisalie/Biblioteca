package com.biblioteca;

public class MembroProfessorBibliotecario extends Membro {
    private int limiteEmprestimos;
    private int periodoEmprestimoDias;

    public MembroProfessorBibliotecario(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.limiteEmprestimos = 7;
        this.periodoEmprestimoDias = 30;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public int getPeriodoEmprestimoDias() {
        return periodoEmprestimoDias;
    }
}

