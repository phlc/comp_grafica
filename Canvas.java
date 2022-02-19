/*
Computação Gráfica
Implementação Algoritmos Unidade 1
Pedro Henrique Lima Carvalho - 651230
*/

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

//Classe do Canvas para Desenho
class Canvas extends JPanel{
    //Atributos
    public int points[][];
    public int maxPoints;
    public int insertedPoints;
    public BufferedImage buffer;
    public int color;
    public boolean isLine, isCircle, isPolygon;
    public boolean isDDA, isBresenham;

    

    //Construtores
    public Canvas(){
        points = new int[2][30];
        insertedPoints=0;
        maxPoints = 2;
        isLine = isDDA = true;
        isCircle = isPolygon = isBresenham = false;
        color = Color.black.getRGB();
        buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        clear();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(buffer, 0, 0, this);
        g2d.dispose();
    }

    public void drawPoint(int x, int y){
        buffer.setRGB(x, y, color);
        if(x>0) buffer.setRGB(x-1, y, color);
        if(y>0) buffer.setRGB(x, y-1, color);
        if(x<499) buffer.setRGB(x+1, y, color);
        if(y<499) buffer.setRGB(x, y+1, color);

        repaint();
    }

    public void addPoint(int x, int y){
        if(insertedPoints < maxPoints){
            points[0][insertedPoints] = x;
            points[1][insertedPoints] = y;
            insertedPoints++;

            drawPoint(x, y);
        }
        else{
            String type = "";
            if(isLine) type = "line";
            else if(isCircle) type = "circle";
            else if(isPolygon) type = "polygon (12 points)";
            JOptionPane.showMessageDialog(this, ("Too many points for a "+type+"\nTry plotting"));
        }
    }

    public void clear(){
        for(int i=0; i<500; i++){
            for(int j=0; j<500; j++){
                buffer.setRGB(i, j, Color.gray.getRGB());
            }
        }
        insertedPoints=0;
        repaint();
    }

    public void ready(){
        for(int i=0; i<500; i++){
            for(int j=0; j<500; j++){
                buffer.setRGB(i, j, Color.white.getRGB());
            }
        }
        repaint();
    }
}
