public class Fila{
    private PostIt[] elementos;
    private int capacidade;
    private int inicio;
    private int fim;
    private int tamanho;

    public Fila(int capacidade){
        this.capacidade = capacidade;
        elementos = new PostIt[capacidade];
        inicio = 0;
        fim = -1;
        tamanho = 0;
    }

    public void enqueue(PostIt elemento){
        if(isFull()){
            throw new IllegalStateException("A fila está cheia");
        }

        fim = (fim + 1) % capacidade;
        elementos[fim] = elemento;
        tamanho++;
    }

    public PostIt dequeue(){
        if(isEmpty()){
            throw new IllegalStateException("A fila está vazia");
        }

        PostIt elementoRemovido = elementos[inicio];
        elementos[inicio] = null;
        inicio = (inicio + 1) % capacidade;
        tamanho--;

        return elementoRemovido;
    }

    public PostIt front(){
        if(isEmpty()){
            throw new IllegalStateException("A fila está vazia");
        }

        return elementos[inicio];
    }

    public boolean isEmpty(){
        return tamanho == 0;
    }

    public boolean isFull(){
        return tamanho == capacidade;
    }
}