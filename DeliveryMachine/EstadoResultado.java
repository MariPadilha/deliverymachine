public class EstadoResultado implements EstadoDoJogo{
    private DeliveryMachine jogo;

    public EstadoResultado(DeliveryMachine jogo){
        this.jogo = jogo;
    }

    @Override
    public void aoEntrar(){
        jogo.mostrarTela("resultado");
    }

    @Override
    public void aoInteragir(){
        jogo.gerarEnderecoVirtual();
        jogo.gerarEnderecoFisico();
        jogo.setEstado(new EstadoTLB(jogo));
    }
}
