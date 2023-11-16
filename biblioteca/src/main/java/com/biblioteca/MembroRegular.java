package com.biblioteca;

public class MembroRegular extends Membro {
    private int limiteEmprestimos;
    private int periodoEmprestimoDias;
    private double precoMulta;


    public MembroRegular(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.limiteEmprestimos = 3;
        this.periodoEmprestimoDias = 14;
        this.precoMulta = 1.0;
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
