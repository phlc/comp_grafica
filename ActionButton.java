/*
Computação Gráfica
Implementação Algoritmos Unidade 1
Pedro Henrique Lima Carvalho - 651230
*/

import java.awt.Color;

import javax.swing.JButton;

//Classe Para Botões de Ações
class ActionButton extends JButton{
    //Atributos
    public boolean isPressed;

    //Construtores
    public ActionButton(String name){
        this.isPressed = false;
        this.setText(name);
    };
    public ActionButton(){
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