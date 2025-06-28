import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class TelaInicial extends JPanel{
    private Image backgroundImage;
    private DeliveryMachine jogo;

    public TelaInicial(DeliveryMachine jogo){
        this.jogo = jogo;
        jogo.gerarEnderecoVirtual();
        criaBackground();
        criaLetreiro();
        criaBotao();
    }

    private void criaBackground(){
        setLayout(new BorderLayout());
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(tamanhoTela);

        backgroundImage = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/backgroundTelaInicial.png").getImage();
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
            JOptionPane.showMessageDialog(null, "endereco virtual gerado:" + jogo.getEnderecoVirtual());
            jogo.mostrarTela("tlb");
        });

        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);
        painelCentral.add(botaoRedondo);
        add(painelCentral, BorderLayout.CENTER);
    }

    private void criaLetreiro(){
        Font fontePersonalizada = null;

        try{
            fontePersonalizada = Font.createFont(Font.TRUETYPE_FONT,
                new File("/home/mari/Development/project_java/delivery_machine/fonte/VT323-Regular.ttf")).deriveFont(Font.BOLD, 300f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontePersonalizada);
        }catch(IOException | FontFormatException e){
            e.printStackTrace();
            fontePersonalizada = new Font("Monospaced", Font.BOLD, 300);
        }

        JLabel titulo = new JLabel("▶ DELIVERY MACHINE ◀", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                String texto = getText();
                FontMetrics fm = g2d.getFontMetrics(getFont());
                int textoLargura = fm.stringWidth(texto);
                int x = (getWidth() - textoLargura) / 2;
                int y = getBaseline(getWidth(), getHeight());

                g2d.setColor(new Color(139, 0, 0, 180));
                g2d.drawString(texto, x + 10, y);

                g2d.setColor(new Color(200, 111, 58));
                g2d.drawString(texto, x, y);

                g2d.dispose();
            }
        };

        titulo.setFont(fontePersonalizada);
        titulo.setOpaque(false);
        titulo.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));
        add(titulo, BorderLayout.NORTH);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
