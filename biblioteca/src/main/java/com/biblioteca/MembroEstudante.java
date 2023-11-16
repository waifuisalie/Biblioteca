package com.biblioteca;

public class MembroEstudante extends Membro { // implements CalculaMulta seria redundante 
    private int limiteEmprestimos;
    private int periodoEmprestimoDias;
    private double precoMulta;


    public MembroEstudante(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.limiteEmprestimos = 4;
        this.periodoEmprestimoDias = 21;
        this.precoMulta = 0.5;
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

    @Override
    public double calcularMulta(Emprestimo emprestimo) {
        String dataDevolucao = emprestimo.getDataDevolucao();
        int diasAtraso = CalculadoraDiasAtraso.calcularDiasAtraso(dataDevolucao);
        return diasAtraso * getPrecoMulta();
    }
}
