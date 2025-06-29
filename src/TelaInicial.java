import java.awt.*;
import java.io.File;
import java.io.IOException;
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
        backgroundImage = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/backgroundTelaInicial.png").getImage();
    }

    private void criaBotao(Dimension tamanhoTela){
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
            JOptionPane.showMessageDialog(null, "endereco virtual gerado:" + jogo.getEnderecoVirtual());
            jogo.gerarEnderecoVirtual();
            jogo.gerarEnderecoFisico();
            jogo.mostrarTela("tlb");
        });

        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);
        painelCentral.add(botaoRedondo);
        add(painelCentral, BorderLayout.CENTER);
    }

    private void criaLetreiro(Dimension tamanhoTela){
        float tamanhoFonte = tamanhoTela.height * 0.12f;
        int deslocamentoX = tamanhoTela.width / 200;

        Font fonte = carregarFontePersonalizada(
            "/home/mari/Development/project_java/delivery_machine/fonte/VT323-Regular.ttf",
            tamanhoFonte
        );

        JLabel titulo = aplicarSombraTexto("▶ DELIVERY MACHINE ◀", fonte, 
            new Color(139, 0, 0, 180), new Color(200, 111, 58), deslocamentoX);

        int margemTopo = (int)(tamanhoTela.height * 0.15);
        titulo.setBorder(BorderFactory.createEmptyBorder(margemTopo, 0, 0, 0));
        titulo.setPreferredSize(new Dimension(tamanhoTela.width, (int)tamanhoFonte * 2));

        add(titulo, BorderLayout.NORTH);
    }

    private Font carregarFontePersonalizada(String caminho, float tamanho){
        try{
            Font fonte = Font.createFont(Font.TRUETYPE_FONT, new File(caminho)).deriveFont(Font.BOLD, tamanho);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonte);
            return fonte;
        }catch(IOException | FontFormatException e){
            e.printStackTrace();
            return new Font("Monospaced", Font.BOLD, (int)tamanho);
        }
    }

    private JLabel aplicarSombraTexto(String texto, Font fonte, Color corSombra, Color corTexto, int deslocamentoX){
        return new JLabel(texto, SwingConstants.CENTER){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                g2.setFont(fonte);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(texto)) / 2;
                int y = getBaseline(getWidth(), getHeight());

                g2.setColor(corSombra);
                g2.drawString(texto, x + deslocamentoX, y);

                g2.setColor(corTexto);
                g2.drawString(texto, x, y);

                g2.dispose();
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
