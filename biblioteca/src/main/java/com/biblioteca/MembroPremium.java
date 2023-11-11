package com.biblioteca;

public class MembroPremium extends Membro {
    private int limiteEmprestimos;
    private int periodoEmprestimoDias;
    private double precoMulta;

    public MembroPremium(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.limiteEmprestimos = 5;
        this.periodoEmprestimoDias = 21;
        this.precoMulta = 0.50; // Preço padrão da multa para membros Premium
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public int getPeriodoEmprestimoDias() {
        return periodoEmprestimoDias;
    }

    public double getPrecoMulta() {
        return precoMulta;
    }

    public void setPrecoMulta(double precoMulta) {
        this.precoMulta = precoMulta;
    }
}


