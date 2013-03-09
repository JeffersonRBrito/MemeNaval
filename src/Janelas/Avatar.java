package Janelas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Avatar extends JPanel{
	
	private Image img;
	
	public Avatar(int x, int y, String imgSrc){
		img = (new ImageIcon(imgSrc).getImage());
		
		this.setLayout(null);
		this.setBounds(x, y,120,120);
	}
	
	public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	}
	

	public Avatar(Image img){
		this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	}
}
