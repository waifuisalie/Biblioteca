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

    @Override
    public double calcularMulta(Emprestimo emprestimo) {
        // Lógica para calcular a multa para membros Professor/Bibliotecário
        // Use a diferença entre a data atual e a data de devolução para calcular os dias de atraso
        int diasAtraso = calcularDiasAtraso(emprestimo.getDataDevolucao());

        if (diasAtraso > 0) {
            // Lógica para calcular a multa específica para membros Professor/Bibliotecário
            // Implemente conforme necessário
            return diasAtraso * 1.0; // Valor padrão, ajuste conforme necessário
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
