package com.biblioteca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class EmprestarLivroGUI extends JFrame {
    private JTextField termoBuscaField;
    private JComboBox<String> criterioBuscaComboBox;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;

    // Declaração dos botões
    private JButton buscarLivrosButton;
    private JButton emprestarButton;
    private JButton cancelarButton;  // Novo botão

    public EmprestarLivroGUI() {
        // Configurações básicas da janela
        setTitle("Empréstimo de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Crie um painel principal com BorderLayout
        mainPanel = new JPanel(new BorderLayout());

        // Crie um painel superior para a busca
        JPanel buscaPanel = new JPanel();
        termoBuscaField = new JTextField();
        termoBuscaField.setPreferredSize(new Dimension(200, 25)); // Ajuste o tamanho aqui
        criterioBuscaComboBox = new JComboBox<>(new String[]{"Título", "Código", "Autor"});
        buscarLivrosButton = new JButton("Buscar Livros");
        buscaPanel.add(new JLabel("Pesquisar por:"));
        buscaPanel.add(criterioBuscaComboBox);
        buscaPanel.add(termoBuscaField);
        buscaPanel.add(buscarLivrosButton);

        // Adicione o botão "Cancelar" ao painel de busca
        cancelarButton = new JButton("Cancelar");
        buscaPanel.add(cancelarButton);

        // Adicione o painel de busca à parte superior da janela
        mainPanel.add(buscaPanel, BorderLayout.NORTH);

        // Crie um modelo de tabela para exibir os resultados da busca
        tableModel = new DefaultTableModel();
        JTable resultadosTable = new JTable(tableModel);

        // Adicione a tabela a um painel com barra de rolagem
        JScrollPane tableScrollPane = new JScrollPane(resultadosTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Crie um painel inferior para os botões de empréstimo
        JPanel emprestimoPanel = new JPanel();
        emprestarButton = new JButton("Realizar Empréstimo");

        // Adicione o botão "Cancelar" ao painel de empréstimo
        cancelarButton = new JButton("Cancelar");
        emprestimoPanel.add(cancelarButton);
        emprestimoPanel.add(emprestarButton);

        // Adicione o painel de empréstimo à parte inferior da janela
        mainPanel.add(emprestimoPanel, BorderLayout.SOUTH);

        // Configurar o ActionListener do botão de busca
        buscarLivrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String termoBusca = termoBuscaField.getText();
                String criterioBusca = (String) criterioBuscaComboBox.getSelectedItem();

                // Implementar a lógica de busca e preenchimento da tabela
                List<String[]> dadosLivros = verificarECarregarArquivoCSV("livros.csv");
                preencherTabela(dadosLivros, termoBusca, criterioBusca);
            }
        });

        // Configurar o ActionListener do botão de empréstimo
        emprestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar a lógica para realizar o empréstimo
                // ...
            }
        });

        // Configurar o ActionListener do botão "Cancelar"
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fechar a janela de empréstimo sem realizar o empréstimo
                dispose();
            }
        });

        // Adicione o painel principal à janela
        add(mainPanel);

        // Chamar a lógica de busca e preenchimento da tabela no construtor
        List<String[]> dadosLivros = verificarECarregarArquivoCSV("livros.csv");
        preencherTabela(dadosLivros, "", ""); // Termo de busca e critério vazios para mostrar todos os livros disponíveis
    }

    // Métodos auxiliares

    // Verifica e carrega o arquivo CSV de livros
    private List<String[]> verificarECarregarArquivoCSV(String nomeArquivo) {
        if (VerificadorArquivo.verificarExistenciaArquivo(nomeArquivo)) {
            // O arquivo existe, então carregue os dados
            return CsvHandler.lerDados(nomeArquivo);
        } else {
            // O arquivo não existe, retorne uma lista vazia ou outra lógica apropriada
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
            return Collections.emptyList(); // ou outra lógica desejada
        }
    }

    // Preenche a tabela com os resultados da busca
private void preencherTabela(List<String[]> dados, String termoBusca, String criterioBusca) {
    // Limpar a tabela antes de preenchê-la novamente
    tableModel.setRowCount(0);

    // Verificar se há dados a serem exibidos
    if (dados == null || dados.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum livro disponível.");
        return;
    }

    // Cabeçalhos da tabela
    String[] colunas = {"Título", "Código", "Autor", "Ano"};

    // Adicionar os cabeçalhos à tabela
    tableModel.setColumnIdentifiers(colunas);

    // Preencher a tabela com todos os livros disponíveis
    for (String[] livro : dados) {
        Object[] rowData = {livro[0], livro[1], livro[2], livro[3]};
        tableModel.addRow(rowData);
    }

    // Se houver um termo de busca, filtrar os resultados
    if (!termoBusca.isEmpty() && criterioBusca != null) {
        int colunaIndex = -1;

        // Determinar qual coluna pesquisar com base no critério de busca
        switch (criterioBusca.toLowerCase()) {
            case "título":
                colunaIndex = 0;
                break;
            case "código":
                colunaIndex = 1;
                break;
            case "autor":
                colunaIndex = 2;
                break;
            default:
                JOptionPane.showMessageDialog(null, "Critério de busca inválido.");
                return;
        }

        // Criar a tabela (resultadosTable)
        JTable resultadosTable = new JTable(tableModel);

        // Filtrar os resultados com base no termo de busca e na coluna escolhida
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        RowFilter<Object, Object> rowFilter = RowFilter.regexFilter("(?i)" + termoBusca, colunaIndex);
        sorter.setRowFilter(rowFilter);
        resultadosTable.setRowSorter(sorter);

        // Adicionar a tabela a um painel com barra de rolagem
        JScrollPane tableScrollPane = new JScrollPane(resultadosTable);
        // Substituir a tabela anterior pela nova tabela filtrada
        mainPanel.remove(1);  // Remove a tabela anterior do painel principal
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);  // Adiciona a nova tabela ao painel principal
        mainPanel.revalidate();  // Atualiza o layout do painel principal
    }
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EmprestarLivroGUI emprestarLivroGUI = new EmprestarLivroGUI();
                emprestarLivroGUI.setVisible(true);
            }
        });
    }
}
