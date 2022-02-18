/*
Computação Gráfica
Implementação Algoritmos Unidade 1
Pedro Henrique Lima Carvalho - 651230
*/

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.lang.model.util.ElementScanner14;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;

//Classe Para Botões de Ações
class actionButton extends JButton{
    //Atributos
    public boolean isPressed;

    //Construtores
    public actionButton(String name){
        this.isPressed = false;
        this.setText(name);
    };
    public actionButton(){
        this("Place Holder");
    }

    public void pressBtn(boolean isPressed){
        this.isPressed = isPressed;
        if(this.isPressed){
            this.setForeground(new Color(0x00CC00));
        }
        else{
            this.setForeground(Color.BLACK);
        }
    }
}

//Classe do Canvas para Desenho
class Canvas extends JPanel{
    //Atributos
    public int points[][];
    public int maxPoints;
    public int insertedPoints;
    public BufferedImage buffer;
    public int color;
    public boolean isLine, isCircle, isPolygon;

    

    //Construtores
    public Canvas(){
        points = new int[2][30];
        insertedPoints=0;
        maxPoints = 2;
        isLine = true;
        isCircle = isPolygon = false;
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


//Classe Base da Interface Gráfica
class App extends JFrame implements ActionListener, MouseInputListener{

    //Variáveis Globais
    Canvas canvas; //Canvas para plotagem

    //Botões do Tipo de Figura
    JRadioButton lineBtn;
    JRadioButton circleBtn;
    JRadioButton polygonBtn;

    //Botões de Ações
    actionButton setPointsBtn;
    actionButton plotBtn;
    actionButton transformBtn;
    actionButton restartBtn;

    //Botões JButton Comuns
    JButton pickColor;

    //Botões de Algoritmos de Rasterização
    JRadioButton ddaBtn;
    JRadioButton bresenhamBtn;


    //Construtor da Classe MyFrame
    public App(){

        //Inicialização do canvas
        canvas = new Canvas();
        canvas.setBounds(2, 2, 500, 500);
        canvas.setBackground(Color.white);
        canvas.setLayout(null);
        canvas.addMouseListener(this);

        //Configurações da Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0x9c9c9c));
        this.setSize(800,600);

        //Configurações radioButtons de tipo
        lineBtn = new JRadioButton("Line", true);
        lineBtn.setBounds(504, 2, 80, 30);
        lineBtn.setOpaque(true);

        circleBtn = new JRadioButton("Circle");
        circleBtn.setBounds(584, 2, 80, 30);
        circleBtn.setOpaque(true);

        polygonBtn = new JRadioButton("Polygon");
        polygonBtn.setBounds(664, 2, 90, 30);
        polygonBtn.setOpaque(true);

        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(lineBtn); typeGroup.add(circleBtn); typeGroup.add(polygonBtn);

        //Configurações actionButtons
        setPointsBtn = new actionButton("Set Points");
        setPointsBtn.setBounds(0, 504, 100, 60);
        setPointsBtn.addMouseListener(this);

        plotBtn = new actionButton("Plot");
        plotBtn.setBounds(100, 504, 100, 60);
        plotBtn.addMouseListener(this);
        plotBtn.setEnabled(false);

        transformBtn = new actionButton("Transform");
        transformBtn.setBounds(200, 504, 100, 60);
        transformBtn.addMouseListener(this);
        transformBtn.setEnabled(false);

        restartBtn = new actionButton("<html>Restart<br/>Config<html>");
        restartBtn.setBounds(300, 504, 100, 60);
        restartBtn.pressBtn(true);
        restartBtn.addMouseListener(this);

        //Configurações Botões Comuns
        pickColor = new JButton("Pick Color");
        pickColor.addMouseListener(this);
        pickColor.setBounds(504, 66, 80, 40);

        //Configurações radioButtons de rasterização
        ddaBtn = new JRadioButton("DDA", true);
        ddaBtn.setBounds(504, 34, 80, 30);
        ddaBtn.setOpaque(true);

        bresenhamBtn = new JRadioButton("Bresenham");
        bresenhamBtn.setBounds(584, 34, 120, 30);
        bresenhamBtn.setOpaque(true);

        ButtonGroup rasterGroup = new ButtonGroup();
        rasterGroup.add(ddaBtn); rasterGroup.add(bresenhamBtn);


        //Montagem dos Componentes
        this.add(canvas);
        this.add(lineBtn); this.add(circleBtn); this.add(polygonBtn);
        this.add(setPointsBtn);
        this.add(plotBtn);
        this.add(transformBtn);
        this.add(restartBtn);
        this.add(ddaBtn);
        this.add(bresenhamBtn);
        this.add(pickColor);

        //Mostrar Canvas
        this.setVisible(true);

    }

    private void enableRadio(boolean enable){
        lineBtn.setEnabled(enable);
        circleBtn.setEnabled(enable);
        polygonBtn.setEnabled(enable);
        ddaBtn.setEnabled(enable);
        bresenhamBtn.setEnabled(enable);
    }

    //Evento Mouse Click
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == canvas && setPointsBtn.isPressed){
            canvas.addPoint(e.getX(), e.getY());
        }
        else if(e.getSource() == setPointsBtn && !setPointsBtn.isPressed){
            canvas.ready();
            setPointsBtn.pressBtn(true);

            plotBtn.pressBtn(false);
            plotBtn.setEnabled(true);
            
            transformBtn.pressBtn(false);
            
            restartBtn.pressBtn(false);
            enableRadio(false);
        }
        else if(e.getSource() == plotBtn && !plotBtn.isPressed){
            setPointsBtn.pressBtn(false);
            setPointsBtn.setEnabled(false);

            plotBtn.pressBtn(true);

            transformBtn.pressBtn(false);
            transformBtn.setEnabled(true);

            restartBtn.pressBtn(false);
            enableRadio(false);
        }
        else if(e.getSource() == transformBtn && !transformBtn.isPressed){
            setPointsBtn.pressBtn(false);
            setPointsBtn.setEnabled(false);

            plotBtn.pressBtn(false);
            plotBtn.setEnabled(false);

            transformBtn.pressBtn(true);

            restartBtn.pressBtn(false);
            
            enableRadio(false);
        }
        else if(e.getSource() == restartBtn){
            setPointsBtn.pressBtn(false);
            setPointsBtn.setEnabled(true);

            plotBtn.pressBtn(false);
            plotBtn.setEnabled(false);

            transformBtn.pressBtn(false);
            transformBtn.setEnabled(false);

            restartBtn.pressBtn(true);
            enableRadio(true);
            canvas.clear();
        }
        else if(e.getSource() == pickColor){
            Color choice = JColorChooser.showDialog(canvas, "Choose your Color", Color.BLACK);
            if(choice != null) canvas.color = choice.getRGB();
        }
    }

    //Métodos das Interfaces
    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) { }

}


public class GraphicManipulation{
    
    public static void main(String args[]){

        //Inicialização do App
        new App();
    }
}
