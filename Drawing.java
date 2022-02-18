import java.awt.*;
import javax.swing.*;


public class Drawing extends JFrame {
    
    
    public Drawing(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        // this.setLocationRelativeTo(null);




        this.setVisible(true);
    }

    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawLine(10, 10, 450, 450);

        // g2D.clearRect(0, 0, 300, 300);
    }
}
