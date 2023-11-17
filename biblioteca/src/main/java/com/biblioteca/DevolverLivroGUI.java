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
        // configuração da janela
        setTitle("Devolução de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // painel principal
        mainPanel = new JPanel(new BorderLayout());

        // painel para busca de livros
        JPanel buscaPanel = new JPanel();
        nomeMembroField = new JTextField();
        nomeMembroField.setPreferredSize(new Dimension(200, 25));
        buscarLivrosButton = new JButton("Buscar Livros para Devolução");
        buscaPanel.add(new JLabel("Nome do Membro:"));
        buscaPanel.add(nomeMembroField);

        // listener para o botão de busca de livros
        buscarLivrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarLivros();
            }
        });

        cancelarDevolucaoButton = new JButton("Cancelar");

        // listener para o botão de cancelar
        cancelarDevolucaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        buscaPanel.add(buscarLivrosButton);
        buscaPanel.add(cancelarDevolucaoButton);
        mainPanel.add(buscaPanel, BorderLayout.NORTH);

        // modelo e tabela para exibir resultados
        tableModel = new DefaultTableModel();
        resultadosTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(resultadosTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // painel para ações de devolução
        JPanel devolucaoPanel = new JPanel();
        devolverButton = new JButton("Realizar Devolução");

        // listener para o botão de devolução
        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarDevolucao();
            }
        });
        
        devolucaoPanel.add(cancelarDevolucaoButton);
        devolucaoPanel.add(devolverButton);
        mainPanel.add(devolucaoPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // configuração de classificação na tabela
        sorter = new TableRowSorter<>(tableModel);
        resultadosTable.setRowSorter(sorter);
    }

    // Método auxiliar para criar a instância correta de Membro com base no tipo
    private Membro criarMembro(String tipoMembro, String nomeMembro) {
        switch (tipoMembro) {
            case "Estudante":
                return new MembroEstudante(nomeMembro,0 /* adicione o número do membro, se necessário */);
            case "Regular":
                return new MembroRegular(nomeMembro, 0/* adicione o número do membro, se necessário */);
            case "Premium":
                return new MembroPremium(nomeMembro, 0/* adicione o número do membro, se necessário */);
            case "Professor/Bibliotecário":
                return new MembroProfessorBibliotecario(nomeMembro, 0/* adicione o número do membro, se necessário */);
            default:
                // Lógica para lidar com tipos de membros desconhecidos ou inesperados
                System.out.println("Tipo de membro desconhecido: " + tipoMembro);
                return null;
        }
    }

    // método para obter um livro pelo seu código
    private Livro obterLivroPorCodigo(String codigoLivro) {
        // Carregar os dados dos empréstimos
        List<String[]> emprestimos = CsvHandler.lerDados("emprestimos.csv");

        // Iterar sobre as linhas para encontrar o livro com o código correspondente
        for (String[] emprestimo : emprestimos) {
            if (emprestimo.length > 2 && emprestimo[2].trim().equals(codigoLivro)) {
                // O livro foi encontrado, retornar uma instância de Livro com base nos dados
                String titulo = emprestimo[3].trim();
                String autor = emprestimo[4].trim();
                int ano = 0; // Certifique-se de ajustar a posição conforme necessário
                // Outros dados do livro podem ser obtidos da linha do empréstimo

                return new Livro(titulo, codigoLivro, autor, ano);
            }
        }

        // Se não encontrar, retorne null ou lide com isso conforme necessário
        return null;
    }

    // método para preencher a tabela com os livros disponíveis para a devolução
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

    // método para carregar os livros disponíveis para a devolução
    private void carregarLivros() {
        String nomeMembro = nomeMembroField.getText();
        if (verificarExistenciaMembro(nomeMembro)) {
            List<String[]> dadosLivros = CsvHandler.verificarECarregarArquivoCSV("livros.csv");
            List<String[]> emprestimos = CsvHandler.verificarECarregarArquivoCSV("emprestimos.csv");
            preencherTabela(dadosLivros, emprestimos, nomeMembro);
        } else {
            JOptionPane.showMessageDialog(null, "Membro não encontrado. Empréstimo não realizado.");
        }
    }

    // método para realizar a devolução
    private void realizarDevolucao() {
         int selectedRow = resultadosTable.getSelectedRow();
                if (selectedRow != -1) {
                    String codigoLivro = ((String) resultadosTable.getValueAt(selectedRow, 1)).trim();
                    String nomeMembro = nomeMembroField.getText();  // Obtém o nome do membro do TextField
                    List<String[]> emprestimos = CsvHandler.verificarECarregarArquivoCSV("emprestimos.csv");
        
                    // garante que a lista não está vazia e tem dados suficientes
                    if (!emprestimos.isEmpty() && emprestimos.get(0).length > 5) {

                        // Iterar sobre as linhas para encontrar o livro
                        for (String[] emprestimo : emprestimos) {
                            if (emprestimo.length > 2 && emprestimo[2].trim().equals(codigoLivro)) {

                                // A linha foi encontrada, agora podemos obter a data de devolução
                                String dataDevolucao = emprestimo[5].trim();
                                String tipoMembro = emprestimo[0].trim();
        
                                // Crie uma instância de Membro com base no tipo
                                Membro membro = criarMembro(tipoMembro, nomeMembro);
        
                                // Crie uma instância de Livro com base no código
                                Livro livro = obterLivroPorCodigo(codigoLivro); 
        
                                int diasAtraso = CalculadoraDiasAtraso.calcularDiasAtraso(dataDevolucao);
                                System.out.println(diasAtraso);
        
                                // Verifique os dias de atraso
                                if (diasAtraso > 0) {        
                                    // Crie uma instância de Emprestimo
                                    Emprestimo emprestimoObj = new Emprestimo(membro, livro, dataDevolucao);
                                    
                                    // Implemente lógica para calcular multa.
                                    double multa = ((CalculaMulta) membro).calcularMulta(emprestimoObj);

                                    String mensagem = "Devolução com atraso de " + diasAtraso + " dias.\nMulta a ser paga: " + multa;

                                    int escolha = JOptionPane.showConfirmDialog(null, mensagem + "\nVocê concorda em pagar a multa?", "Aviso", JOptionPane.YES_NO_OPTION);

                                    // Verifica escolha do usuário
                                    if (escolha == JOptionPane.YES_OPTION) {
                                        // Usuário concordou em pagar a multa
                                        // 1. Excluir linha de empréstimo em emprestimos.csv
                                        List<String[]> emprestimos_updt = CsvHandler.lerDados("emprestimos.csv");
                                        for (int i = 0; i < emprestimos_updt.size(); i++) {
                                            String[] emprestimo_updt = emprestimos_updt.get(i);
                                            if (emprestimo_updt.length > 2 && emprestimo_updt[2].trim().equals(codigoLivro)) {
                                                emprestimos_updt.remove(i);
                                                break;
                                            }
                                        }
                                        // escreve a linha atualizada (apaga)
                                        CsvHandler.escreverLinhas("emprestimos.csv", emprestimos_updt);

                                        // 2. Atualizar disponibilidade do livro em livros.csv
                                        List<String[]> livros = CsvHandler.verificarECarregarArquivoCSV("livros.csv");
                                        for (int i = 0; i < livros.size(); i++) {
                                            String[] livro2 = livros.get(i);
                                            if (livro2.length > 1 && livro2[1].trim().equals(codigoLivro)) {
                                                livro2[4] = "true"; // Atualiza a disponibilidade para true
                                                livros.set(i, livro2);
                                                break;
                                            }
                                        }
                                        CsvHandler.escreverLinhas("livros.csv", livros);

                                    // Exiba mensagem de sucesso
                                    JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso.\nMulta paga.");

                                    // Recarregar a tabela com os livros atualizados
                                    carregarLivros();

                                    } 
                                } else {
                                    // Usuário concordou em pagar a multa
                                        // 1. Excluir linha de empréstimo em emprestimos.csv
                                        List<String[]> emprestimos_updt = CsvHandler.verificarECarregarArquivoCSV("emprestimos.csv");
                                        for (int i = 0; i < emprestimos_updt.size(); i++) {
                                            String[] emprestimo_updt = emprestimos_updt.get(i);
                                            if (emprestimo_updt.length > 2 && emprestimo_updt[2].trim().equals(codigoLivro)) {
                                                emprestimos_updt.remove(i);
                                                break;
                                            }
                                        }
                                        CsvHandler.escreverLinhas("emprestimos.csv", emprestimos_updt);

                                        // 2. Atualizar disponibilidade do livro em livros.csv
                                        List<String[]> livros = CsvHandler.verificarECarregarArquivoCSV("livros.csv");
                                        for (int i = 0; i < livros.size(); i++) {
                                            String[] livro2 = livros.get(i);
                                            if (livro2.length > 1 && livro2[1].trim().equals(codigoLivro)) {
                                                livro2[4] = "true"; // Atualiza a disponibilidade para true
                                                livros.set(i, livro2);
                                                break;
                                            }
                                        }
                                        CsvHandler.escreverLinhas("livros.csv", livros);

                                    // Exiba mensagem de sucesso
                                    JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso.");
                                    // Recarregar a tabela com os livros atualizados
                                    carregarLivros();
                                }
                                break; // Saia do loop após encontrar a correspondência
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhum livro selecionado para devolução.");
                    }
                } else {
                    // Lógica para lidar com a falta de dados
                    JOptionPane.showMessageDialog(null, "Dados de empréstimos ausentes ou formato incorreto.");
                }
            }
    
    // método que verifica existência
    private boolean verificarExistenciaMembro(String nomeMembro) {
        List<String[]> dadosMembros = CsvHandler.lerDados("membros.csv");
    
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
