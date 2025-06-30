import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;

public class TelaTabelaDePaginas extends JPanel{
    private Image backgroundImage;
    private DeliveryMachine jogo;
    private JLabel labelLoading;
    private Timer timer;

    public TelaTabelaDePaginas(DeliveryMachine jogo, TelaTLB TLB){
        this.jogo = jogo;
        backgroundImage = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/backgroundTabelaDePaginas.png").getImage();

        setLayout(null);
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(tamanhoTela);

        backgroundImage = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/backgroundTabelaDePaginas.png").getImage();

        // Painel que centraliza os elementos
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setOpaque(false);
        painelCentral.setBounds(0, 0, tamanhoTela.width, tamanhoTela.height);

        // Carrega ícone animado (GIF)
        ImageIcon loadingIcon = new ImageIcon("/home/mari/Development/project_java/delivery_machine/imagens/loading.gif");
        JLabel loadingLabel = new JLabel(loadingIcon);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setOpaque(false);
        conteudo.add(loadingLabel, BorderLayout.CENTER);

        painelCentral.add(conteudo, BorderLayout.CENTER);
        add(painelCentral);
        criaLetreiro();

    }

    public void iniciarBusca(Dimension tamanhoTela){
        int delay = 2000 + new Random().nextInt(4001);
        timer = new Timer(delay, e -> {
            ((Timer)e.getSource()).stop();
            JOptionPane.showMessageDialog(this, "Endereço virtual encontrado!");
            jogo.getResultado().criaInformacoes(2);
            jogo.mostrarTela("resultado");
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void criaLetreiro(){
        Font fontePersonalizada = null;

        try{
            fontePersonalizada = Font.createFont(Font.TRUETYPE_FONT,
                new File("/home/mari/Development/project_java/delivery_machine/fonte/VT323-Regular.ttf")).deriveFont(Font.BOLD, 150f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontePersonalizada);
        }catch(IOException | FontFormatException e){
            e.printStackTrace();
            fontePersonalizada = new Font("Monospaced", Font.BOLD, 150);
        }

        JLabel titulo = new JLabel("Procurando na Tabela de Páginas...", SwingConstants.CENTER){
            @Override
            protected void paintComponent(Graphics g){
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
        titulo.setBounds(0, 80, Toolkit.getDefaultToolkit().getScreenSize().width, 300);
        add(titulo);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}