package com.biblioteca;

public class MembroProfessorBibliotecario extends Membro {
    private double precoMulta;


    public MembroProfessorBibliotecario(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.precoMulta = 0.7;
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
