/*
Computação Gráfica
Implementação Algoritmos Unidade 1
Pedro Henrique Lima Carvalho - 651230
*/

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;



//Classe Base da Interface Gráfica
class App extends JFrame implements ActionListener, MouseInputListener{

    //Variáveis Globais
    Canvas canvas; //Canvas para plotagem

    //Botões do Tipo de Figura
    JRadioButton lineBtn;
    JRadioButton circleBtn;
    JRadioButton polygonBtn;

    //Botões de Ações
    ActionButton setPointsBtn;
    ActionButton plotBtn;
    ActionButton transformBtn;
    ActionButton restartBtn;

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
        lineBtn.addMouseListener(this);

        circleBtn = new JRadioButton("Circle");
        circleBtn.setBounds(584, 2, 80, 30);
        circleBtn.setOpaque(true);
        circleBtn.addMouseListener(this);

        polygonBtn = new JRadioButton("Polygon");
        polygonBtn.setBounds(664, 2, 90, 30);
        polygonBtn.setOpaque(true);
        polygonBtn.addMouseListener(this);

        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(lineBtn); typeGroup.add(circleBtn); typeGroup.add(polygonBtn);

        //Configurações ActionButtons
        setPointsBtn = new ActionButton("Set Points");
        setPointsBtn.setBounds(0, 504, 100, 60);
        setPointsBtn.addMouseListener(this);

        plotBtn = new ActionButton("Plot");
        plotBtn.setBounds(100, 504, 100, 60);
        plotBtn.addMouseListener(this);
        plotBtn.setEnabled(false);

        transformBtn = new ActionButton("Transform");
        transformBtn.setBounds(200, 504, 100, 60);
        transformBtn.addMouseListener(this);
        transformBtn.setEnabled(false);

        restartBtn = new ActionButton("<html>Restart<br/>Config<html>");
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
        ddaBtn.addMouseListener(this);

        bresenhamBtn = new JRadioButton("Bresenham");
        bresenhamBtn.setBounds(584, 34, 120, 30);
        bresenhamBtn.setOpaque(true);
        bresenhamBtn.addMouseListener(this);

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
    public void mousePressed(MouseEvent e) {
        //Adicionar pontos no canvas
        if(e.getSource() == canvas && setPointsBtn.isPressed){
            canvas.addPoint(e.getX(), e.getY());
        }
        //Controle botão setPointsBtn
        else if(e.getSource() == setPointsBtn && !setPointsBtn.isPressed 
                                              && setPointsBtn.isEnabled()){
            canvas.ready();
            setPointsBtn.pressBtn(true);

            plotBtn.pressBtn(false);
            plotBtn.setEnabled(true);
            
            transformBtn.pressBtn(false);
            
            restartBtn.pressBtn(false);
            enableRadio(false);
        }
        //Controle botão plotBtn
        else if(e.getSource() == plotBtn && !plotBtn.isPressed 
                                         && plotBtn.isEnabled()){
            setPointsBtn.pressBtn(false);
            setPointsBtn.setEnabled(false);

            plotBtn.pressBtn(true);

            transformBtn.pressBtn(false);
            transformBtn.setEnabled(true);

            restartBtn.pressBtn(false);
            enableRadio(false);

            canvas.plot();
        }
        //Controle botão transformBtn
        else if(e.getSource() == transformBtn && !transformBtn.isPressed 
                                              && transformBtn.isEnabled()){
            setPointsBtn.pressBtn(false);
            setPointsBtn.setEnabled(false);

            plotBtn.pressBtn(false);
            plotBtn.setEnabled(false);

            transformBtn.pressBtn(true);

            restartBtn.pressBtn(false);
            
            enableRadio(false);
        }
        //Controle botão restartBtn
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
        //Controle botão pickColer
        else if(e.getSource() == pickColor){
            Color choice = JColorChooser.showDialog(canvas, "Choose your Color", Color.BLACK);
            if(choice != null) canvas.color = choice.getRGB();
        }
        //Controle botão lineBtn
        else if(e.getSource() == lineBtn && !canvas.isLine 
                                         && lineBtn.isEnabled()){
            canvas.isLine = true;
            canvas.isCircle = false;
            canvas.isPolygon = false;
            canvas.maxPoints = 2;

            canvas.isDDA = true;
            canvas.isBresenham = false;
            ddaBtn.setEnabled(true);
            bresenhamBtn.setEnabled(true);
            ddaBtn.setSelected(true);
        }
        //Controle botão circleBtn
        else if(e.getSource() == circleBtn && !canvas.isCircle      
                                           && circleBtn.isEnabled()){
            canvas.isLine = false;
            canvas.isCircle = true;
            canvas.isPolygon = false;
            canvas.maxPoints = 2;

            canvas.isBresenham = true;
            canvas.isDDA = false;
            bresenhamBtn.setSelected(true);
            ddaBtn.setEnabled(false);
            bresenhamBtn.setEnabled(false);
            
        }
        //Controle botão polygonBtn
        else if(e.getSource() == polygonBtn && !canvas.isPolygon 
                                            && polygonBtn.isEnabled()){
            canvas.isLine = false;
            canvas.isCircle = false;
            canvas.isPolygon = true;
            canvas.maxPoints = 12;

            canvas.isDDA = true;
            canvas.isBresenham = false;
            ddaBtn.setEnabled(true);
            bresenhamBtn.setEnabled(true);
            ddaBtn.setSelected(true);
        }

        //Controle botão ddaBtn
        else if(e.getSource() == ddaBtn && !canvas.isDDA 
                                            && ddaBtn.isEnabled()){

            canvas.isDDA = true;
            canvas.isBresenham = false;
        }

        //Controle botão bresenhamBtn
        else if(e.getSource() == bresenhamBtn && !canvas.isBresenham 
                                            && bresenhamBtn.isEnabled()){
            
            canvas.isDDA = false;
            canvas.isBresenham = true;
        }
    }

    //Métodos das Interfaces
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

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
