package fr.esiea.glpoo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Class permettant de cr�er les pi�ces et de les mettres dans le d�p�t de pi�ce du jeu et de les d�placer/rotater
public class Pieces extends JPanel{


/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//A VOIR COMMENT FAIRE VINCENT
	private int num_j=1; //Variable permettant de choisir le jeu(puzzle) choisit par le joueur



//Constructeur de la piece 
	public Pieces() {
		
        setLayout(null); // On supprime le layout manager
 
        PiecesMove listener = new PiecesMove(this); //Permet de cr�er le listener permettant de bouger la piece
      
        for(int num=1; num<17; num++) { //Cr�ation des 16 pi�ces du jeu gr�ce � la m�thode createPieces
            add(createPieces(num,num_j));
        }
  
        //Ajout des listener permettant de rotater et d�placer/d�poser une pi�ce
        addKeyListener(listener); 
        addMouseListener(listener);
        addMouseMotionListener(listener);
        
        //Pour le KeyListener 
        setFocusable(true);
        requestFocus();
        
        
    }
	
	
   
	
	//Methode permettant de cr�er les pi�ces du jeu
	  private JLabel createPieces(int num, int num_j) {
		  
		    //On r�cup�re l'image de la pi�ce selon son num�ro, ainsi que le num�ro du jeu et on l'ajoute au Label
	        ImageIcon image = new ImageIcon("src/main/ressources/jeu_"+num_j+"/piece_"+num+".jpg");
			JLabel piece=new JLabel(image);
	        
			  //Variable random permettant de mettre au hasard les pi�ces dans le d�pot
		      int lower = 1;
		      int higher = 5;

		      int random = (int)(Math.random() * (higher-lower)) + lower;
		       
		        
	     //Valeur de gauche haut (505,283) dans le d�pot des pi�ces
			if(random==1)
			 piece.setLocation(502,283 );
		//Valeur de droite haut (594,283)
			if(random==2)
			 piece.setLocation(594,283 );
		//Valeur de droite bas (594,374)
			if(random==3)
			 piece.setLocation(594,374 );
		//Valeur de gauche bas (505 ,374)
			if(random==4)
			 piece.setLocation(505,374 );
		
			//On affecte la taille de la pi�ce et son nom pour l'identifier
	        piece.setSize(80,80); 
	        piece.setName("piece_"+num); 
	        
	       
	        return piece;
	    }
	 
	  
	  
	  
	  
	  
	  
	  
	  //Debut de la class PiecesMove permettant de cr�er un listener permettant de faire les mouvements de rotation et d�placement d'une pi�ce
	    private static class PiecesMove extends MouseAdapter implements KeyListener{
	    	
	    	
			private boolean move; //Variable permettant de savoir si la pi�ce est d�placer
	        private int relx;
	        private JLabel component;
	        private int rely;
	        private Container container;
			private int Val_Rotation=0; //La valeur de rotation est nulle au d�part
	   
			
	        public PiecesMove(Container container) {
	            this.container=container;
	        }
	        
	       

	 
	        @Override //Listener permettant de d�poser une pi�ce lors de son d�palcement et de d�placer une pi�ce
	        public void mousePressed(MouseEvent e) {
	        	
	        	//Si on a un mouvement
	            if ( move ) {
	                move=false; //On le met en arr�t
	                component.setBorder(null); //Et on supprime la bordure noire qui indique que nous d�pla�ons une pi�ce
	              //METTRE ICI POUR RECUPERER LE NOM DE LA PIECE DEPOSER POUR VINCENT// 
	      
	               
	                component=null; //On oublie la component
	                
	               
	            }
	            else { // Si il n'y a pas de mouvement
	                component = getPiece(e.getX(),e.getY()); //On m�morise la pi�ce � d�placer avec la m�thode getPiece
	                if ( component!=null ) { 
	                	
	                    container.setComponentZOrder(component,0); //Place le composant le plus haut possible
	                    relx = e.getX()-component.getX(); //On m�morise la position relative
	                    rely = e.getY()-component.getY(); //On m�morise la position relative
	                    move=true; //Indication du d�marrage du mouvement
	                    component.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //On indique la pi�ce s�lectionn�e par une bordure noire
	                   
	                }
	            }
	        }
	 
	        //M�thode permettant de d�finir la pi�ce que nous avons cliqu� dessus
	        private JLabel getPiece(int x, int y) {
	        	
	            //On recherche la premi�re pi�ce qu'on clique dessus pour la d�placer
	            for(Component component : container.getComponents()) {
	            	// Il faut que getName soit diff�rent de null puisque seul les pi�ces ont un nom comme composant, et on veut seulement d�placer les pi�ces
	                if ( component instanceof JLabel && component.getBounds().contains(x, y) && component.getName()!=null ) { 
	                	return (JLabel)component; //On retourne la pi�ce qu'on a cliqu� dessus
	                }
	            }
	            return null;
	        }
	 
	        @Override
	        public void mouseMoved(MouseEvent e) {
	            if ( move ) {
	                //Si on d�place le component (la pi�ce), on change sa position
	                component.setLocation(e.getX()-relx, e.getY()-rely);
	            }
	        }
	        
	        @Override //Listener KeyPressed pour la Rotation
	        public void keyPressed(KeyEvent evt) {
				
				  if(evt.getKeyCode()==82){ //V�rification de la touche R pour la rotation
					  
					  if(move==true){ //Si on a la pi�ce en mouvement
				 
						  	rotate(component);//Alors on peut effectuer une rotation
				
				 //Pour nesrine, c'est pour la valeur de rotation
						  	Val_Rotation++; //On augmente la valeur du nombre de rotation
						  	if(Val_Rotation==4)//Si on a effectu� un tour complet
						  		Val_Rotation=0;//On remet la valeur � z�ro
				
					  }
				  }
				}
	        	@Override
				public void keyTyped(KeyEvent evt) {
	        		 
	        	}
	        	@Override
				public void keyReleased(KeyEvent evt) { 
	        	
	        	}

	        //M�thode permettant d'effectuer une rotation d'un JLabel
	        public void rotate(JLabel panel){
	        	
	        	ImageIcon icon = (ImageIcon) panel.getIcon();
	        	
	        	int w = icon.getIconWidth();
		        int h = icon.getIconHeight();
		        int type = BufferedImage.TYPE_INT_RGB;  // other options, see api
		        BufferedImage image = new BufferedImage(h, w, type);
		        Graphics2D g2 = image.createGraphics();
		        double x = (h - w)/2.0;
		        double y = (w - h)/2.0;
		        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		        at.rotate(Math.toRadians(90), w/2.0, h/2.0);
		        g2.drawImage(icon.getImage(), at, panel);
		        g2.dispose();
		        icon = new ImageIcon(image);
		        panel.setIcon(icon);
		        

         }
	     
	       
} //Fin de la class PiecesMove
	    
	   
	    
	    //NESRINE FAUT QUE TU COMMENTES
	    //Methode run permettant de la class Pieces ..
	    public String run() {
			 
			String csvFile = "src/test/ressources/faces-01.csv";
			BufferedReader br = null;
			String line = ";";
			String cvsSplitBy = ",";
		 
			try {
		 
				
		 
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
		 
					// use comma as separator
					String[] couleur = line.split(cvsSplitBy);
		            
				
		 return couleur[1];
				}
		 
		 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		 
			
			return null;
		  }




		



	   




	
	    
	    	
	    	
	    
	
	 
	 
}//Fin de la class Pi�ces
