package com.biblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CadastroLivroGUI extends JFrame {
    public CadastroLivroGUI() {
        // Configurações básicas da janela
        setTitle("Cadastro de Livro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas a janela atual ao clicar em fechar
        setSize(400, 300);
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Crie um painel para adicionar componentes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        // Adicione componentes para o formulário de cadastro de livro
        JLabel tituloLabel = new JLabel("Título:");
        JTextField tituloField = new JTextField();

        JLabel codigoLabel = new JLabel("Código:");
        JTextField codigoField = new JTextField();

        JLabel autorLabel = new JLabel("Autor:");
        JTextField autorField = new JTextField();

        JLabel anoLabel = new JLabel("Ano:");
        JTextField anoField = new JTextField();

        JButton cadastrarButton = new JButton("Cadastrar");

        // Adicione os componentes ao painel
        panel.add(tituloLabel);
        panel.add(tituloField);
        panel.add(codigoLabel);
        panel.add(codigoField);
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
                // Obter os valores do formulário
                String titulo = tituloField.getText();
                String codigo = codigoField.getText();
                String autor = autorField.getText();
                int ano = Integer.parseInt(anoField.getText());

                // Implementar a lógica para cadastrar o livro no sistema
                Livro livro = new Livro(titulo, codigo, autor, ano);

                // Verificar e criar o arquivo CSV de livros
                verificarECriarArquivoCSV("livros.csv");

                // Salvar as informações do livro no arquivo CSV
                List<String[]> dadosLivro = livro.obterDadosParaCSV();
                CsvHandler.escreverDados("livros.csv", dadosLivro);

                // Exibir mensagem de sucesso
                JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");

                // Fechar a janela de cadastro após o cadastro
                dispose();
            }
        });
    }

    private void verificarECriarArquivoCSV(String nomeArquivo) {
        if (!Files.exists(Paths.get(nomeArquivo))) {
            try {
                Files.createFile(Paths.get(nomeArquivo));
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao criar arquivo CSV.");
            }
        }
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
