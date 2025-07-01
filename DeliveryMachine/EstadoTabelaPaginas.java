import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class EstadoTabelaPaginas implements EstadoDoJogo{
    private DeliveryMachine jogo;

    public EstadoTabelaPaginas(DeliveryMachine jogo){
        this.jogo = jogo;
    }

    @Override
    public void aoEntrar(){
        jogo.mostrarTela("tabelaPaginas");
        Dimension tamanho = Toolkit.getDefaultToolkit().getScreenSize();

        int delay = 2000 + new Random().nextInt(4001);
        Timer timer = new Timer(delay, e -> {
            ((Timer) e.getSource()).stop();
            JOptionPane.showMessageDialog(null, "Endereço encontrado");
            jogo.getResultado().criaInformacoes(2);
            jogo.setEstado(new EstadoResultado(jogo));
        });

        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void aoInteragir(){}
}
