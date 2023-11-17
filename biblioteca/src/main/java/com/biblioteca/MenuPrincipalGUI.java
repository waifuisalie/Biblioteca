package com.biblioteca;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuPrincipalGUI extends JFrame {

    private static final String BACKGROUND_IMAGE_PATH = "/home/waifuisalie/Documents/biblioteca/biblioteca/src/main/java/com/biblioteca/biblioteca_sala_pucpr.jpg"; 

    public MenuPrincipalGUI() {
        // Configurações básicas da janela
        setTitle("Biblioteca :3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Centraliza a janela na tela 
        

        // Crie um painel com imagem de fundo
        BackgroundPanel panel = new BackgroundPanel();
        panel.setLayout(new GridBagLayout()); // Utiliza GridBagLayout

        // Crie os botões para as opções do menu
        JButton cadastrarLivroButton = new JButton("Cadastrar Livro");
        JButton devolverLivroButton = new JButton("Devolver Livro");
        JButton emprestarLivroButton = new JButton("Emprestar Livro"); // Novo botão
        JButton buscarLivroButton = new JButton("Buscar Livro");
        JButton cadastrarMembroButton = new JButton("Cadastrar Membro");
        JButton sairButton = new JButton("Sair");

        // Define um tamanho preferido para os botões
        Dimension buttonSize = new Dimension(200, 40);
        cadastrarLivroButton.setPreferredSize(buttonSize);
        devolverLivroButton.setPreferredSize(buttonSize);
        emprestarLivroButton.setPreferredSize(buttonSize);
        buscarLivroButton.setPreferredSize(buttonSize);
        cadastrarMembroButton.setPreferredSize(buttonSize);
        sairButton.setPreferredSize(buttonSize);

        // configuração do layout usando GridBagConsraints para o painel
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; // coluna 0
        gbc.gridy = 0; // linha 0

        // define as margens
        gbc.insets = new Insets(10, 0, 10, 0); // Adiciona espaço entre os botões
        panel.add(cadastrarLivroButton, gbc);

        // muda a linha para 1
        gbc.gridy = 1;
        panel.add(devolverLivroButton, gbc);

        // muda a linha para 2        
        gbc.gridy = 2;
        panel.add(emprestarLivroButton, gbc);

        gbc.gridy = 3;
        panel.add(buscarLivroButton, gbc);

        gbc.gridy = 4;
        panel.add(cadastrarMembroButton, gbc);

        gbc.gridy = 5;
        panel.add(sairButton, gbc);

        // Adicione o painel à janela
        add(BorderLayout.CENTER, panel);

        // Configure as ações dos botões
        cadastrarLivroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // abre a interface gráfica para a cadastrar de livros
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        CadastroLivroGUI cadastroLivroGUI = new CadastroLivroGUI();
                        cadastroLivroGUI.setVisible(true);
                    }
                });
            }
        });
        
        devolverLivroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // abre a interface gráfica para a devoluçao de livros
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        DevolverLivroGUI devolverLivroGUI = new DevolverLivroGUI();
                        devolverLivroGUI.setVisible(true);
                    }
                });
            }
        });

        emprestarLivroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Criar e exibir a janela de empréstimo
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        EmprestarLivroGUI emprestarLivroGUI = new EmprestarLivroGUI();
                        emprestarLivroGUI.setVisible(true);
                    }
                });
            }
        });

        buscarLivroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // abre a interface para buscar livros
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        BuscaLivroGUI buscaLivroGUI = new BuscaLivroGUI();
                        buscaLivroGUI.setVisible(true);
                    }
                });
            }
        });

        cadastrarMembroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // abre a interface gráfica para cadastrar membro
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        CadastroMembroGUI cadastroMembroGUI = new CadastroMembroGUI();
                        cadastroMembroGUI.setVisible(true);
                    }
                });
            }
        });

        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // classe interna para exibir uma imagem de fundo
    private class BackgroundPanel extends JPanel {
        private Image background;

        // construtor que carrega a imagem de fundo
        public BackgroundPanel() {
            try {
                background = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // sobreescreve o método paintComponent para desenhar a imagem de fundo
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        }
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuPrincipalGUI menuGUI = new MenuPrincipalGUI();
                menuGUI.setVisible(true);
            }
        });
    }
}
