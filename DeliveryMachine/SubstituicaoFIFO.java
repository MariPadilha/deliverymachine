import java.util.List;
import java.util.Queue;

public class SubstituicaoFIFO implements PoliticaSubstituicaoTLB{
    @Override
    public PostIt escolher(Queue<PostIt> fila, List<PostIt> postIts){
        return fila.poll();
    }   
}
