/*
Computação Gráfica
Implementação Algoritmos Unidade 1
Pedro Henrique Lima Carvalho - 651230
*/

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.Font;


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

    //Componentes de Transformação
    //Translação
    JPanel translationPanel;
    JLabel translationLabelX;
    JLabel translationLabelY;
    JTextField translationTextX;
    JTextField translationTextY;
    JButton translationSubmitBtn;

    //Escala
    JPanel scalePanel;
    JLabel scaleLabelX;
    JLabel scaleLabelY;
    JTextField scaleTextX;
    JTextField scaleTextY;
    JButton scaleSubmitBtn;

    //Rotação
    JPanel rotationPanel;
    JLabel rotationLabel;
    JTextField rotationAngle;
    JButton rotationSubmitBtn;

    //Reflexão
    JPanel reflectionPanel;
    JLabel reflectionLabel;
    JButton reflectionX;
    JButton reflectionY;
    JButton reflectionXY;

    //Recorte
    JPanel clippingPanel;
    JLabel clippingLabel;
    JRadioButton cohenBtn;
    JRadioButton liangBtn;
    ActionButton setClippingBtn;
    ActionButton undoBtn;



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
        pickColor.setBounds(504, 500, 80, 40);

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

        //Configurações Componentes de Translação
        translationPanel = new JPanel();
        translationPanel.setBounds(504, 66, 220, 30);
        translationPanel.setLayout(null);

        translationLabelX = new JLabel("Translations X:");
        translationLabelX.setBounds(4, 0, 100, 30);
        
        translationTextX = new JTextField(3);
        translationTextX.setBounds(104, 0, 40, 30);
        
        translationLabelY = new JLabel("Y:");
        translationLabelY.setBounds(148, 0, 20, 30);
    
        translationTextY = new JTextField(3);
        translationTextY.setBounds(164, 0, 40, 30);
        
        translationSubmitBtn = new JButton("Translate");
        translationSubmitBtn.setBounds(718, 66, 82, 30);
        translationSubmitBtn.setOpaque(true);
        translationSubmitBtn.addMouseListener(this);

        translationPanel.add(translationLabelX);
        translationPanel.add(translationTextX);
        translationPanel.add(translationLabelY);
        translationPanel.add(translationTextY);

        //Configurações Componentes de Escala
        scalePanel = new JPanel();
        scalePanel.setBounds(504, 98, 220, 30);
        scalePanel.setLayout(null);

        scaleLabelX = new JLabel("Scales           X:");
        scaleLabelX.setBounds(4, 0, 100, 30);
        
        scaleTextX = new JTextField(3);
        scaleTextX.setBounds(104, 0, 40, 30);
        
        scaleLabelY = new JLabel("Y:");
        scaleLabelY.setBounds(148, 0, 20, 30);
    
        scaleTextY = new JTextField(3);
        scaleTextY.setBounds(164, 0, 40, 30);
        
        scaleSubmitBtn = new JButton("Scale");
        scaleSubmitBtn.setBounds(718, 98, 82, 30);
        scaleSubmitBtn.setOpaque(true);
        scaleSubmitBtn.addMouseListener(this);

        scalePanel.add(scaleLabelX);
        scalePanel.add(scaleTextX);
        scalePanel.add(scaleLabelY);
        scalePanel.add(scaleTextY);

        //Configurações Componentes de Rotação
        rotationPanel = new JPanel();
        rotationPanel.setBounds(504, 130, 220, 30);
        rotationPanel.setLayout(null);

        rotationLabel = new JLabel("Rotations           Angle:");
        rotationLabel.setBounds(4, 0, 150, 30);
        
        rotationAngle = new JTextField(3);
        rotationAngle.setBounds(164, 0, 40, 30);
        
        rotationSubmitBtn = new JButton("Rotate");
        rotationSubmitBtn.setBounds(718, 130, 82, 30);
        rotationSubmitBtn.setOpaque(true);
        rotationSubmitBtn.addMouseListener(this);

        rotationPanel.add(rotationLabel);
        rotationPanel.add(rotationAngle);

        //Configurações Componentes de Reflexão
        reflectionPanel = new JPanel();
        reflectionPanel.setBounds(504, 162, 110, 30);
        reflectionPanel.setLayout(null);

        reflectionLabel = new JLabel("Reflections:");
        reflectionLabel.setBounds(4, 0, 110, 30);
        reflectionPanel.add(reflectionLabel);

        reflectionX = new JButton("X");
        reflectionX.setBounds(614, 162, 60, 30);
        reflectionX.setOpaque(true);
        reflectionX.addMouseListener(this);

        reflectionY = new JButton("Y");
        reflectionY.setBounds(674, 162, 60, 30);
        reflectionY.setOpaque(true);
        reflectionY.addMouseListener(this);

        reflectionXY = new JButton("XY");
        reflectionXY.setBounds(734, 162, 60, 30);
        reflectionXY.setOpaque(true);
        reflectionXY.addMouseListener(this);

        //Configurações Componentes de Recorte
        clippingPanel = new JPanel();
        clippingPanel.setBounds(504, 194, 294, 70);
        clippingPanel.setLayout(null);
        
        clippingLabel = new JLabel("Clipping:");
        clippingLabel.setBounds(4, 0, 60, 30);
        clippingPanel.add(clippingLabel);

        cohenBtn = new JRadioButton("Cohen-Sutherland", true);
        cohenBtn.setBounds(62, 0, 138, 30);
        cohenBtn.setOpaque(true);
        Font newRadioButtonFont=new Font(cohenBtn.getFont().getName(),cohenBtn.getFont().getStyle(),11);
        cohenBtn.setFont(newRadioButtonFont);
        cohenBtn.addMouseListener(this);

        liangBtn = new JRadioButton("Liang-Barsky");
        liangBtn.setBounds(194, 0, 110, 30);
        liangBtn.setOpaque(true);
        liangBtn.setFont(newRadioButtonFont);
        liangBtn.addMouseListener(this);

        ButtonGroup clippingGroup = new ButtonGroup();
        clippingGroup.add(cohenBtn); clippingGroup.add(liangBtn);
        clippingPanel.add(cohenBtn);
        clippingPanel.add(liangBtn);

        setClippingBtn = new ActionButton("Select Two Points");
        setClippingBtn.setBounds(4, 32, 200, 30);
        setClippingBtn.setOpaque(true);
        setClippingBtn.addMouseListener(this);
        clippingPanel.add(setClippingBtn);

        undoBtn = new ActionButton("Undo");
        undoBtn.setBounds(214, 32, 80, 30);
        undoBtn.setOpaque(true);
        undoBtn.addMouseListener(this);
        clippingPanel.add(undoBtn);

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
        this.add(translationPanel);
        this.add(translationSubmitBtn);
        this.add(scalePanel);
        this.add(scaleSubmitBtn);
        this.add(rotationPanel);
        this.add(rotationSubmitBtn);
        this.add(reflectionPanel);
        this.add(reflectionX);
        this.add(reflectionY);
        this.add(reflectionXY);
        this.add(clippingPanel);
        
        
        //Desabilitar botões de Transformação
        enableTransformations(false);
        enableClipping(false);

        //Mostrar Canvas
        this.setVisible(true);

    }

    private void enableTransformations(boolean enable){

        //Translação
        translationPanel.setEnabled(enable);
        translationLabelX.setEnabled(enable);
        translationLabelY.setEnabled(enable);
        translationTextX.setEnabled(enable);
        translationTextY.setEnabled(enable);
        translationSubmitBtn.setEnabled(enable);

        //Escala
        scalePanel.setEnabled(enable);
        scaleLabelX.setEnabled(enable);
        scaleLabelY.setEnabled(enable);
        scaleTextX.setEnabled(enable);
        scaleTextY.setEnabled(enable);
        scaleSubmitBtn.setEnabled(enable);

        //Rotação
        rotationPanel.setEnabled(enable);
        rotationLabel.setEnabled(enable);
        rotationAngle.setEnabled(enable);
        rotationSubmitBtn.setEnabled(enable);

        //Reflexão
        reflectionPanel.setEnabled(enable);
        reflectionLabel.setEnabled(enable);
        reflectionX.setEnabled(enable);
        reflectionY.setEnabled(enable);
        reflectionXY.setEnabled(enable);
    }

    private void enableRadio(boolean enable){
        lineBtn.setEnabled(enable);
        circleBtn.setEnabled(enable);
        polygonBtn.setEnabled(enable);
        ddaBtn.setEnabled(enable);
        bresenhamBtn.setEnabled(enable);
    }

    private void enableClipping(boolean enable){
        clippingLabel.setEnabled(enable);

        cohenBtn.setEnabled(enable);
        cohenBtn.setSelected(true);

        liangBtn.setEnabled(enable);

        setClippingBtn.setEnabled(enable);
        setClippingBtn.pressBtn(false);

        undoBtn.setEnabled(false);
        undoBtn.pressBtn(false);
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

            enableTransformations(true);
            
            enableRadio(false);

            if(!circleBtn.isSelected()) enableClipping(true);

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

            enableTransformations(false);

            enableClipping(false);

            translationTextX.setText("");
            translationTextY.setText("");

            scaleTextX.setText("");
            scaleTextY.setText("");

            rotationAngle.setText("");

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

            scaleTextX.setVisible(true);
            scaleLabelY.setVisible(true);
            scaleLabelX.setText("Scales           X:");
            scaleLabelX.setBounds(4, 0, 100, 30);

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

            scaleTextX.setVisible(false);
            scaleLabelY.setVisible(false);
            scaleLabelX.setText("Scales                 Radius:");
            scaleLabelX.setBounds(4, 0, 160, 30);

            
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

            scaleTextX.setVisible(true);
            scaleLabelY.setVisible(true);
            scaleLabelX.setText("Scales           X:");
            scaleLabelX.setBounds(4, 0, 100, 30);
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

        //Controle botão translate
        else if(e.getSource() == translationSubmitBtn && 
                                 translationSubmitBtn.isEnabled()){
            try{
                String textX = translationTextX.getText();
                String textY = translationTextY.getText();
                double newX = Double.parseDouble(textX);
                double newY = Double.parseDouble(textY);
                canvas.translate((int)Math.round(newX), -1*(int)Math.round(newY));
            }catch(Exception exp){
                translationTextX.setText("");
                translationTextY.setText("");
                JOptionPane.showMessageDialog(this, ("Inputs must be numbers\nIntegers or Floats"));
            }
        }

        //Controle botão scale
        else if(e.getSource() == scaleSubmitBtn && 
                                 scaleSubmitBtn.isEnabled()){
            try{
                String textX = scaleTextX.getText();
                String textY = scaleTextY.getText();
                double newX;
                double newY = Double.parseDouble(textY);
                if(circleBtn.isSelected()) newX=newY;
                else newX = Double.parseDouble(textX);
                if(newX <=0 || newY<=0) throw new Exception();
                canvas.scale(newX, newY);
            }catch(Exception exp){
                scaleTextX.setText("");
                scaleTextY.setText("");
                JOptionPane.showMessageDialog(this, ("Inputs must be numbers greater than 0\nIntegers or Floats\n"));
            }
        }

        //Controle botão rotate
        else if(e.getSource() == rotationSubmitBtn && 
                                 rotationSubmitBtn.isEnabled()){
            try{
                String textA = rotationAngle.getText();
                double angle = Double.parseDouble(textA);
                canvas.rotate(-1*angle);
            }catch(Exception exp){
                rotationAngle.setText("");
                JOptionPane.showMessageDialog(this, ("Angle must be a number\nIntegers or Floats\n"));
            }
        }

        //Controle botão reflectionX
        else if(e.getSource() == reflectionX && 
                                 reflectionX.isEnabled()){
            canvas.reflectX();
        }

        //Controle botão reflectionY
        else if(e.getSource() == reflectionY && 
                                 reflectionY.isEnabled()){
            canvas.reflectY();
        }

        //Controle botão reflectionX
        else if(e.getSource() == reflectionXY && 
                                 reflectionXY.isEnabled()){
            canvas.reflectXY();
        }

        //Controle botão setClipping
        else if(e.getSource() == setClippingBtn && !setClippingBtn.isPressed
                                 && setClippingBtn.isEnabled()){
            setClippingBtn.pressBtn(true);
            undoBtn.setEnabled(false);
            cohenBtn.setEnabled(false);
            liangBtn.setEnabled(false);
            enableTransformations(false);
        }

        //Controle selecionar area de clipping
        if(e.getSource() == canvas && setClippingBtn.isPressed
                            && !undoBtn.isEnabled()){
            if(canvas.clipPointsInserted == 1){
                undoBtn.setEnabled(true);
                setClippingBtn.pressBtn(false);
                setClippingBtn.setEnabled(false);
            }
            canvas.addPointClipArea(e.getX(), e.getY());
        }

        //Controle Botão undoClipping
        if(e.getSource() == undoBtn && undoBtn.isEnabled()){
            setClippingBtn.setEnabled(true);
            setClippingBtn.pressBtn(false);
            cohenBtn.setEnabled(true);
            liangBtn.setEnabled(true);
            undoBtn.setEnabled(false);
            canvas.plot();
        }

        //Controle botão cohen-sutherland
        else if(e.getSource() == cohenBtn && !canvas.isCohen
                                            && cohenBtn.isEnabled()){

            canvas.isCohen = true;
        }

        //Controle botão cohen-sutherland
        else if(e.getSource() == liangBtn && canvas.isCohen
                                          && liangBtn.isEnabled()){

            canvas.isCohen = false;
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
