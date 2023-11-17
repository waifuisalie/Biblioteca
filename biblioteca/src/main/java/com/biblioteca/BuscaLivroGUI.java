package com.biblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuscaLivroGUI extends JFrame {

    public BuscaLivroGUI() {
        // configuração da janela
        setTitle("Buscar Livro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Criando painel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Adicionando componentes para o formulário de busca de livro
        JLabel tipoBuscaLabel = new JLabel("Tipo de Busca:");
        JComboBox<String> tipoBuscaComboBox = new JComboBox<>(new String[]{"Título", "Código", "Autor"});

        JLabel buscaLabel = new JLabel("Digite o termo:");
        JTextField buscaField = new JTextField();

        JButton buscarButton = new JButton("Buscar");

        // adicionando componentes ao painel
        panel.add(tipoBuscaLabel);
        panel.add(tipoBuscaComboBox);
        panel.add(buscaLabel);
        panel.add(buscaField);
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(buscarButton);

        // Adicionando o painel à janela
        add(panel);

        // Configure a ação do botão "Buscar"
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obter o tipo de busca selecionado
                String tipoBusca = (String) tipoBuscaComboBox.getSelectedItem();
                
                // Obter o termo de busca
                String termoBusca = buscaField.getText();

                // Verificar e criar o arquivo CSV de livros, se necessário
                CsvHandler.verificarECriarArquivoCSV("livros.csv");

                // Realizar a busca no arquivo CSV
                List<String[]> dadosLivros = CsvHandler.lerDados("livros.csv");

                // Exibir resultados da busca
                exibirResultadosBusca(dadosLivros, tipoBusca, termoBusca);

                // Fechar a janela de busca após a busca
                dispose();
            }
        });
    }

    // exibir resultados de busca
    private void exibirResultadosBusca(List<String[]> dadosLivros, String tipoBusca, String termoBusca) {
        StringBuilder resultados = new StringBuilder("Resultados da busca por " + tipoBusca + ":\n");
        boolean encontrouResultados = false;
    
        for (String[] livro : dadosLivros) {
            boolean livroCorrespondeBusca = false;
    
            // Verifica se o termo de busca está no título, código, autor ou ano do livro, dependendo do tipo de busca
            switch (tipoBusca.toLowerCase()) {
                case "título":
                    livroCorrespondeBusca = livro[0].toLowerCase().contains(termoBusca.toLowerCase());
                    break;
                case "código":
                    livroCorrespondeBusca = livro[1].toLowerCase().contains(termoBusca.toLowerCase());
                    break;
                case "autor":
                    livroCorrespondeBusca = livro[2].toLowerCase().contains(termoBusca.toLowerCase());
                    break;
                default:
                    // Tipo de busca inválido
                    JOptionPane.showMessageDialog(null, "Tipo de busca inválido.");
                    return;
            }
    
            // Se o livro corresponde à busca, incluir detalhes na lista de resultados
            if (livroCorrespondeBusca) {
                resultados.append("Título: ").append(livro[0]).append(", Código: ").append(livro[1])
                        .append(", Autor: ").append(livro[2]).append(", Ano: ").append(livro[3]);
    
                // Adicione detalhes sobre a disponibilidade
                if (livro.length > 4) {
                    boolean disponivel = Boolean.parseBoolean(livro[4]);
                    resultados.append(", Disponível para Empréstimo: ").append(disponivel ? "Sim" : "Não");
                }
    
                resultados.append("\n");
                encontrouResultados = true;
            }
        }
    
        // Verificar se nenhum resultado foi encontrado
        if (!encontrouResultados) {
            JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado para a busca por " + tipoBusca + ".");
        } else {
            // Exibir os resultados em um diálogo JOptionPane
            JOptionPane.showMessageDialog(null, resultados.toString());
        }
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BuscaLivroGUI buscaLivroGUI = new BuscaLivroGUI();
                buscaLivroGUI.setVisible(true);
            }
        });
    }
}
