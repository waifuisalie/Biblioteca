package com.biblioteca;

// Classe principal que representa a Biblioteca
public class Biblioteca {
    public static void main(String[] args) {
        // Exemplo de uso do sistema
        Livro livro1 = new Livro("Java: The Complete Reference", "123", "Herbert Schildt", 2000);
        Livro livro2 = new Livro("Clean Code", "456", "Robert C. Martin", 2001);

        Membro membro1 = new Membro("João", 1);
        Membro membro2 = new Membro("Maria", 2);

        membro1.cadastrar();
        membro2.cadastrar();

        membro1.realizarEmprestimo(livro1);
        membro2.realizarEmprestimo(livro2);

        membro1.realizarDevolucao(membro1.getHistoricoEmprestimos().get(0));

        System.out.println(livro1);
        System.out.println(livro2);
        System.out.println(membro1);
        System.out.println(membro2);
    }
}