package com.biblioteca;

public class MembroEstudante extends Membro { // implements CalculaMulta seria redundante 
    private double precoMulta;

    // construtor
    public MembroEstudante(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.precoMulta = 0.5;
    }

    // obter preço de multa
    public double getPrecoMulta() {
        return precoMulta;
    }

    // setter
    public void setPrecoMulta(double precoMulta) {
        this.precoMulta = precoMulta;
    }

    // implementação do método abstrato calcularMulta da interface CalculaMulta
    @Override
    public double calcularMulta(Emprestimo emprestimo) {
        String dataDevolucao = emprestimo.getDataDevolucao();
        int diasAtraso = CalculadoraDiasAtraso.calcularDiasAtraso(dataDevolucao);
        return diasAtraso * getPrecoMulta();
    }
}
