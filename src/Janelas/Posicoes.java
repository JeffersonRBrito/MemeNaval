package Janelas;

public class Posicoes {
	public int x, y;
	public boolean preenchido = false; //preenchida por uma peca
	public boolean acertada = false;//acertada por um tiro
	public String nomePeca;
	
	public Posicoes(int x, int y, boolean preenchido){
		this.x = x;
		this.y = y;
		this.preenchido = preenchido;
		
	}
	
}
