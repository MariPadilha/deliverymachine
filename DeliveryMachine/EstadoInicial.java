
public class EstadoInicial implements EstadoDoJogo{
    private DeliveryMachine jogo;

    public EstadoInicial(DeliveryMachine jogo){
        this.jogo = jogo;
    }

    @Override
    public void aoEntrar(){
        jogo.getTLB().resetar();
        jogo.mostrarTela("inicio");
    }

    @Override
    public void aoInteragir(){
        jogo.gerarEnderecoFisico();
        jogo.gerarEnderecoVirtual();
        jogo.setEstado(new EstadoTLB(jogo));
    }
}
