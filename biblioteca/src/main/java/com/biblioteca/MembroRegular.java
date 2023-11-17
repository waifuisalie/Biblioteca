package com.biblioteca;

public class MembroRegular extends Membro {
    private double precoMulta;


    public MembroRegular(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.precoMulta = 1.0;
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
