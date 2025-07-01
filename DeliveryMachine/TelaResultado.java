import java.awt.*;
import javax.swing.*;

public class TelaResultado extends JPanel{
    private DeliveryMachine jogo;
    private Image backgroundImage;
    private JPanel blocoCinza;

    public TelaResultado(DeliveryMachine jogo){
        this.jogo = jogo;
        setLayout(null);
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(tamanhoTela);

        backgroundImage = new ImageIcon("imagens/backgroundTelaResultado.png").getImage();

        criaLetreiro(tamanhoTela);
        criaBotao(tamanhoTela, 0.28, "imagens/playpreto.png", 1);
        criaBotao(tamanhoTela, 0.50, "imagens/restart.png", 2);
        criaBotao(tamanhoTela, 0.70, "imagens/x.png", 3);
        criaTextoInferiorFixo(tamanhoTela);
        criaBloco(tamanhoTela);
    }

    private JLabel criaLinhaTexto(String texto){
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Monospaced", Font.PLAIN, 50));
        return label;
    }

    public void criaInformacoes(int opcao){
        if(blocoCinza != null){
            blocoCinza.removeAll();
            blocoCinza.revalidate();
            blocoCinza.repaint();
        }

        String encontrado = switch (opcao){
            case 1 -> "TLB";
            case 2 -> "Tabela de Páginas";
            default -> "Desconhecido";
        };
        JLabel label1 = criaLinhaTexto("Endereço virtual: " + jogo.getEnderecoVirtual());
        JLabel label2 = criaLinhaTexto("Endereço físico: " + jogo.getEnderecoFisico());
        JLabel label3 = criaLinhaTexto("Encontrado em: " + encontrado);
        JLabel label4 = criaLinhaTexto("Tempo decorrido: " +  (int)(Math.random() * 10000 + 200) + " ms");

        blocoCinza.add(label1);
        blocoCinza.add(Box.createVerticalStrut(20));
        blocoCinza.add(label2);
        blocoCinza.add(Box.createVerticalStrut(20));
        blocoCinza.add(label3);
        blocoCinza.add(Box.createVerticalStrut(20));
        blocoCinza.add(label4);
    }

    private void criaBloco(Dimension tamanhoTela){
        JPanel sombra = new JPanel();
        sombra.setBackground(new Color(30,30,30));
        sombra.setOpaque(true);

        int largura = (int) (tamanhoTela.width * 0.65);
        int altura = (int) (tamanhoTela.height * 0.6);
        int xSombra = (tamanhoTela.width - largura) / 2 + 20;
        int ySombra = (tamanhoTela.height - altura) / 2 + 20;
        sombra.setBounds(xSombra, ySombra, largura, altura);
        add(sombra);

        blocoCinza = new JPanel();
        blocoCinza.setBackground(new Color(60,60,60));
        blocoCinza.setLayout(new BoxLayout(blocoCinza, BoxLayout.Y_AXIS));
        blocoCinza.setOpaque(true);
        blocoCinza.setBounds((tamanhoTela.width - largura) / 2, (tamanhoTela.height - altura) / 2, largura, altura);

        blocoCinza.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80)); // margem interna
        repaint();
        add(blocoCinza);
        add(sombra);
    }

    private void criaLetreiro(Dimension tamanhoTela){
        JLabel titulo = LetreiroComSombra.criar(
            "Endereço encontrado!",
            tamanhoTela,
            0.12f,
            new Color(139, 0, 0, 180),
            new Color(200, 111, 58)
        );
        titulo.setBounds(0, (int)(tamanhoTela.height * 0.02), tamanhoTela.width, (int)(tamanhoTela.height * 0.2));
        add(titulo);
    }

    private void criaBotao(Dimension tamanhoTela, double proporcaoX, String caminho, int opcao){
        EstadoInicial reiniciar = new EstadoInicial(jogo);
        ImageIcon playIcon = new ImageIcon(caminho);
        Image img = playIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        Icon iconRedondo = new ImageIcon(img);

        RoundButton botaoRedondo = new RoundButton(
            new Color(200, 111, 58),
            new Color(139, 0, 0, 180),
            iconRedondo
        );

        botaoRedondo.setPreferredSize(new Dimension(250, 250));

        botaoRedondo.addActionListener(e -> {
            jogo.gerarEnderecoVirtual();
            jogo.gerarEnderecoFisico();
            if(opcao == 1)
                jogo.interagirEstado();
            else if(opcao == 2)
                jogo.setEstado(reiniciar);
            else if(opcao == 3)
                System.exit(0);
        });
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);
        painelCentral.setBounds((int)(
            (tamanhoTela.width - 250)*proporcaoX),
            (int)((tamanhoTela.height - 250)*0.95),
            250, 250
        );
        painelCentral.add(botaoRedondo);
        add(painelCentral);
    }

    private JLabel criaTextoFixo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Monospaced", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void criaTextoInferiorFixo(Dimension tamanhoTela){
        JLabel continuar = criaTextoFixo("Continuar");
        JLabel reiniciar = criaTextoFixo("Reiniciar");
        JLabel parar = criaTextoFixo("Parar");

        int y = (int)(tamanhoTela.height * 0.75);
        int largura = tamanhoTela.width;

        continuar.setBounds((int)(largura * 0.265), y, 300, 40);
        reiniciar.setBounds((int)(largura * 0.475), y, 300, 40); 
        parar.setBounds((int)(largura * 0.665), y, 300, 40);     

        setLayout(null);
        add(continuar);
        add(reiniciar);
        add(parar);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
