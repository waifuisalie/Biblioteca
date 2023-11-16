package com.biblioteca;

public class MembroProfessorBibliotecario extends Membro {
    private int limiteEmprestimos;
    private int periodoEmprestimoDias;
    private double precoMulta;


    public MembroProfessorBibliotecario(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.limiteEmprestimos = 7;
        this.periodoEmprestimoDias = 30;
        this.precoMulta = 0.7;
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
