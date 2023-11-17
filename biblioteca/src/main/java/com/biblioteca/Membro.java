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

    // implementação do método cadastrar da classe Pessoa
    @Override
    public void cadastrar() {
        System.out.println("Membro cadastrado: " + getNome());
    }

    // método para representar o objeto como uma string CSV
    public String toCsvString() {
        return getNome() + "," + numeroMembro;
    }

    // método toStrinf para fornecer uma representação de string do objeto
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
