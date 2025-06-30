import java.awt.*;
import javax.swing.*;

public class TelaInicial extends JPanel{
    private Image backgroundImage;
    private DeliveryMachine jogo;

    public TelaInicial(DeliveryMachine jogo){
        this.jogo = jogo;

        setLayout(new BorderLayout());
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(tamanhoTela);

        criaBackground(tamanhoTela);
        criaLetreiro(tamanhoTela);
        criaBotao(tamanhoTela);
    }

    private void criaBackground(Dimension tamanhoTela){
        backgroundImage = new ImageIcon("imagens/backgroundTelaInicial.png").getImage();
    }

    private void criaBotao(Dimension tamanhoTela){
        int tamanhoBotao = tamanhoTela.width / 10;
        ImageIcon playIcon = new ImageIcon("imagens/play.png");
        Image img = playIcon.getImage().getScaledInstance(tamanhoBotao / 2, tamanhoBotao / 2, Image.SCALE_SMOOTH);
        Icon iconRedondo = new ImageIcon(img);

        RoundButton botaoRedondo = new RoundButton(
            new Color(200, 111, 58),
            new Color(139, 0, 0, 180),
            iconRedondo
        );

        botaoRedondo.setPreferredSize(new Dimension(tamanhoBotao, tamanhoBotao));

        botaoRedondo.addActionListener(e -> {
            jogo.gerarEnderecoFisico();
            jogo.gerarEnderecoVirtual();
            JOptionPane.showMessageDialog(null, "endereço virtual gerado: " + jogo.getEnderecoVirtual());
            jogo.getTLB().criaLetreiro(tamanhoTela, jogo.getEnderecoVirtual());
            jogo.mostrarTela("tlb");
        });

        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);
        painelCentral.add(botaoRedondo);
        add(painelCentral, BorderLayout.CENTER);
    }

    private void criaLetreiro(Dimension tamanhoTela){
        JLabel titulo = LetreiroComSombra.criar(
            "DELIVERY MACHINE",
            tamanhoTela,
            0.12f,
            new Color(139, 0, 0, 180),
            new Color(200, 111, 58)
        );
        add(titulo, BorderLayout.NORTH);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}