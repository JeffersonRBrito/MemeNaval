package Janelas;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JOptionPane;

public class PainelEsconde extends JPanel {
	private MouseListener apagaBotao;
	private Painel tabuleiro;
	private boolean jogoIniciado = false;
	
	public PainelEsconde(Painel pn){
		//tabuleiro.setLayout(null);
		tabuleiro = pn;
		//this.setBounds(10, 10, 500, 500);
		//this.setBackground(null);
		
		apagaBotao = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if( tabuleiro.isEnabled() && (jogoIniciado)){
					JButton bt = ( (JButton)e.getSource() );
					bt.setVisible(false);
					tabuleiro.setEnabled(false);
					if(tabuleiro.pos.get(tabuleiro.getCelula(bt.getX(), bt.getY())).preenchido){
						tabuleiro.acertouTiro = true;
					} else
						tabuleiro.acertouTiro = false;
				}
				else if(!jogoIniciado)
					JOptionPane.showMessageDialog(null, "Inicie o jogo para comecar");
				else if((!tabuleiro.isEnabled()) && (jogoIniciado))
					JOptionPane.showMessageDialog(null, "Espere sua vez");
			}
		};
		
		for(int i = 0; i<10;i++){
			for(int j=0; j<10; j++){
				JButton quadrado = new JButton("");
				quadrado.setBounds(j*50, i*50, 50, 50);
				quadrado.setText("");
				quadrado.setBorder(new LineBorder(Color.BLUE));
				quadrado.addMouseListener(apagaBotao);
				tabuleiro.add(quadrado);				
			}
		}
		
		tabuleiro.setEnabled(false);
	}
	
	public void habilitaPainel(boolean hab){
		tabuleiro.setEnabled(hab);
	}
	
	public void setJogoIniciado(boolean hab){
		jogoIniciado = hab;
	}
	
	public boolean isJogoIniciado(){
		return jogoIniciado;
	}
	
	public boolean isHabilitado(){
		return tabuleiro.isEnabled();
	}
}
