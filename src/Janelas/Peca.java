package Janelas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ToolBarUI;

public class Peca extends JLabel {
	private int nCasas;
	private int rotacao;
	private int xi, yi, x0, y0;
	private int JI;
	private Image img = null;
	private String caminhoImagemFundo = null, orientacao;
	private int posVetor;
	public boolean resetada;
	private int[] celulasOcupadas; 

	public Peca(int x, int y, int n, int r, String imgSrc, String imgExt) {
		this(new ImageIcon(imgSrc+imgExt).getImage());
		
		nCasas = n;
		rotacao = r;
		x0 = xi = x;
		y0 = yi = y;
		caminhoImagemFundo = imgSrc;
		orientacao = imgExt;
		resetada = true;
		celulasOcupadas = new int[nCasas];
		
		if (r == 0)
			this.setBounds(x, y, n * 50, 50);
		else
			this.setBounds(x, y, 50, n * 50);

		// this.setBorder(new LineBorder(Color.black));
	}

	public Peca(int x, int y, int n, int r) {
		// Caso queira ter img de fundo, descomente
		// this(new ImageIcon(img).getImage());

		nCasas = n;
		rotacao = r;
		x0 = xi = x;
		y0 = yi = y;
		// caminhoImagemFundo = img;
		resetada = true;

		if (r == 0)
			this.setBounds(x, y, n * 50, 50);
		else
			this.setBounds(x, y, 50, n * 50);

		//this.setBorder(new LineBorder(Color.CYAN));
	}
	

	public Peca(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void mudaRotacao(int n) {
		rotacao = n;
	}

	public int getCasas() {
		return nCasas;
	}

	public int getRotacao() {
		return rotacao;
	}

	public void setNovaXY(int x, int y) {
		xi = x;
		yi = y;
	}

	public int getXi() {
		return xi;
	}

	public int getYi() {
		return yi;
	}

	public int getX0() {
		return x0;
	}

	public int getY0() {
		return y0;
	}

	public void setJI(int ji) {
		JI = ji;
	}

	public int getJI() {
		return JI;
	}

	public void paintComponent(Graphics g) {
		// Caso queira ter img de fundo, descomente
		g.drawImage(img, 0, 0, null);

		if (caminhoImagemFundo == null) {
			UIDefaults uid = UIManager.getDefaults();
			Graphics2D g2d = (Graphics2D) g;
			Dimension d = this.getSize();

			// Se quiser por uma cor em degrade, bote cores diferentes para os
			// parametros Color.XXXX
			g2d.setPaint(new GradientPaint(0, 0, Color.BLACK, 0, d.height,
					Color.BLACK, false));
			g2d.fillRect(0, 0, d.width, d.height);
		}

	}

	// Somente se for ter img de fundo
	public String getImgPath() {
		return caminhoImagemFundo;
	}
	
	public String getImgOrientacao(){
		return orientacao;
	}

	public void reseta() {
		JI = 0;
		xi = x0;
		yi = y0;
		// resetada = true;
	}
	
	public int[] getCelulasOcupadas(){
		if (rotacao == 0){
			for(int i = 0; i<nCasas; i++){
				celulasOcupadas[i] = JI + i;
				
			}
		}
		if (rotacao == 1){
			for(int i = 0; i<nCasas; i++){
				celulasOcupadas[i] = JI + (i*10);
				
			}
		}
		
		return celulasOcupadas;
	}
	
	public boolean contemCelula(int celula){
		int[] aux = getCelulasOcupadas();
	 
		for(int indice = 0; indice < aux.length; indice++){
			
			if(aux[indice] == celula)
				return true;
		}
			
		return false;
	}

}
