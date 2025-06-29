import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.*;
import javax.swing.border.Border;

public class TelaTLB extends JPanel{
    private Image backgroundImage;
    private DeliveryMachine jogo;
    private List<PostIt> postIts = new ArrayList<>();
    private Queue<PostIt> filaTLB = new LinkedList<>();

    public TelaTLB(DeliveryMachine jogo){
        this.jogo = jogo;
        
        setLayout(null);
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(tamanhoTela);
        
        criaBackground(tamanhoTela);
        criaPostIt(tamanhoTela);

        botaoInserirBuscar();
    }

    private void botaoInserirBuscar(){
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        int largura = tamanhoTela.width / 6;
        int altura = tamanhoTela.height / 15;

        Font fonteBotao = new Font("Monospaced", Font.BOLD, tamanhoTela.height / 45);
        Color corTexto = Color.WHITE;
        Color corFundo = new Color(200, 111, 58);
        Color corHover = new Color(139, 0, 0, 180);

        // ⬇️ AQUI: Criação da borda com "sombra"
        Border sombra = BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(139, 0, 0, 180));
        Border preenchimento = BorderFactory.createEmptyBorder(10, 20, 10, 20);
        Border bordaComposta = BorderFactory.createCompoundBorder(sombra, preenchimento);
        // Inserir TLB
        JButton botaoInserir = new JButton("Inserir TLB");
        botaoInserir.setFont(fonteBotao);
        botaoInserir.setForeground(corTexto);
        botaoInserir.setBackground(corFundo);
        botaoInserir.setFocusPainted(false);
        botaoInserir.setPreferredSize(new Dimension(largura, altura));
        botaoInserir.setBorder(bordaComposta);
        botaoInserir.setOpaque(true);

        botaoInserir.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                botaoInserir.setBackground(corHover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt){
                botaoInserir.setBackground(corFundo);
            }
        });

        botaoInserir.addActionListener(e -> {
            String endVirt = String.format("0x%X", (int)(Math.random() * 256));
            String endFis = String.format("0x%X", (int)(Math.random() * 256));
            inserirNaTLB(endVirt, endFis);
        });

        // Buscar endereço
        JButton botaoBuscar = new JButton("Buscar endereço");
        botaoBuscar.setFont(fonteBotao);
        botaoBuscar.setForeground(corTexto);
        botaoBuscar.setBackground(corFundo);
        botaoBuscar.setFocusPainted(false);
        botaoBuscar.setPreferredSize(new Dimension(largura, altura));
        botaoBuscar.setBorder(bordaComposta);
        botaoBuscar.setOpaque(true);

        botaoBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botaoBuscar.setBackground(corHover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botaoBuscar.setBackground(corFundo);
            }
        });

        botaoBuscar.addActionListener(e -> {
            String endVirt = jogo.getEnderecoVirtual();

            if (verificar(endVirt)) {
                JOptionPane.showMessageDialog(this, "HIT na TLB para " + endVirt);
                jogo.mostrarTela("resultado");
            } else {
                JOptionPane.showMessageDialog(this, "MISS! Inserindo " + endVirt);
                jogo.mostrarTela("tabelaPaginas");
                jogo.getTabelaPaginas().iniciarBusca();
            }
        });

        JPanel painelBotoes = new JPanel();
        painelBotoes.setOpaque(false);
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelBotoes.add(Box.createHorizontalGlue());
        painelBotoes.add(botaoInserir);
        painelBotoes.add(Box.createHorizontalStrut(tamanhoTela.width / 50));
        painelBotoes.add(botaoBuscar);
        painelBotoes.add(Box.createHorizontalGlue());


        // Painel wrapper para centralizar na parte inferior
        JPanel painelWrapper = new JPanel();
        painelWrapper.setLayout(new BorderLayout());
        painelWrapper.setOpaque(false);
        painelWrapper.add(painelBotoes, BorderLayout.CENTER);

        // Defina posição e tamanho manualmente
        int larguraWrapper = tamanhoTela.width;
        int alturaWrapper = altura + 60; // Altura do botão + margem
        int xWrapper = 0;
        int yWrapper = tamanhoTela.height - alturaWrapper - 100;

        painelWrapper.setBounds(xWrapper, yWrapper, larguraWrapper, alturaWrapper);
        add(painelWrapper);

    }

    private boolean verificar(String enderecoVirtual){
        for(PostIt postIt : filaTLB){
            if(postIt.getEnderecoVirtual().equals(enderecoVirtual)){
                return true;
            }
        }
        return false;
    }

    private void criaPostIt(Dimension tamanhoTela){
        double[][] proporcoes = {
            {0.21, 0.27}, {0.305, 0.285}, {0.42, 0.28}, {0.52, 0.28}, {0.64, 0.275},
            {0.21, 0.45}, {0.30, 0.45}, {0.39, 0.45}, {0.48, 0.45}, {0.575, 0.45}, {0.66, 0.45},
            {0.20, 0.63}, {0.32, 0.64}, {0.42, 0.63}, {0.51, 0.63}, {0.64, 0.64}
        };

        for(int i = 0; i < 16; i++){
            PostIt postIt = new PostIt("", "", (int)(tamanhoTela.width * proporcoes[i][0]), (int)(tamanhoTela.height * proporcoes[i][1]));
            add(postIt);
            postIts.add(postIt);
        }
    }

    private void criaBackground(Dimension tamanhoTela){
        backgroundImage = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/backgroundTLB.png").getImage();
    }

    public void inserirNaTLB(String enderecoVirtual, String enderecoFisico){
        PostIt novo;

        if(filaTLB.size() < 16){
            novo = postIts.get(filaTLB.size());
        } else {
            PostIt maisAntigo = filaTLB.poll();
            novo = maisAntigo;
        }

        novo.atualizar(enderecoVirtual, enderecoFisico);
        novo.repaint();
        filaTLB.add(novo);
    }  

    public void resetar(){
        for(PostIt postIt : postIts){
            postIt.atualizar("", "");
        }
        filaTLB.clear();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
