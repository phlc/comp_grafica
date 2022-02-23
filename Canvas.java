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

    //Metodos
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(buffer, 0, 0, this);
        g2d.dispose();
    }

    public void drawPoint(int x, int y){
        renderPoint(x, y);
        if(x>0) renderPoint(x-1, y);
        if(y>0) renderPoint(x, y-1);
        if(x<499) renderPoint(x+1, y);
        if(y<499) renderPoint(x, y+1);

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

    private void renderPoint(int x, int y){
        buffer.setRGB(x, y, color);
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

    public void plot(){
        if(this.isLine){
            if(this.isDDA){
                plotLineDDA(points[0][0], points[0][1],
                            points[1][0], points[1][1]);
            }
            else if(this.isBresenham){
                plotLineBresenham();
            }
        }
        else if(this.isCircle){
            plotCircle();
        }
        else if(this.isPolygon){
            plotPolygon();
        }

    }

    private void plotLineDDA(int x1, int y1, int x2, int y2){
        int dx, dy, steps;
        double addX, addY, x, y;

        dx = x2 - x1;
        dy = y2 - y1;

        if(Math.abs(dx) > Math.abs(dy)) steps = Math.abs(dx);
        else steps = Math.abs(dy);

        addX = (double)dx/steps;
        addY = (double)dy/steps;

        x=x1; y=y1;


        ready();
        renderPoint((int)Math.round(x), (int)Math.round(y));
        for(int i=0; i<steps; i++){
            x+=addX;
            y+=addY;
            renderPoint((int)Math.round(x), (int)Math.round(y));
        }
    }

    private void plotLineBresenham(){

    }

    private void plotCircle(){

    }

    private void plotPolygon(){

    }
}
