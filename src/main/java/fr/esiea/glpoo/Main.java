package fr.esiea.glpoo;

import java.awt.BorderLayout;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Hello world!
 *
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends Pieces
{

	Pieces piece = new Pieces();

	String couleur=piece.run();
	
	Colorinfrench color =new Colorinfrench();
	
	Color test=color.parse(couleur);

	
	public Main() {
     
		
		JFrame frame=new JFrame("Eternity");


    

		  
        frame.setMinimumSize(new Dimension(640,480));
        frame.setExtendedState(frame.MAXIMIZED_BOTH); // Pour rendre la fenetre � la taille maximul de l'�cran
        frame.setUndecorated(true);//Mettre enti�rement en plein ecran (enlever la barre windows et le haut du JFrame)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(test);

     
        
        JPanel buttonPanel=new JPanel();
     

        frame.pack();
       
        frame.setVisible(true);
        
       

//System.out.println(couleur);
    }
    
    public static void main(String[] args) {
        new Main();
    }
}
