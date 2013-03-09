package Janelas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class Tiro extends JLabel{
	
	Image img;
	
	public Tiro(int x, int y, String imgSrc){
		this(new ImageIcon(imgSrc).getImage());
		this.setBounds(x, y, 50, 50);
		this.setBorder(new LineBorder(Color.orange));
	}
		
	
	public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	}
	

	public Tiro(Image img){
		this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	}
}
