import java.awt.*;
import javax.swing.*;

public class DeliveryMachine extends JFrame{
    private CardLayout layout;
    private JPanel container;
    private String enderecoVirtual;
    private String enderecoFisico;
    private TelaInicial Inicio;
    private TelaTLB TLB;
    private TelaTabelaDePaginas TabelaPaginas;
    private TelaResultado Resultado;


    public DeliveryMachine(){
        super("Delivery Machine");
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);

        layout = new CardLayout();
        container = new JPanel(layout);
        Inicio = new TelaInicial(this);
        TLB = new TelaTLB(this);
        TabelaPaginas = new TelaTabelaDePaginas(this, TLB);
        Resultado = new TelaResultado(this);
        
        criarNavegabilidade();
    }

    private void criarNavegabilidade(){
        container.add(Inicio, "inicio");
        container.add(TLB, "tlb");
        container.add(TabelaPaginas, "tabelaPaginas");
        container.add(Resultado, "resultado");        
        add(container);
        setVisible(true);
    }

    public void gerarEnderecoVirtual(){
        enderecoVirtual = String.format("0x%X", (int)(Math.random() * 256));
    }

    public void gerarEnderecoFisico(){
        enderecoFisico = String.format("0x%X", (int)(Math.random() * 256));
    }

    public void setEnderecoFisico(String enderecoFisico){
        this.enderecoFisico = enderecoFisico;
    }
  
    public String getEnderecoVirtual(){
        return enderecoVirtual;
    }

    public String getEnderecoFisico(){
        return enderecoFisico;
    }

    public TelaTabelaDePaginas getTabelaPaginas(){
        return TabelaPaginas;
    }

    public TelaResultado getResultado(){
        return Resultado;
    }

    public TelaTLB getTLB(){
        return TLB;
    }
    
    public void mostrarTela(String nome){
        layout.show(container, nome);
    }

    public void reiniciar(){
        TLB.resetar();
        mostrarTela("inicio");
    }

    public void continuar(Dimension tamanhoTela){
        gerarEnderecoFisico();
        gerarEnderecoVirtual();
        TLB.criaLetreiro(tamanhoTela, enderecoVirtual);
        mostrarTela("tlb");
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new DeliveryMachine());
    }
}
