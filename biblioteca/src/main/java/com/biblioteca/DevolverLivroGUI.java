package com.biblioteca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DevolverLivroGUI extends JFrame {
    private JTextField nomeMembroField;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JButton buscarLivrosButton;
    private JButton devolverButton;
    private JButton cancelarDevolucaoButton;
    private JTable resultadosTable;
    private TableRowSorter<DefaultTableModel> sorter;

    public DevolverLivroGUI() {
        setTitle("Devolução de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());

        JPanel buscaPanel = new JPanel();
        nomeMembroField = new JTextField();
        nomeMembroField.setPreferredSize(new Dimension(200, 25));
        buscarLivrosButton = new JButton("Buscar Livros para Devolução");
        buscaPanel.add(new JLabel("Nome do Membro:"));
        buscaPanel.add(nomeMembroField);

        buscarLivrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarLivros();
            }
        });

        cancelarDevolucaoButton = new JButton("Cancelar");
        cancelarDevolucaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        

        buscaPanel.add(buscarLivrosButton);
        buscaPanel.add(cancelarDevolucaoButton);
        mainPanel.add(buscaPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        resultadosTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(resultadosTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel devolucaoPanel = new JPanel();
        devolverButton = new JButton("Realizar Devolução");

        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implemente a lógica de devolução aqui
                int selectedRow = resultadosTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Obter os dados do livro selecionado
                    String titulo = (String) resultadosTable.getValueAt(selectedRow, 0);
                    String codigo = (String) resultadosTable.getValueAt(selectedRow, 1);

                    // Agora você tem os dados do livro selecionado, e pode prosseguir com a lógica de devolução.
                    realizarDevolucao(titulo, codigo);
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum livro selecionado para devolução.");
                }
            }
        });

        devolucaoPanel.add(cancelarDevolucaoButton);
        devolucaoPanel.add(devolverButton);
        mainPanel.add(devolucaoPanel, BorderLayout.SOUTH);

        add(mainPanel);

        sorter = new TableRowSorter<>(tableModel);
        resultadosTable.setRowSorter(sorter);
    }

    private List<String[]> verificarECarregarArquivoCSV(String nomeArquivo) {
        if (VerificadorArquivo.verificarExistenciaArquivo(nomeArquivo)) {
            return CsvHandler.lerDados(nomeArquivo);
        } else {
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
            return List.of();
        }
    }

    private void preencherTabela(List<String[]> dadosLivros, List<String[]> emprestimos, String nomeMembro) {
        tableModel.setRowCount(0);

        if (dadosLivros.isEmpty() || emprestimos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum livro disponível para devolução.");
            return;
        }

        String[] colunas = {"Título", "Código", "Autor", "Ano"};
        tableModel.setColumnIdentifiers(colunas);

        for (String[] emprestimo : emprestimos) {
            // Verificar se o empréstimo pertence ao membro 
            if (emprestimo.length >= 5 && emprestimo[1].trim().equalsIgnoreCase(nomeMembro)) {
                // Procurar o livro correspondente
                for (String[] livro : dadosLivros) {
                    if (livro[1].equals(emprestimo[2])) {
                        Object[] rowData = {livro[0], livro[1], livro[2], livro[3]};
                        tableModel.addRow(rowData);
                        break;
                    }
                }
            }
        }

        sorter.setRowFilter(null); // Limpar qualquer filtro existente
    }

    private void carregarLivros() {
        String nomeMembro = nomeMembroField.getText();
        if (verificarExistenciaMembro(nomeMembro)) {
            List<String[]> dadosLivros = verificarECarregarArquivoCSV("livros.csv");
            List<String[]> emprestimos = verificarECarregarArquivoCSV("emprestimos.csv");
            preencherTabela(dadosLivros, emprestimos, nomeMembro);
        } else {
            JOptionPane.showMessageDialog(null, "Membro não encontrado. Empréstimo não realizado.");
        }
    }

    // Implemente o método realizarDevolucao aqui
    private void realizarDevolucao(String titulo, String codigo) {
        // Lógica de devolução do livro
        // Atualizar o estado do livro para "disponível" no arquivo livros.csv
        // Remover o registro de empréstimo do arquivo emprestimos.csv
        // Mostrar mensagem de sucesso ou erro
    }

    private boolean verificarExistenciaMembro(String nomeMembro) {
        List<String[]> dadosMembros = verificarECarregarArquivoCSV("membros.csv");
    
        for (String[] membro : dadosMembros) {
            if (membro[0].equalsIgnoreCase(nomeMembro)) {
                return true; // Membro encontrado
            }
        }
        return false; // Membro não encontrado
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DevolverLivroGUI devolverLivroGUI = new DevolverLivroGUI();
                devolverLivroGUI.setVisible(true);
            }
        });
    }
}
