package Janelas;

import javax.swing.JLabel;

public  class Maquina {
	Painel pJogador, pMaquina, pAuxiliar;
	int pontoMaquina = 0, indiceCelula = 0;
	int ultimaCelula = 0, penultimaCelula = 0, proximaJogada=0, numAtualErros, celulaPivo = 0, proximaCelula = (int)(Math.random()*99);
	Peca maq2, maq3, maq4, maq5;
	public boolean acertouUltima = false, acertouPenultima = false, jogandoEmCruz = false, jogandoEmLinha = false, podeMudarPivo = true;
	private char orientacaoVetorEmLinha;
	int[] celulasPossiveis;

	
	public Maquina(Painel pmaquina, Painel pjogador, Painel pauxiliar){
		pMaquina = new Painel();
		pMaquina = pmaquina;
		pJogador = new Painel();
		pJogador = pjogador;
		pAuxiliar = new Painel();
		pAuxiliar = pauxiliar;
		celulasPossiveis = new int[4];
	}
	
	public void jogar() {
		geraCelulaAleatoria();
		acertou(proximaCelula);
		
		//imprimirCelulasGeradas();
		System.out.println("Maquina tentou jogar na celula: " + proximaCelula);
	}//jogar
	
	public boolean acertou(int ultimaCelula){
		
		//Se acertou alguma peça
		if (pJogador.pos.get(ultimaCelula).preenchido) {
				pAuxiliar.add(new Tiro(pJogador.pos.get(ultimaCelula).x,
					pJogador.pos.get(ultimaCelula).y, "Images/trollThumb.jpg"));// adiciono a marca de tiro(TROLLFACE) no painel auxiliar
			
			pJogador.pos.get(ultimaCelula).acertada = true;

			pontoMaquina++;			

			pAuxiliar.add(new Tiro(pJogador.pos.get(ultimaCelula).x,
					pJogador.pos.get(ultimaCelula).y, "Images/trollThumb.jpg"));// adiciono a marca de tiro(TROLLFACE) no painel auxiliar

			pAuxiliar.repaint();
			
			this.ultimaCelula = ultimaCelula;
			acertouPenultima = acertouUltima;
			acertouUltima = true;
			/*
			geraVetor();
			
			if(jogandoEmCruz || jogandoEmLinha)
				geraCelulaInteligente();
			*/
			return true;
			
		} else {
			pAuxiliar.add(new Tiro(pAuxiliar.pos.get(ultimaCelula).x,
					pAuxiliar.pos.get(ultimaCelula).y, "Images/tiroMaquina.png"));
			pAuxiliar.pos.get(ultimaCelula).acertada = true;

			pAuxiliar.repaint();
			
			this.ultimaCelula = ultimaCelula;
			contabilizaErro();
			
			acertouPenultima = acertouUltima;
			acertouUltima = false;
			
			/*
			if(jogandoEmCruz || jogandoEmLinha){
				geraCelulaInteligente();
				acertouUltima = false;
			}else if ((!jogandoEmCruz)&&(!jogandoEmLinha)){
				acertouPenultima = acertouUltima;
				acertouUltima = false;
				
				if((!acertouPenultima)&&(!acertouUltima)){
					podeMudarPivo = true;
					geraCelulaAleatoria();
				}
			}else if((acertouPenultima)&&(jogandoEmLinha)){
				geraVetor();
			}
			*/
			
			//geraVetor();
			
			return false;
		}
	}//acertou

	public void montaJogoMaquina() {
					
		//posicionar as peças (aleatoriamente \ha)
		maq2 = posicionaPeca(geraRotacao(), 2);
		maq3 = posicionaPeca(geraRotacao(), 3);
		maq4 = posicionaPeca(geraRotacao(), 4);
		maq5 = posicionaPeca(geraRotacao(), 5);
		
		maq2.setName("p2v");
		maq3.setName("p3v");
		maq4.setName("p4v");
		maq5.setName("p5v");
		
		pMaquina.add(maq2);
		pMaquina.add(maq3);
		pMaquina.add(maq4);
		pMaquina.add(maq5);
	}

