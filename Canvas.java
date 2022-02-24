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
        points = new int[2][12];
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
        if(x<500 && x>=0 && y<500 && y>=0)
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
        ready();
        if(this.isLine){
            if(this.isDDA){
                plotLineDDA(points[0][0], points[1][0],
                            points[0][1], points[1][1]);
            }
            else if(this.isBresenham){
                plotLineBresenham(points[0][0], points[1][0],
                                  points[0][1], points[1][1]);
            }
        }
        else if(this.isCircle){
            plotCircle(points[0][0], points[1][0],
                       points[0][1], points[1][1]);
        }
        else if(this.isPolygon){
            plotPolygon();
        }
        repaint();
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

        renderPoint((int)Math.round(x), (int)Math.round(y));
        for(int i=0; i<steps; i++){
            x+=addX;
            y+=addY;
            renderPoint((int)Math.round(x), (int)Math.round(y));
        }
    }

    private void plotLineBresenham(int x1, int y1, int x2, int y2){
        int dx, dy, x, y, c1, c2, p, addX, addY;

        dx = x2 - x1;
        dy = y2 - y1;

        if(dx>=0)addX = 1;
        else{addX = -1; dx = -1*dx;}

        if(dy>=0)addY = 1;
        else{addY = -1; dy = -1*dy;}

        x=x1; y=y1;
        renderPoint(x, y);

        if(dy<dx){
            p = 2*dy - dx;
            c1 = 2*dy;
            c2 = 2*(dy-dx);
            for(int i=0; i<dx; i++){
                x += addX;
                if(p<0) p += c1;
                else{y += addY; p += c2;}
                renderPoint(x, y);
            }
        }
        else{
            p = 2*dx -dy;
            c1 = 2*dx;
            c2 = 2*(dx-dy);
            for(int i=0; i<dy; i++){
                y += addY;
                if(p<0) p += c1;
                else{x += addX; p += c2;}
                renderPoint(x, y);
            }
        }
    }

    private void renderCirclePoints(int xc, int yc, int x, int y){
        renderPoint(xc+x, yc+y);
        renderPoint(xc-x, yc+y);
        renderPoint(xc+x, yc-y);
        renderPoint(xc-x, yc-y);
        renderPoint(xc+y, yc+x);
        renderPoint(xc-y, yc+x);
        renderPoint(xc+y, yc-x);
        renderPoint(xc-y, yc-x);
    }

    private void plotCircle(int xc, int yc, int x2, int y2){
        int x, y, p, r;

        //Calcular raio
        r = (int)Math.round(Math.sqrt((x2-xc)*(x2-xc) + (y2-yc)*(y2-yc)));

        x = 0; y = r; p = 3 - 2*r;
        renderCirclePoints(xc, yc, x, y);
        while(x<y){
            if(p<0) p += 4*x + 6;
            else{ p += 4*(x-y) + 10; y-=1;}
            x+=1;
            renderCirclePoints(xc, yc, x, y);
        }
        
    }

    private void plotPolygon(){
        if(this.isDDA){
            for(int i=1; i<this.insertedPoints; i++){
                plotLineDDA(points[0][i-1], points[1][i-1],
                            points[0][i], points[1][i]);
            }
            plotLineDDA(points[0][insertedPoints-1], points[1][insertedPoints-1],
                            points[0][0], points[1][0]);
        }
        else if(this.isBresenham){
            for(int i=1; i<this.insertedPoints; i++){
                plotLineBresenham(points[0][i-1], points[1][i-1],
                            points[0][i], points[1][i]);
            }
            plotLineBresenham(points[0][insertedPoints-1], points[1][insertedPoints-1],
                            points[0][0], points[1][0]);
        }
    }
}
