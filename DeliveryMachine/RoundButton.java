import java.awt.*;
import javax.swing.*;

class RoundButton extends JButton{
    private Color fillColor;
    private Color shadowColor;
    private Icon icon;

    public RoundButton(Color fillColor, Color shadowColor, Icon icon){
        this.fillColor = fillColor;
        this.shadowColor = shadowColor;
        this.icon = icon;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(300, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        g2.setColor(shadowColor);
        g2.fillOval(5, 5, w - 10, h - 10);

        g2.setColor(fillColor);
        g2.fillOval(0, 0, w - 10, h - 10);

        if (icon != null) {
            int iconW = icon.getIconWidth();
            int iconH = icon.getIconHeight();
            int x = (w - iconW) / 2 - 5;
            int y = (h - iconH) / 2 - 5;
            icon.paintIcon(this, g2, x, y);
        }

        g2.dispose();
    }
}