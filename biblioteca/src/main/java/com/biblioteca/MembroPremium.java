package com.biblioteca;

public class MembroPremium extends Membro implements CalculaMulta {
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

    @Override
    public double calcularMulta(Emprestimo emprestimo) {
        // Lógica para calcular a multa para membros Premium
        // Use a diferença entre a data atual e a data de devolução para calcular os dias de atraso
        int diasAtraso = calcularDiasAtraso(emprestimo.getDataDevolucao());
        
        if (diasAtraso > 0) {
            return diasAtraso * precoMulta;
        } else {
            return 0.0; // Sem multa se não houver atraso
        }
    }
    
    // Implemente o método calcularDiasAtraso aqui
    private int calcularDiasAtraso(String dataDevolucao) {
        // Lógica para calcular a diferença em dias entre a data atual e a data de devolução
        // Implemente conforme necessário
        // Retorna a diferença em dias
        return 0;
    }
}
