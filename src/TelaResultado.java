import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class TelaResultado extends JPanel{
    private DeliveryMachine jogo;
    private Image backgroundImage;

    public TelaResultado(DeliveryMachine jogo){
        this.jogo = jogo;
        setLayout(null);
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(tamanhoTela);

        backgroundImage = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/backgroundTelaResultado.png").getImage();

        criaLetreiro(tamanhoTela);
        criaBotao(tamanhoTela);
    }

    private void criaLetreiro(Dimension tamanhoTela) {
        Font fontePersonalizada;
        try {
            fontePersonalizada = Font.createFont(Font.TRUETYPE_FONT,
                new File("/home/mari/Development/project_java/delivery_machine/fonte/VT323-Regular.ttf")).deriveFont(Font.BOLD, 150f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontePersonalizada);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            fontePersonalizada = new Font("Monospaced", Font.BOLD, 150);
        }

        JLabel titulo = new JLabel("Endereço encontrado!", SwingConstants.CENTER) {
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
        titulo.setBounds(0, 80, tamanhoTela.width, 300);
        add(titulo);
    }

    private void criaBotao(Dimension tamanhoTela) {
        ImageIcon playIcon = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/restart.png"); // Ícone diferente?
        Image img = playIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        Icon iconRedondo = new ImageIcon(img);

        RoundButton botaoRedondo = new RoundButton(
            new Color(200, 111, 58),
            new Color(139, 0, 0, 180),
            iconRedondo
        );

        botaoRedondo.setPreferredSize(new Dimension(250, 250));

        botaoRedondo.addActionListener(e -> {
            jogo.gerarEnderecoVirtual(); // Gera um novo endereço para a próxima rodada
            jogo.mostrarTela("inicio");
        });

        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);
        painelCentral.setBounds(
            (tamanhoTela.width - 250) / 2,
            (tamanhoTela.height - 250) / 2,
            250, 250
        );
        painelCentral.add(botaoRedondo);
        add(painelCentral);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
