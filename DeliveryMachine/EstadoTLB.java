import java.awt.*;
import javax.swing.*;

public class EstadoTLB extends JPanel implements EstadoDoJogo{
    private DeliveryMachine jogo;

    public EstadoTLB(DeliveryMachine jogo){
        this.jogo = jogo;
    }

    @Override
    public void aoEntrar(){
        Dimension tamanho = Toolkit.getDefaultToolkit().getScreenSize();
        jogo.getTLB().criaLetreiro(tamanho, jogo.getEnderecoVirtual());
        jogo.mostrarTela("tlb");
    }

    @Override
    public void aoInteragir(){
        String endVirt = jogo.getEnderecoVirtual();
        boolean hit = jogo.getTLB().verificar(endVirt);

        if(hit){
            JOptionPane.showMessageDialog(null, "HIT na TLB para " + endVirt);
            jogo.getResultado().criaInformacoes(1);
            jogo.setEstado(new EstadoResultado(jogo));
        }else{
            JOptionPane.showMessageDialog(null, "MISS! Indo para a tabela de páginas");
            jogo.getTLB().inserirNaTLB(endVirt, jogo.getEnderecoFisico());
            jogo.setEstado(new EstadoTabelaPaginas(jogo));
        }
    }
}
