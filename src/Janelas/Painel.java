package Janelas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Painel extends JPanel {
	public int x_click, y_click;
	public int ID;
	public boolean objCLicado = false;
	public boolean acertouTiro = false;
	public ArrayList<Posicoes> pos;
	
	
	public Painel(){
		//int x, y;
		pos = new ArrayList<Posicoes>();
		
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				pos.add(new Posicoes(j*50,i*50, false));
			}
		}
	}
	
	 private Image img;

	  public Painel(String img) {
	    this(new ImageIcon(img).getImage());
	    
	    pos = new ArrayList<Posicoes>();
		
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				pos.add(new Posicoes(j*50,i*50, false));
			}
		}
		
	  }

	  public Painel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	    
	    pos = new ArrayList<Posicoes>();
		
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				pos.add(new Posicoes(j*50,i*50, false));
			}
		}
		
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }
	  
	  public void resetaCelulas(){
		  for(int i = 0; i < pos.size(); i++){
			  pos.get(i).preenchido = false;
			  pos.get(i).acertada =  false;
		  }
	  }
	/*
	public void setPreenchido(int x, int y){
		
	}*/
	
	public void imprimePosicoes(){
		for(int i = 0; i < pos.size(); i++){
			if(pos.get(i).preenchido)
				System.out.println(i+"-"+"P");
		}
	}
	
	public void setPreenchido(Peca peca){
		int casas = peca.getCasas();
		int rotacao = peca.getRotacao();
		
		int i = peca.getX() / 50;
		int j = peca.getY() / 50;

		String ji = String.valueOf(j) + String.valueOf(i);
		int JI = Integer.valueOf(ji);
		
		if (rotacao == 0) {
			for (int c = 0; c < casas; c++) {
				this.pos.get(JI + c).preenchido = true;
			}
		} else {
			for (int c = 0; c < casas; c++) {
				this.pos.get(JI + (c * 10)).preenchido = true;
			}
		}

	}
	
	public int getCelula(int x, int y){

		int i = x / 50;
		int j = y / 50;

		String ji = String.valueOf(j) + String.valueOf(i);
		int JI = Integer.valueOf(ji);
		
		return JI;
	}
	
}