	public int geraRotacao() {
		int rotacao;

		// Escolhe rotacao
		if ((Math.random() * 10) < 5)
			rotacao = 0;
		else
			rotacao = 1;

		return rotacao;
	}
	
	public Peca posicionaPeca(int rotacao, int nCasas){
		Peca peca;
		if(rotacao == 0){
			int i=0, j=0;
			boolean podeMover = false;

			while (!podeMover) {
				// Calculando o limite de escolha de coluna
				int jlimite = 10 - (nCasas);

				// Gerando linha-coluna aleatória :)
				 j = ((int) (Math.random() * jlimite));
				 i = ((int) (Math.random() * 9));

				String ji = String.valueOf(i) + String.valueOf(j);
				int JI = Integer.valueOf(ji);
				
				podeMover = true;
				for (int c = 0; c < nCasas; c++) {
					if (pMaquina.pos.get(JI + c).preenchido) {
						podeMover = false;
						break;
					}	
				}//for
			}// while
			
			
			peca = new Peca(j*50,i*50,nCasas, rotacao);
			pMaquina.setPreenchido(peca);
			
			
		}else{
			
			int i=0, j=0;
			boolean podeMover = false;

			while (!podeMover) {
				// Calculando o limite de escolha de coluna
				int ilimite = 10 - (nCasas);

				// Gerando linha-coluna aleatória :)
				 j = ((int) (Math.random() * 9));
				 i = ((int) (Math.random() * ilimite));
				 
				String ji = String.valueOf(i) + String.valueOf(j);
				int JI = Integer.valueOf(ji);
				
				podeMover = true;
				for (int c = 0; c < nCasas; c++) {
					if (pMaquina.pos.get(JI + (c*10)).preenchido) {
						podeMover = false;
						break;
					}
				}//for
			}// while
			
			
			peca = new Peca(j*50,i*50,nCasas, rotacao);
			pMaquina.setPreenchido(peca);
			
			
			}//else
		return peca;
	}
	
	public void geraCelulaAleatoria() {
		int celula = (int) (Math.random() * 99); // Escolhe uma celula pra jogar
		jogandoEmCruz = false;
		jogandoEmLinha = false;
		
		// Evita que a máquina tente atirar em uma posição qe já foi "atirada"
		while (pAuxiliar.pos.get(celula).acertada) {// Enquanto a celula em que a
													// maquina tentar jogar
													// estiver como ja acertada
			celula = (int) (Math.random() * 99); // Procura uma outra celula
		}
		
		
		this.proximaCelula = celula;
	}// metodo
	
	public void geraCelulaInteligente() {
		
		int celula = 0;
		// Evita que a máquina tente atirar em uma posição qe já foi "atirada"

		celula = celulasPossiveis[indiceCelula];
		while (pJogador.pos.get(celula).acertada) {
			indiceCelula++;
			celula = celulasPossiveis[indiceCelula];
			
			if(indiceCelula == 3)
				indiceCelula = 0;
			
		}
		
		if(indiceCelula == 3){
			indiceCelula = 0;
			
			if(jogandoEmCruz){
				jogandoEmCruz = false;
				jogandoEmLinha = true;
			}
		}
		
		this.proximaCelula = celula;
	}// metodo

	
	public void geraCruzCelulasPossiveis() {
		jogandoEmCruz = true;
		jogandoEmLinha = false;
		podeMudarPivo = false;
		
		int cAbaixo = celulaPivo + 10;
		int cAcima = celulaPivo - 10;
		int cDireita = celulaPivo + 1;
		int cEsquerda = celulaPivo -1;
		
		if(cAbaixo > 99)
			cAbaixo = celulaPivo;		
		if(cAcima < 0)
			cAcima = 0;
		if( (cDireita%10) == 0)
			cDireita = cDireita -1;		
		if(cEsquerda < 0)
			cEsquerda = celulaPivo;
		else if(Integer.reverse(cEsquerda)>=90)
			cEsquerda += 1;
		
		celulasPossiveis[0] = cAcima;
		celulasPossiveis[1] = cAbaixo;
		celulasPossiveis[2] = cDireita;
		celulasPossiveis[3] = cEsquerda;
				
	}
		
