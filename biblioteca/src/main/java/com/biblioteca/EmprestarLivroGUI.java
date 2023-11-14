package com.biblioteca;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        emprestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar se uma linha está selecionada
                int selectedRow = resultadosTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Obter os dados do livro selecionado
                    String titulo = (String) resultadosTable.getValueAt(selectedRow, 0);
                    String codigo = (String) resultadosTable.getValueAt(selectedRow, 1);
                    String autor = (String) resultadosTable.getValueAt(selectedRow, 2);
                    String ano = (String) resultadosTable.getValueAt(selectedRow, 3);
        
                    // Agora você tem os dados do livro selecionado, e pode prosseguir com a lógica de empréstimo.
                    realizarEmprestimo(titulo, codigo, autor, ano);
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum livro selecionado.");
                }
            }
        });
        

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
            boolean disponivel = livro.length > 4 && "true".equalsIgnoreCase(livro[4]);
    
            // Verificar se o livro está disponível
            if (disponivel) {
                Object[] rowData = {livro[0], livro[1], livro[2], livro[3]};
                tableModel.addRow(rowData);
            }
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

    // Dentro do método realizarEmprestimo, após adicionar o empréstimo ao CSV
private void realizarEmprestimo(String titulo, String codigo, String autor, String ano) {
    String nomeMembro = JOptionPane.showInputDialog("Digite o nome do membro:");

    // Verificar a existência do membro
    if (verificarExistenciaMembro(nomeMembro)) {
        // Adicione os dados do empréstimo ao arquivo CSV de empréstimos
        String dataDevolucao = JOptionPane.showInputDialog("Digite a data de devolução:");
        adicionarEmprestimoAoCSV(nomeMembro, titulo, dataDevolucao, codigo);

        // Atualizar a disponibilidade do livro para "false" no arquivo livros.csv
        String chaveLivro = codigo; // Você pode ajustar a chave conforme necessário
        String novaLinhaLivro = String.format("%s, %s, %s, %s, false", titulo, codigo, autor, ano);
        CsvHandler.atualizarLinha("livros.csv", chaveLivro, novaLinhaLivro);

        JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");

        // update table 
        mostrarTodosLivros();
    } else {
        JOptionPane.showMessageDialog(null, "Membro não encontrado. Empréstimo não realizado.");
    }
}


    private void adicionarEmprestimoAoCSV(String nomeMembro, String tituloLivro, String dataDevolucao, String codigo) {
        // Construir a linha de empréstimo
        String tipoMembro = CsvHandler.obterTipoMembro(nomeMembro, "membros.csv");
        String emprestimo = String.format("%s, %s, %s, %s, %s, %s",
                tipoMembro, nomeMembro, codigo, tituloLivro, obterDataAtual(), dataDevolucao);
    
        // Escrever a linha no arquivo CSV de empréstimos usando CsvHandler
        CsvHandler.escreverLinha("emprestimos.csv", emprestimo);
    }

    private String obterDataAtual() {
        // Obtém a data atual
        LocalDate dataAtual = LocalDate.now();

        // Formata a data para o formato desejado (dd/MM/yyyy)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatter);

        return dataFormatada;
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
                EmprestarLivroGUI emprestarLivroGUI = new EmprestarLivroGUI();
                emprestarLivroGUI.setVisible(true);
            }
        });
    }
}
