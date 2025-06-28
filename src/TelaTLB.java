import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.*;

public class TelaTLB extends JLayeredPane{

    private DeliveryMachine jogo;
    private List<PostIt> postIts = new ArrayList<>();
    private final int TLB_TAMANHO = 16;
    private Queue<PostIt> filaTLB = new LinkedList<>();

    public TelaTLB(DeliveryMachine jogo){
        this.jogo = jogo;
        
        setLayout(null);
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(tamanhoTela);
        
        criaBackground(tamanhoTela);
        criaPostIt(tamanhoTela);

        JButton teste = new JButton("Inserir TLB");
        teste.setBounds(50, 50, 150, 40);
        teste.addActionListener(e -> {
            String endVirt = String.format("0x%X", (int)(Math.random() * 256));
            String endFis = String.format("0x%X", (int)(Math.random() * 256));
            inserirNaTLB(endVirt, endFis);
        });
        add(teste, JLayeredPane.MODAL_LAYER);

        
        JButton testar = new JButton("Buscar endereço");
        testar.setBounds(220, 50, 200, 40);
        testar.addActionListener(e -> {
            String endVirt = jogo.getEnderecoVirtual();

            if(verificar(endVirt)){
                JOptionPane.showMessageDialog(this, "HIT na TLB para " + endVirt);
            }else{
                JOptionPane.showMessageDialog(this, "MISS! Inserindo " + endVirt);
                jogo.mostrarTela("tabelaPaginas");
                jogo.getTabelaPaginas().iniciarBusca();
            }
        });
        add(testar, JLayeredPane.MODAL_LAYER);

        //criaBotao();
    }

    private boolean verificar(String enderecoVirtual){
        for(PostIt postIt : filaTLB){
            if(postIt.getEnderecoVirtual().equals(enderecoVirtual)){
                return true;
            }
        }
        return false;
    }

    private void criaBotao(){
        ImageIcon playIcon = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/play.png");
        Image img = playIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        Icon iconRedondo = new ImageIcon(img);

        RoundButton botaoRedondo = new RoundButton(
            new Color(200, 111, 58),
            new Color(139, 0, 0, 180),
            iconRedondo
        );

        botaoRedondo.setPreferredSize(new Dimension(250, 250));

        botaoRedondo.addActionListener(e -> {
            System.out.println("Indo para Tabela de Páginas...");
            jogo.mostrarTela("tabelaDePaginas");
        });

        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();

        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);
        painelCentral.setBounds(
            (tamanhoTela.width - 250) / 2,
            (tamanhoTela.height - 250) / 2,
            250, 250
        );
        painelCentral.add(botaoRedondo);

        add(painelCentral, JLayeredPane.MODAL_LAYER);
    }

    private void criaPostIt(Dimension tamanhoTela){
        double[][] proporcoes = {
            {0.21, 0.27}, {0.305, 0.29}, {0.42, 0.28}, {0.52, 0.28}, {0.64, 0.28},
            {0.21, 0.46}, {0.30, 0.46}, {0.39, 0.46}, {0.48, 0.46}, {0.575, 0.46}, {0.66, 0.46},
            {0.20, 0.65}, {0.32, 0.66}, {0.42, 0.65}, {0.51, 0.65}, {0.64, 0.66}
        };

        for(int i = 0; i < 16; i++){
            PostIt postIt = new PostIt("", "", (int)(tamanhoTela.width * proporcoes[i][0]), (int)(tamanhoTela.height * proporcoes[i][1]));
            add(postIt, JLayeredPane.PALETTE_LAYER);
            postIts.add(postIt);
        }
    }

    private void criaBackground(Dimension tamanhoTela){
        Image backgroundImage;
        JLabel backgroundLabel;


        backgroundImage = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/backgroundTLB.png").getImage();

        backgroundLabel = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundLabel.setBounds(0, 0, tamanhoTela.width, tamanhoTela.height);
        add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
    }

    public void inserirNaTLB(String enderecoVirtual, String enderecoFisico){
        PostIt novo;

        if(filaTLB.size() < TLB_TAMANHO){
            novo = postIts.get(filaTLB.size());
        }else{
            PostIt maisAntigo = filaTLB.poll();
            maisAntigo.atualizar("", "");
            novo = maisAntigo;
        }

        novo.atualizar(enderecoVirtual, enderecoFisico);
        filaTLB.add(novo);
    }

    public void resetar(){
        for(PostIt postIt : postIts){
            postIt.atualizar("", "");
        }
    }
} 