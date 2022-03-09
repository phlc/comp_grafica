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
    public int minX, maxX, minY, maxY;
    public double u1, u2;
    public int clipPointsInserted;
    public int insertedPoints;
    public BufferedImage buffer;
    public int color;
    public boolean isLine, isCircle, isPolygon, isCohen;
    public boolean isDDA, isBresenham;

    

    //Construtores
    public Canvas(){
        points = new int[2][12];
        insertedPoints=0;
        maxPoints = 2;
        clipPointsInserted = 0;
        isLine = isDDA = isCohen = true;
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
        clipPointsInserted=0;
        isCohen = true;
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

    public void translate(int x, int y){
        for(int i=0; i<insertedPoints; i++){
            points[0][i] += x;
            points[1][i] += y;
        }
        plot();
    }

    public void scale(double x, double y){
        int x1, y1;
        x1 = points[0][0];
        y1 = points[1][0];

        translate(-x1, -y1);
        
        for(int i=0; i<insertedPoints; i++){
            points[0][i] *= x;
            points[1][i] *= y;
        }
   
        translate(x1, y1);

        plot();
    }

    public void rotate(double theta){
        int x1, y1;
        x1 = points[0][0];
        y1 = points[1][0];

        translate(-x1, -y1);
        
        for(int i=0; i<insertedPoints; i++){
            double x = (double) points[0][i];
            double y = (double) points[1][i];
            points[0][i] = (int)Math.round(x * Math.cos(theta*Math.PI/180) - y * Math.sin(theta*Math.PI/180));
            points[1][i] = (int)Math.round(x * Math.sin(theta*Math.PI/180) + y * Math.cos(theta*Math.PI/180));
        }
   
        translate(x1, y1);

        plot();

    }

    public void reflectX(){
        int x1, y1;
        x1 = points[0][0];
        y1 = points[1][0];

        translate(-x1, -y1);
        
        for(int i=0; i<insertedPoints; i++){
            points[1][i] *= -1;
        }
   
        translate(x1, y1);

        plot();
    }

    public void reflectY(){
        int x1, y1;
        x1 = points[0][0];
        y1 = points[1][0];

        translate(-x1, -y1);
        
        for(int i=0; i<insertedPoints; i++){
            points[0][i] *= -1;
        }
   
        translate(x1, y1);

        plot();
    }

    public void reflectXY(){
        int x1, y1;
        x1 = points[0][0];
        y1 = points[1][0];

        translate(-x1, -y1);
        
        for(int i=0; i<insertedPoints; i++){
            points[0][i] *= -1;
            points[1][i] *= -1;
        }
   
        translate(x1, y1);

        plot();
    }

    public void addPointClipArea(int x, int y){
        if(clipPointsInserted == 0){
            minX = x;
            minY = y;
            clipPointsInserted++;
        }
        else if(clipPointsInserted == 1){
            if(minX>x){
                maxX = minX;
                minX = x;
            }
            else maxX = x;
            if(minY>y){
                maxY = minY;
                minY = y;
            }
            else maxY = y;

            ready();   
            for(int i=1; i<insertedPoints; i++){
                if(isCohen) clipCohen(points[0][i-1], points[1][i-1],
                                        points[0][i], points[1][i]);
                else clipLiang(points[0][i-1], points[1][i-1],
                                points[0][i], points[1][i]);
            }
            if(isPolygon){
                if(isCohen) clipCohen(points[0][insertedPoints-1], points[1][insertedPoints-1],
                                        points[0][0], points[1][0]);
                else clipLiang(points[0][insertedPoints-1], points[1][insertedPoints-1],
                                points[0][0], points[1][0]);
            }
            clipPointsInserted = 0;
            repaint();
        }
    }

    private int getCode(int x, int y){
        int code = 0;

        if(x < minX) code+=1;
        if(x > maxX) code+=2;
        if(y < minY) code+=4;
        if(y > maxY) code+=8;

        return code;
    }

    private void clipCohen(int x1, int y1, int x2, int y2){
        boolean aceite, feito;
        aceite = feito = false;
        int c1, c2, cfora;

        while(!feito){
            c1 = getCode(x1, y1);
            c2 = getCode(x2, y2);
            if(c1==0 && c2==0){
                aceite = feito = true;
            }
            else if((c1&c2) !=0) feito = true;
            else{
                double xint, yint;
                xint = yint = 0.0;

                if(c1!=0) cfora = c1;
                else cfora = c2;

                if(((cfora&1) == 1)){
                    xint = (double) minX;
                    yint = y1 + (y2-y1)*(double)(minX-x1)/(x2-x1);
                }
                else if(((cfora&2) == 2)){
                    xint = (double) maxX;
                    yint = y1 + (y2-y1)*(double)(maxX-x1)/(x2-x1);
                }
                else if(((cfora&4) == 4)){
                    yint = (double) minY;
                    xint = x1 + (x2-x1)*(double)(minY-y1)/(y2-y1);
                }
                else if(((cfora&8) == 8)){
                    yint = (double) maxY;
                    xint = x1 + (x2-x1)*(double)(maxY-y1)/(y2-y1);
                }
                if(c1==cfora){
                    x1 = (int)Math.round(xint);
                    y1 = (int)Math.round(yint);
                }
                else{
                    x2 = (int)Math.round(xint);
                    y2 = (int)Math.round(yint);
                }
            }
            if(aceite){
                plotLineBresenham(x1, y1, x2, y2);
            }
        }
    }

    private boolean cliptest(int p, int q){
        boolean result = true;
        double r = (double) q/p;
        if(p<0){
            if(r>u2) result = false;
            else if(r>u1) u1 = r;
        }
        else if(p>0){
            if(r<u1) result = false;
            else if(r<u2) u2 = r;
        }
        else if(q<0) result = false;
        return result;
    }

    private void clipLiang(int x1, int y1, int x2, int y2){
        u1 = 0.0; u2 = 1.0;
        int dx = x2 - x1;
        int dy = y2 - y1;

        if(cliptest(-dx, x1-minX))
            if(cliptest(dx, maxX-x1))
                if(cliptest(-dy, y1-minY))
                    if(cliptest(dy, maxY-y1)){
                        if(u2 < 1.0){
                            x2 = (int)Math.round(x1 + u2*dx);
                            y2 = (int)Math.round(y1 + u2*dy);
                        }
                        if(u1 > 0.0){
                            x1 = (int)Math.round(x1 + u1*dx);
                            y1 = (int)Math.round(y1 + u1*dy);
                        }
                        plotLineBresenham(x1, y1, x2, y2);
                    }
    }


}
