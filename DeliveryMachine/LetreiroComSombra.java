import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class LetreiroComSombra{

    public static JLabel criar(String texto, Dimension tamanhoTela, float proporcaoAlturaFonte, Color corSombra, Color corTexto){

        float tamanhoFonte = tamanhoTela.height * proporcaoAlturaFonte;
        int deslocamentoX = tamanhoTela.width / 200;

        Font fonte = carregarFonte("fonte/VT323-Regular.ttf", tamanhoFonte);
        JLabel label = desenharSombra(texto, fonte, corSombra, corTexto, deslocamentoX);

        int margemTopo = (int)(tamanhoTela.height * 0.09);
        label.setBorder(BorderFactory.createEmptyBorder(margemTopo, 0, 0, 0));
        label.setPreferredSize(new Dimension(tamanhoTela.width, (int)tamanhoFonte * 2));

        return label;
    }

    private static Font carregarFonte(String caminho, float tamanho){
        try{
            Font fonte = Font.createFont(Font.TRUETYPE_FONT, new File(caminho)).deriveFont(Font.BOLD, tamanho);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonte);
            return fonte;
        }catch(IOException | FontFormatException e){
            e.printStackTrace();
            return new Font("Monospaced", Font.BOLD, (int)tamanho);
        }
    }

    private static JLabel desenharSombra(String texto, Font fonte, Color corSombra, Color corTexto, int deslocamentoX){
        return new JLabel(texto, SwingConstants.CENTER){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                g2.setFont(fonte);
                String textoAtual = getText();
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(textoAtual)) / 2;
                int y = getBaseline(getWidth(), getHeight());

                g2.setColor(corSombra);
                g2.drawString(textoAtual, x + deslocamentoX, y);

                g2.setColor(corTexto);
                g2.drawString(textoAtual, x, y);

                g2.dispose();
            }
        };
    }
}