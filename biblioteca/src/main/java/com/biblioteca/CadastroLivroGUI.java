package com.biblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroLivroGUI extends JFrame {
    public CadastroLivroGUI() {
        // Configurações básicas da janela
        setTitle("Cadastro de Livro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas a janela atual ao clicar em fechar
        setSize(400, 300);
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Crie um painel para adicionar componentes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Adicione componentes para o formulário de cadastro de livro
        JLabel tituloLabel = new JLabel("Título:");
        JTextField tituloField = new JTextField();

        JLabel autorLabel = new JLabel("Autor:");
        JTextField autorField = new JTextField();

        JLabel anoLabel = new JLabel("Ano:");
        JTextField anoField = new JTextField();

        JButton cadastrarButton = new JButton("Cadastrar");

        // Adicione os componentes ao painel
        panel.add(tituloLabel);
        panel.add(tituloField);
        panel.add(autorLabel);
        panel.add(autorField);
        panel.add(anoLabel);
        panel.add(anoField);
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(cadastrarButton);

        // Adicione o painel à janela
        add(panel);

        // Configure a ação do botão "Cadastrar"
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui você pode obter os valores do formulário e realizar o cadastro no sistema
                String titulo = tituloField.getText();
                String autor = autorField.getText();
                int ano = Integer.parseInt(anoField.getText()); // Supondo que o ano seja um número inteiro

                // Implemente a lógica para cadastrar o livro no sistema
                // Exemplo: Biblioteca.cadastrarLivro(titulo, autor, ano);

                // Feche a janela de cadastro após o cadastro
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CadastroLivroGUI cadastroLivroGUI = new CadastroLivroGUI();
                cadastroLivroGUI.setVisible(true);
            }
        });
    }
}
