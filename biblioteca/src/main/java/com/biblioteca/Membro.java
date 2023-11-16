package com.biblioteca;


public abstract class Membro extends Pessoa implements CalculaMulta {
    private int numeroMembro;

    public Membro(String nome, int numeroMembro) {
        super(nome);
        this.numeroMembro = numeroMembro;
    }

    public int getNumeroMembro() {
        return numeroMembro;
    }

    @Override
    public void cadastrar() {
        System.out.println("Membro cadastrado: " + getNome());
    }

    public String toCsvString() {
        return getNome() + "," + numeroMembro;
    }

    @Override
    public String toString() {
        return "Membro{" +
                "nome='" + getNome() + '\'' +
                ", numeroMembro=" + numeroMembro +
                '}';
    }

    // Método abstrato para calcular a multa
    public abstract double calcularMulta(Emprestimo emprestimo);
}
