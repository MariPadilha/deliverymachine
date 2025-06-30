import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class TelaTabelaDePaginas extends JPanel{
    private Image backgroundImage;
    private DeliveryMachine jogo;
    private JLabel labelLoading;
    private Timer timer;

    public TelaTabelaDePaginas(DeliveryMachine jogo, TelaTLB TLB){
        this.jogo = jogo;
        backgroundImage = new ImageIcon("imagens/backgroundTabelaDePaginas.png").getImage();

        setLayout(null);
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(tamanhoTela);

        backgroundImage = new ImageIcon("imagens/backgroundTabelaDePaginas.png").getImage();

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setOpaque(false);
        painelCentral.setBounds(0, 0, tamanhoTela.width, tamanhoTela.height);

        ImageIcon loadingIcon = new ImageIcon("imagens/loading.gif");
        JLabel loadingLabel = new JLabel(loadingIcon);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setOpaque(false);
        conteudo.add(loadingLabel, BorderLayout.CENTER);

        painelCentral.add(conteudo, BorderLayout.CENTER);
        add(painelCentral);
        criaLetreiro(tamanhoTela);
    }

    public void iniciarBusca(Dimension tamanhoTela){
        int delay = 2000 + new Random().nextInt(4001);
        timer = new Timer(delay, e -> {
            ((Timer)e.getSource()).stop();
            JOptionPane.showMessageDialog(this, "Endereço encontrado");
            jogo.getResultado().criaInformacoes(2);
            jogo.mostrarTela("resultado");
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void criaLetreiro(Dimension tamanhoTela){
        JLabel titulo = LetreiroComSombra.criar(
            "Procurando na tabela de páginas...",
            tamanhoTela,
            0.08f,
            new Color(139, 0, 0, 180),
            new Color(200, 111, 58)
        );
        titulo.setBounds(0, (int)(tamanhoTela.height * 0.02), tamanhoTela.width, (int)(tamanhoTela.height * 0.2));
        add(titulo);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}