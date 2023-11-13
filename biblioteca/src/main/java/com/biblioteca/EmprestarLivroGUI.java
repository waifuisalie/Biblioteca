package com.biblioteca;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JButton buscarLivrosButton;
    private JButton emprestarButton;
    private JButton cancelarBuscaButton;
    private JButton cancelarEmprestimoButton;
    private JTable resultadosTable;
    private TableRowSorter<DefaultTableModel> sorter;

    public EmprestarLivroGUI() {
        setTitle("Empréstimo de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());

        JPanel buscaPanel = new JPanel();
        termoBuscaField = new JTextField();
        termoBuscaField.setPreferredSize(new Dimension(200, 25));
        criterioBuscaComboBox = new JComboBox<>(new String[]{"Título", "Código", "Autor"});
        buscarLivrosButton = new JButton("Buscar Livros");
        buscaPanel.add(new JLabel("Pesquisar por:"));
        buscaPanel.add(criterioBuscaComboBox);
        buscaPanel.add(termoBuscaField);
        buscarLivrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String termoBusca = termoBuscaField.getText();
                String criterioBusca = (String) criterioBuscaComboBox.getSelectedItem();
                List<String[]> dadosLivros = verificarECarregarArquivoCSV("livros.csv");
                preencherTabela(dadosLivros, termoBusca, criterioBusca);
            }
        });

        cancelarBuscaButton = new JButton("Cancelar");
        cancelarBuscaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTodosLivros();
            }
        });

        buscaPanel.add(buscarLivrosButton);
        buscaPanel.add(cancelarBuscaButton);
        mainPanel.add(buscaPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        resultadosTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(resultadosTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel emprestimoPanel = new JPanel();
        emprestarButton = new JButton("Realizar Empréstimo");

        cancelarEmprestimoButton = new JButton("Cancelar");
        cancelarEmprestimoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        emprestimoPanel.add(cancelarEmprestimoButton);
        emprestimoPanel.add(emprestarButton);
        mainPanel.add(emprestimoPanel, BorderLayout.SOUTH);

        resultadosTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setBotoesVisiveis(resultadosTable.getSelectedRow() != -1);
            }
        });

        add(mainPanel);

        List<String[]> dadosLivros = verificarECarregarArquivoCSV("livros.csv");
        preencherTabela(dadosLivros, "", "");

        sorter = new TableRowSorter<>(tableModel);
        resultadosTable.setRowSorter(sorter);
    }

    private void setBotoesVisiveis(boolean visivel) {
        emprestarButton.setEnabled(visivel);
        cancelarEmprestimoButton.setEnabled(visivel);
    }

    private List<String[]> verificarECarregarArquivoCSV(String nomeArquivo) {
        if (VerificadorArquivo.verificarExistenciaArquivo(nomeArquivo)) {
            return CsvHandler.lerDados(nomeArquivo);
        } else {
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
            return Collections.emptyList();
        }
    }

    private void preencherTabela(List<String[]> dados, String termoBusca, String criterioBusca) {
        tableModel.setRowCount(0);

        if (dados == null || dados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum livro disponível.");
            return;
        }

        String[] colunas = {"Título", "Código", "Autor", "Ano"};
        tableModel.setColumnIdentifiers(colunas);

        for (String[] livro : dados) {
            Object[] rowData = {livro[0], livro[1], livro[2], livro[3]};
            tableModel.addRow(rowData);
        }

        if (!termoBusca.isEmpty() && criterioBusca != null) {
            int colunaIndex = -1;

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

            RowFilter<Object, Object> rowFilter = RowFilter.regexFilter("(?i)" + termoBusca, colunaIndex);
            sorter.setRowFilter(rowFilter);
            mainPanel.revalidate();
        }
    }

    private void mostrarTodosLivros() {
        List<String[]> dadosLivros = verificarECarregarArquivoCSV("livros.csv");
        preencherTabela(dadosLivros, "", "");
    
        // Limpar o filtro existente
        sorter.setRowFilter(null);
    
        termoBuscaField.setText("");
        criterioBuscaComboBox.setSelectedIndex(0);
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
