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
        panel.setLayout(new GridLayout(3, 2));

        // Adicione componentes para o formulário de busca de livro
        JLabel buscaLabel = new JLabel("Digite o título ou código:");
        JTextField buscaField = new JTextField();
        JButton buscarButton = new JButton("Buscar");

        // Adicione os componentes ao painel
        panel.add(buscaLabel);
        panel.add(buscaField);
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(buscarButton);

        // Adicione o painel à janela
        add(panel);

        // Dentro do ActionListener do botão "Buscar" em BuscaLivroGUI
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obter o termo de busca
                String termoBusca = buscaField.getText();

                // Verificar e criar o arquivo CSV de livros, se necessário
                VerificadorArquivo.verificarECriarArquivoCSV("livros.csv");

                // Realizar a busca no arquivo CSV
                List<String[]> dadosLivros = CsvHandler.lerDados("livros.csv");

                // Exibir resultados da busca
                exibirResultadosBusca(dadosLivros, termoBusca);

                // Fechar a janela de busca após a busca
                dispose();
            }
        });
    }

    // Método para exibir os resultados da busca
    private void exibirResultadosBusca(List<String[]> dadosLivros, String termoBusca) {
        // Implemente a lógica para exibir os resultados da busca, considerando o termo de busca
        // Pode ser mostrado em uma nova janela, em um JOptionPane, ou em outro componente de interface gráfica
        // Aqui você pode adicionar a lógica específica para a apresentação dos resultados.
        // Exemplo: JOptionPane.showMessageDialog(null, "Resultados da busca: " + resultados);
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
