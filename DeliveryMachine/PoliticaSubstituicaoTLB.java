import java.util.List;
import java.util.Queue;

public interface PoliticaSubstituicaoTLB{
    PostIt escolher(Queue<PostIt> fila, List<PostIt> postIts);
}
