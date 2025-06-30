import java.awt.*;
import javax.swing.*;

public class PostIt extends JLabel{
    private String enderecoVirtual;
    private String enderecoFisico;
    private int X;
    private int Y;

    public PostIt(String enderecoVirtual, String enderecoFisico, int x, int y){
        this.enderecoVirtual = enderecoVirtual;
        this.enderecoFisico = enderecoFisico;
        this.X = x;
        this.Y = y;
        criaLabel(x, y);
    }

    public void atualizar(String virtual, String fisica){
        this.enderecoVirtual = virtual;
        this.enderecoFisico = fisica;
        if(fisica.isEmpty() && virtual.isEmpty())
            atualizarTexto(false);
        else
            atualizarTexto(true);
    }

    public String getEnderecoVirtual(){
        return enderecoVirtual;
    }

    public String getEnderecoFisico(){
        return enderecoFisico;
    }

    public int getX(){
        return X;
    }

    public int getY(){
        return Y;
    }

    private void criaLabel(int x, int y){
        setBounds(x, y, 150, 80);
        setForeground(Color.BLACK);
        setFont(new Font("Monospaced", Font.BOLD, 30));
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(false);
        atualizarTexto(false);
    }

    private void atualizarTexto(boolean mostrar){
        if(mostrar)
            setText("<html>V: " + enderecoVirtual + "<br>F: " + enderecoFisico + "</html>");
        else
            setText("");
    }
}
