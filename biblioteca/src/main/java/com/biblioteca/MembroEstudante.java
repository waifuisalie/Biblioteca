package com.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MembroEstudante extends Membro implements CalculaMulta {
    private int limiteEmprestimos;
    private int periodoEmprestimoDias;

    public MembroEstudante(String nome, int numeroMembro) {
        super(nome, numeroMembro);
        this.limiteEmprestimos = 4;
        this.periodoEmprestimoDias = 21;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public int getPeriodoEmprestimoDias() {
        return periodoEmprestimoDias;
    }

    @Override
    public double calcularMulta(Emprestimo emprestimo) {
        // Obtém a data atual
        LocalDate dataAtual = LocalDate.now();

        // Obtém a data de devolução do empréstimo
        LocalDate dataDevolucao = LocalDate.parse(emprestimo.getDataDevolucao(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Calcula a diferença de dias
        long diasAtraso = dataAtual.until(dataDevolucao).getDays();

        // Se não houver atraso, a multa é zero
        if (diasAtraso <= 0) {
            return 0.0;
        }

        // Aqui você pode implementar a lógica específica de multa para MembroEstudante
        // Por exemplo, pode ser uma multa fixa por dia de atraso
        double multaFixaPorDia = 2.0;
        return diasAtraso * multaFixaPorDia;
    }
}
