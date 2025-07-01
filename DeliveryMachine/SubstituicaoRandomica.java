import java.util.*;

public class SubstituicaoRandomica implements PoliticaSubstituicaoTLB{
    @Override
    public PostIt escolher(Queue<PostIt> fila, List<PostIt> postIts){
        int index = new Random().nextInt(fila.size());
        return new ArrayList<>(fila).get(index);
    }
}