	public void geraLinhaCelulasPossiveis(char Orientacao){
		jogandoEmLinha = true;
		jogandoEmCruz = false;
		podeMudarPivo = false;
		indiceCelula = 0;
		
		if(Orientacao == 'e'){
			
			for(int i = 0; i < 4; i++){
				
				int cEsquerda = (celulaPivo - i) -1;
				
					if(cEsquerda < 0)
						cEsquerda = 0;
					
						celulasPossiveis[i] = cEsquerda;
			}//for
		}else if (Orientacao == 'd'){
			for(int i = 0; i < 4; i++){
				
				int cDireita = (celulaPivo + i) + 1;
			
				if( (cDireita%10) == 0)
					cDireita = 0;
				
				celulasPossiveis[i] = cDireita;
			}		
		} else if (Orientacao == 'b'){
			for(int i = 0; i < 4; i++){
				
				int cAbaixo = (celulaPivo + (i*10))+10;
				
				if(cAbaixo > 99)
					cAbaixo = 99;
				
				celulasPossiveis[i] = cAbaixo;
			}
		} else if(Orientacao == 'c'){
			for(int i = 0; i < 4; i++){
		
				int cAcima = (celulaPivo - (i*10))-10;
				
				if(cAcima < 0)
					cAcima = 0;
				
				celulasPossiveis[i] = cAcima;
			}
		}
	}
	
	private void geraVetor() {
		//Se acertou a primeira vez gera vetor das próximas possibilidades
		//if((!jogandoEmCruz) ||( (!jogandoEmCruz)&&(acertouUltima) )){
			if (acertouUltima && (!acertouPenultima)) {
				if(podeMudarPivo)
					this.celulaPivo = ultimaCelula;
				
				geraCruzCelulasPossiveis();
				geraCelulaInteligente();
				
				System.out.println("Gerei uma cruz:");
				imprimirCelulasGeradas();
			} else if (acertouUltima && acertouPenultima) {
				int orientacao = celulaPivo - ultimaCelula;

				if (orientacao == 1)
					orientacaoVetorEmLinha = 'e';
				if (orientacao == -1)
					orientacaoVetorEmLinha = 'd';
				if (orientacao == 10)
					orientacaoVetorEmLinha = 'c';
				if (orientacao == -10)
					orientacaoVetorEmLinha = 'b';
				
				numAtualErros = 0;
				geraLinhaCelulasPossiveis(orientacaoVetorEmLinha);
				geraCelulaInteligente();
				
				System.out.println("Gerei uma linha");
				imprimirCelulasGeradas();
			} else if ((!acertouUltima) && acertouPenultima) { // Gera linha no
																// sentido
																// contrario
				inverteOrientacao();
				geraLinhaCelulasPossiveis(orientacaoVetorEmLinha);
				
				geraCelulaInteligente();
				System.out.println("Gerando em linha revertida");
			} else if ((!acertouUltima) && (!acertouPenultima))
				geraCelulaAleatoria();
		//}//if
	}
	
	public void inverteOrientacao(){
		if(orientacaoVetorEmLinha == 'c')
			orientacaoVetorEmLinha = 'b';
		else if(orientacaoVetorEmLinha == 'b')
			orientacaoVetorEmLinha = 'c';
		else if(orientacaoVetorEmLinha == 'd')
			orientacaoVetorEmLinha = 'e';
		else if(orientacaoVetorEmLinha == 'e')
			orientacaoVetorEmLinha = 'd';
	}
	
	public void contabilizaErro(){
		if(jogandoEmCruz || jogandoEmLinha)
			numAtualErros += 1;
		
		if(numAtualErros == 3 && jogandoEmCruz){
			jogandoEmCruz = false;
			jogandoEmLinha = true;
			numAtualErros = 0;
		}else if(numAtualErros == 2 && jogandoEmLinha){
			jogandoEmLinha = false;
			numAtualErros = 0;	
		}
	}
	public void imprimirCelulasGeradas(){
		for(int i = 0; i < celulasPossiveis.length; i++)
			System.out.println(celulasPossiveis[i]);
		
		System.out.println("------------------------------------");
	}
}
