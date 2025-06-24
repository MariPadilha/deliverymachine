import java.awt.*;
import javax.swing.*;

public class DeliveryMachine extends JFrame{
    private CardLayout layout;
    private JPanel container;

    public DeliveryMachine(){
        super("Delivery Machine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);

        layout = new CardLayout();
        container = new JPanel(layout);

        container.add(new TelaInicial(this), "inicial");
        container.add(new TelaTLB(this), "tlb");
        container.add(new TelaTabelaDePaginas(this), "tabelaDePaginas");
        add(container);
        setVisible(true);
    }

    public void mostrarTela(String nome) {
        layout.show(container, nome);
    }

    public void adicionarTela(String nome, JPanel tela) {
        container.add(tela, nome);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeliveryMachine());
    }
}
