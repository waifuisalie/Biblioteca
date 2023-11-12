package com.biblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuscaLivroGUI extends JFrame {

    public BuscaLivroGUI() {
        // Configurações básicas da janela
        setTitle("Buscar Livro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Crie um painel para adicionar componentes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Adicione componentes para o formulário de busca de livro
        JLabel tipoBuscaLabel = new JLabel("Tipo de Busca:");
        JComboBox<String> tipoBuscaComboBox = new JComboBox<>(new String[]{"Título", "Código", "Autor"});

        JLabel buscaLabel = new JLabel("Digite o termo:");
        JTextField buscaField = new JTextField();

        JButton buscarButton = new JButton("Buscar");

        // Adicione os componentes ao painel
        panel.add(tipoBuscaLabel);
        panel.add(tipoBuscaComboBox);
        panel.add(buscaLabel);
        panel.add(buscaField);
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(buscarButton);

        // Adicione o painel à janela
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
                VerificadorArquivo.verificarECriarArquivoCSV("livros.csv");

                // Realizar a busca no arquivo CSV
                List<String[]> dadosLivros = CsvHandler.lerDados("livros.csv");

                // Exibir resultados da busca
                exibirResultadosBusca(dadosLivros, tipoBusca, termoBusca);

                // Fechar a janela de busca após a busca
                dispose();
            }
        });
    }

    private void exibirResultadosBusca(List<String[]> dadosLivros, String tipoBusca, String termoBusca) {
        // Implemente a lógica para buscar e exibir os resultados
        // Pode ser útil usar um JOptionPane ou outra interface gráfica para exibir os resultados
        // ...
    
        // Exemplo de uso do JOptionPane para exibir resultados em um diálogo simples:
        StringBuilder resultados = new StringBuilder("Resultados da busca por " + tipoBusca + ":\n");
        boolean encontrouResultados = false;
    
        for (String[] livro : dadosLivros) {
            // Verifica se o termo de busca está no título, código, autor ou ano do livro, dependendo do tipo de busca
            switch (tipoBusca.toLowerCase()) {
                case "título":
                    if (livro[0].toLowerCase().contains(termoBusca.toLowerCase())) {
                        resultados.append("Título: ").append(livro[0]).append(", Código: ").append(livro[1])
                                .append(", Autor: ").append(livro[2]).append(", Ano: ").append(livro[3]).append("\n");
                        encontrouResultados = true;
                        // Adicione outros detalhes do livro, se necessário
                    }
                    break;
                case "código":
                    if (livro[1].toLowerCase().contains(termoBusca.toLowerCase())) {
                        resultados.append("Título: ").append(livro[0]).append(", Código: ").append(livro[1])
                                .append(", Autor: ").append(livro[2]).append(", Ano: ").append(livro[3]).append("\n");
                        encontrouResultados = true;
                        // Adicione outros detalhes do livro, se necessário
                    }
                    break;
                case "autor":
                    if (livro[2].toLowerCase().contains(termoBusca.toLowerCase())) {
                        resultados.append("Título: ").append(livro[0]).append(", Código: ").append(livro[1])
                                .append(", Autor: ").append(livro[2]).append(", Ano: ").append(livro[3]).append("\n");
                        encontrouResultados = true;
                        // Adicione outros detalhes do livro, se necessário
                    }
                    break;
                default:
                    // Tipo de busca inválido
                    JOptionPane.showMessageDialog(null, "Tipo de busca inválido.");
                    return;
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
