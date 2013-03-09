package Janelas;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class MainWindow {

	JFrame janela;
	private Jogador jogador;
	Painel painel, maquina, auxPainel, auxMaquina; // o auxPainel serve para
													// mostrar onde a maquina
													// jogou
	JButton jogar, pausar, parar;
	PainelEsconde esconde;
	JLabel dica, tempoRestante, pontuacaoMaquina, pontuacaoJogador,
			nomeJogador, nomeMaquina, mostraTurno;
	Peca p2v, p3v, p4v, p5v;

	Timer relogio, relogioAux, verificaSeJogou;
	int tempoParaJogar = 10, tempoQueMaquinaJoga;
	int turno = 0;// 0 - usuario, 1 = maquina
	private int pontoMaquina = 0, pontoJogador = 0, pontoJogadorAnterior = 0,
			pontoMaquinaAnterior = 0;
	Avatar avatarPC, avatarJogador, versus;
	
	Maquina maq;

	public static void centerContainer(Container container) {
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		int componentWidth = container.getWidth();
		int componentHeight = container.getHeight();
		container.setBounds((screenSize.width - componentWidth) / 2,
				(screenSize.height - componentHeight) / 2, componentWidth,
				componentHeight);
	}

	public MainWindow() {

		janela = new JFrame("Meme Naval v4.0 ®");
		janela.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		painel = new Painel(new ImageIcon("Images/bgTabelaAzul.jpg").getImage());
		auxPainel = new Painel();
		mostraTurno = new JLabel();
		
		// auxMaquina = new Painel();

		// Cria avatares
		avatarJogador = new Avatar(330, 560,
				"Images/Avatar/Jogador/challenge.png");
		avatarPC = new Avatar(80, 560, "Images/Avatar/PC/challenge.png");
		nomeJogador = new JLabel();
		nomeMaquina = new JLabel();
		nomeJogador.setText("Você ");
		nomeMaquina.setText("PC");
		nomeJogador.setBounds((45 + avatarJogador.getX()),
				(avatarJogador.getY() - 25), 100, 20);
		nomeMaquina.setBounds((52 + avatarPC.getX()), (avatarPC.getY() - 25),
				100, 20);
		versus = new Avatar(230, 595, "Images/Avatar/vs.png");
		mostraTurno.setText("Turno: Você");
		janela.add(avatarJogador);
		janela.add(avatarPC);
		janela.add(versus);
		janela.add(nomeJogador);
		janela.add(nomeMaquina);

		/*
		 * dica = new JLabel(
		 * "Arraste pela borda superior ou esquerda para uma melhor precisao.");
		 * dica2 = new JLabel("Dois cliques para rotacionar."")
		 */

		pontuacaoJogador = new JLabel();
		pontuacaoMaquina = new JLabel();

		jogador = new Jogador();
		p2v = new Peca(0, 500, 2, 0, "Images/Navios/2dim", "H.png");
		p3v = new Peca(0, 570, 3, 0, "Images/Navios/3dim", "H.png");
		p4v = new Peca(250, 500, 4, 0, "Images/Navios/4dim", "H.png");
		p5v = new Peca(250, 570, 5, 0, "Images/Navios/5dim", "H.png");

		janela.setLayout(null);
		painel.setLayout(null);
		auxPainel.setLayout(null);
		// auxMaquina.setLayout(null);

		painel.add(p2v);
		painel.add(p3v);
		painel.add(p4v);
		painel.add(p5v);

		MouseAdapter cliquePainelMaquina = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jogador.xi = e.getX();
				jogador.yi = e.getY();
			}
		};

		MouseAdapter mousePressed = new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				// verifica se foi um duplo clique na pe�a enquanto ela est�
				// resetada
				if ((e.getClickCount() == 2) && (painel.objCLicado)
						&& (painel.isEnabled())) {// Se ocorreu duplo clique em
													// um objeto
					Peca pc = (Peca) painel.getComponent(painel.ID);

					if (pc.resetada) {// Se objeto estava como resetado
						painel.remove(pc);
						janela.repaint();

						if (pc.getRotacao() == 0)// Verifica se a peca estava na
													// horizontal
							pc = new Peca(pc.getX0(), pc.getY0(),
									pc.getCasas(), 1, pc.getImgPath(), "V.png");// Instancia
																				// a
																				// peca
																				// na
						// vertical
						else
							// Caso contrario
							pc = new Peca(pc.getX0(), pc.getY0(),
									pc.getCasas(), 0, pc.getImgPath(), "H.png");// Instancia
																				// a
																				// peca
																				// na
						// horizontal

						painel.add(pc);
						janela.repaint();
					}
				}
			}

			public void mousePressed(MouseEvent e) {

				if (painel.isEnabled()) {
					int x, y, w, h;
					// Procura em todos os objetos do painel onde o mouse foi
					// pressionado, qual deles sofreu o clique
					for (int i = 0; i < ((Painel) e.getSource())
							.getComponentCount(); i++) {
						x = ((JLabel) (((Painel) e.getSource()).getComponent(i)))
								.getX();
						y = ((JLabel) (((Painel) e.getSource()).getComponent(i)))
								.getY();
						w = ((JLabel) (((Painel) e.getSource()).getComponent(i)))
								.getWidth();
						h = ((JLabel) (((Painel) e.getSource()).getComponent(i)))
								.getHeight();

						if (((e.getX() >= x) && (e.getX() <= (x + w)))// Se
																		// achar
																		// o
																		// objeto
																		// que
																		// ocorreu
																		// o
																		// clique
								&& ((e.getY() >= y) && (e.getY() <= (y + h)))) {
							((Painel) e.getSource()).objCLicado = true;// Marco
																		// pro
																		// painel
																		// que
																		// um
																		// objeto
																		// foi
																		// clicado
							((Painel) e.getSource()).ID = i;// Guardo o ID deste
															// objeto no painel
							((Painel) e.getSource()).x_click = e.getX()
									- ((JLabel) (((Painel) e.getSource())
											.getComponent(i))).getX();
							((Painel) e.getSource()).y_click = e.getY()
									- ((JLabel) (((Painel) e.getSource())
											.getComponent(i))).getY();
							break;// Paro de procurar por outros objetos
						} else
							// Se nao achar nenhum objeto clicado
							((Painel) e.getSource()).objCLicado = false;// Marca
																		// para
																		// o
																		// painel
																		// que
																		// nenhum
																		// objeto
																		// foi
																		// clicado

					}
				}
			}

			public void mouseReleased(MouseEvent e) {

				if (painel.isEnabled()) {
					Integer i, j, x, y, xLimite, yLimite;
					int ID = ((Painel) e.getSource()).ID;
					Painel p = ((Painel) e.getSource());
					Peca peca = ((Peca) (((Painel) e.getSource())
							.getComponent(ID)));

					int casas = peca.getCasas();
					int rotacao = peca.getRotacao();

					xLimite = p.getWidth() - peca.getWidth();
					yLimite = p.getHeight() - peca.getHeight() - 100;

					if (p.objCLicado) {
						if (e.getX() > xLimite) {
							x = xLimite;
						} else {
							x = e.getX();
						}// Fecha x limite

						if (e.getY() > yLimite) {
							y = yLimite;
							peca.resetada = true;
						} else {
							y = e.getY();
							peca.resetada = false;
						}

						if (!peca.resetada) {
							i = x / 50;
							j = y / 50;

							String ji = String.valueOf(j) + String.valueOf(i);
							int JI = Integer.valueOf(ji);
							boolean podeMover = true;

							if (rotacao == 0) {
								for (int c = 0; c < casas; c++) {
									if (p.pos.get(JI + c).preenchido) {
										podeMover = false;
										break;
									}
								}
							} else {
								for (int c = 0; c < casas; c++) {
									if (p.pos.get(JI + (c * 10)).preenchido) {
										podeMover = false;
										break;
									}
								}
							}

							if (podeMover) {
								if (rotacao == 0) {
									for (int c = 0; c < casas; c++) {
										p.pos.get(JI + c).preenchido = true;
									}
								} else {
									for (int c = 0; c < casas; c++) {
										p.pos.get(JI + (c * 10)).preenchido = true;
									}
								}
								peca.setLocation(i * 50, j * 50);
								peca.setNovaXY(i * 50, j * 50);
								peca.setJI(JI);
								// peca.resetada = false;
								painel.repaint();
							} else {
								peca.setLocation(peca.getXi(), peca.getYi());
								if (rotacao == 0) {
									for (int c = 0; c < casas; c++) {
										p.pos.get(peca.getJI() + c).preenchido = true;
									}
								} else {
									for (int c = 0; c < casas; c++) {
										p.pos.get(peca.getJI() + (c * 10)).preenchido = true;
									}
								}
							}

						} else { // Caso peca resetada
							painel.remove(peca);
							peca = new Peca(peca.getX0(), peca.getY0(),
									peca.getCasas(), peca.getRotacao(),
									peca.getImgPath(), peca.getImgOrientacao());
							painel.add(peca);

							peca.revalidate();
							painel.repaint();
							janela.repaint();

						}// Fecha if que checa se ela ta como resetada ou n�o

					}// Fecha if de painel objeto clicado
					janela.repaint();
				}// fecha if painel habilitado

			}// fecha listener release

		};// fecha classe de todo o action

		MouseMotionAdapter mml_painel = new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {
				int x, y, xLimite, yLimite;
				int ID = ((Painel) e.getSource()).ID;
				Peca peca = ((Peca) (((JPanel) e.getSource()).getComponent(ID)));
				Painel p = ((Painel) e.getSource());

				if ((p.objCLicado) && (p.isEnabled())) {
					x = e.getX() - p.x_click;
					y = e.getY() - p.y_click;
					xLimite = p.getWidth() - peca.getWidth();
					yLimite = p.getHeight() - peca.getHeight();

					if (peca.getRotacao() == 0) {
						for (int c = 0; c < peca.getCasas(); c++)
							p.pos.get(peca.getJI() + c).preenchido = false;
					} else {
						for (int c = 0; c < peca.getCasas(); c++)
							p.pos.get(peca.getJI() + (c * 10)).preenchido = false;
					}

					if (x < 0)
						x = 0;

					if (y < 0)
						y = 0;

					if (x > xLimite)
						x = xLimite;

					if (y > yLimite)
						y = yLimite;

					peca.setLocation(x, y);
					janela.repaint();
				}
			}
		};

		painel.addMouseMotionListener(mml_painel);
		painel.addMouseListener(mousePressed);

		janela.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				Object[] options = { "Vou largar de barriga!",
						"Tá me estranhando, mano?!" };
				int opc = JOptionPane
						.showOptionDialog(
								null,
								"Fechando o jogo, você perderá todo o progresso caso o jogo já tenha iniciado.\n"
										+ "Deseja fechar mesmo assim?",
								"Fechar o jogo", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options, 1);
				if (opc == 0)
					System.exit(1);

			}
		});

		// janela.add(new PainelEsconde(painel));
		janela.add(auxPainel);
		janela.add(painel);
		// janela.add(dica);

		janela.setBounds(100, 100, 1200, 780);
		// Adiciona painel do usuario na janela do jogo, localizado no canto
		// direito
		painel.setBounds((janela.getWidth() - 525), 10, 500, 650);
		// painel.setBorder(new LineBorder(Color.RED));
		// dica.setBounds((painel.getX() - 25),(painel.getHeight() +
		// painel.getY()), 550, 20);
		auxPainel.setBounds(painel.getX(), painel.getY(), painel.getWidth(),
				painel.getHeight());

		// Cria janela da maquina
		maquina = new Painel(
				new ImageIcon("Images/bgTabelaAzul.jpg").getImage());
		maquina.setLayout(null);
		maquina.setBounds(10, 10, 500, 500);
		// auxMaquina.setBounds(maquina.getX(), maquina.getY(),
		// maquina.getWidth(), maquina.getHeight());
		esconde = new PainelEsconde(maquina);
		// maquina.addMouseListener(cliquePainelMaquina);
		janela.add(esconde);
		// janela.add(auxMaquina);

		// Adiciona pecas em posicoes fixas para a maquina
		// PS: Fazer um metodo para criar precas em posicoes aleatorias
		maq = new Maquina(maquina, painel, auxPainel);

		maq.montaJogoMaquina();

		janela.add(maquina);

		// Adiciona os demais elementos da tela do jogo

		// Clique do botao Jogar
		MouseAdapter play = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!existsPecaResetada()) {
					relogio.start();
					relogioAux.start();
					verificaSeJogou.start();

					esconde.habilitaPainel(true);
					esconde.setJogoIniciado(true);
					((JButton) e.getSource()).setEnabled(false);
					janela.repaint();

					painel.setEnabled(false);
					pausar.setEnabled(true);
					parar.setEnabled(true);

				} else
					JOptionPane
							.showMessageDialog(null,
									"Termine de montar o seu jogo para iniciar a partida");
			}
		};

		// Clique do botao Pausar/Continuar
		MouseAdapter pausarJogo = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (relogio.isRunning()) {
					relogio.stop();
					relogioAux.stop();
					verificaSeJogou.stop();

					pausar.setText("Continuar");
				} else {
					relogio.start();
					relogioAux.start();
					verificaSeJogou.start();

					pausar.setText("Pausar");
				}
			}
		};

		// Clique do botao Parar
		MouseAdapter pararJogo = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Object[] options = { "Partiu!", "Ta loucona ow?!" };
				int opc = JOptionPane.showOptionDialog(null,
						"Parando o jogo, você perderá todo o progresso.\n"
								+ "Deseja parar mesmo assim?", "Parar o jogo",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, 1);

				if ((relogio.isRunning()) && (opc == 0)) {
					relogio.stop();
					relogioAux.stop();
					verificaSeJogou.stop();

					tempoParaJogar = 10;
					tempoRestante.setText("Tempo: 10 seg.");

					jogar.setEnabled(true);
					pausar.setEnabled(false);
					parar.setEnabled(false);

					resetaPainelJogador();
					resetaPontuacao();
					resetaPainelAux();
					resetaMaquina();
					resetaAvatar();
				}
			}
		};

		// Relogios
		// Imprime tempo restante de 1 em 1 segundo na tela
		ActionListener relogioTela = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tempoParaJogar == 10)
					tempoQueMaquinaJoga = 7;

				tempoParaJogar -= 1;
				tempoRestante.setText("Tempo: "
						+ String.valueOf(tempoParaJogar) + " seg.");

				if ((turno == 1) && (tempoParaJogar == tempoQueMaquinaJoga)) {
					maq.jogar();
					// Peca p = null;
					if (maq.acertouUltima) {
						alterarPontuacao(maq.pontoMaquina, 1);
					}
					esconde.habilitaPainel(true); // habilito o painel da
													// maquina
				}
				/*
				 * if (tempoParaJogar == 9) { if (jogadorGanhou()) {
				 * JOptionPane.showMessageDialog(null, "Aff! voce ganhou -.-'");
				 * fimDeJogo(); } else if (maquinaGanhou()) {
				 * JOptionPane.showMessageDialog(null,
				 * "Noooooob! Voce perdeu :P"); fimDeJogo(); } }
				 */
			}
		};

		// Verifica se o usuario ja jogou, se alguem ja ganhou ou qualquer outra
		// coisa desejada
		ActionListener verificaJogo = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if ((!esconde.isHabilitado()) && (turno == 0)) {
					turno = 1;
					tempoParaJogar = 10;
					relogio.restart();

					if (maquina.acertouTiro) {
						pontoJogador++;
						alterarPontuacao(pontoJogador, 0);
						// pontuacaoJogador.setText("Voce: " + pontoJogador +
						// " ponto(s)");

					}

				} else if ((esconde.isHabilitado()) && (turno == 1)) {
					turno = 0; // passo a vez pro jogador
					tempoParaJogar = 10;
					relogio.restart();
				}
				if(turno == 1)
					mostraTurno.setText("Turno: Máquina");
				else
					mostraTurno.setText("Turno: Você");
			}
		};

		// Relogio que bate a cada 10 segundos
		ActionListener bateuTempo = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				tempoParaJogar = 10;

				if (turno == 0)// Se o jogador estava jogando
					turno = 1;// Maquina joga neste turno
				else
					// Caso contrario
					turno = 0;// Jogador joga

				if (turno == 1)// Se o turno eh da maquina
					esconde.habilitaPainel(false);
				else
					esconde.habilitaPainel(true);

			}
		};

		relogio = new Timer(10000, bateuTempo);// tempo total da partida
		relogioAux = new Timer(1000, relogioTela);// tempo que msotra no visor,
													// atualizando a cada
													// segundo
		verificaSeJogou = new Timer(50, verificaJogo);// ve como anda os status
														// do jogo a cada decimo
														// de segundo

		tempoRestante = new JLabel();
		tempoRestante.setBounds(550, 10, 100, 20);
		tempoRestante.setText("Tempo: 10 seg.");
		mostraTurno.setBounds(550, 80, 100, 25);
		
		jogar = new JButton("Jogar");
		pausar = new JButton("Pausar");
		parar = new JButton("Parar");

		jogar.setBounds(545, 395, 100, 30);
		pausar.setBounds(jogar.getX(), (jogar.getY() + jogar.getHeight() + 10),
				jogar.getWidth(), jogar.getHeight());
		parar.setBounds(jogar.getX(),
				(pausar.getY() + pausar.getHeight() + 10), jogar.getWidth(),
				jogar.getHeight());

		pontuacaoJogador.setBounds(545, 130, 100, 30);
		pontuacaoMaquina.setBounds(545, 150, 100, 30);
		pontuacaoJogador.setText("Você: " + pontoJogador + " ponto(s)");
		pontuacaoMaquina.setText("PC: " + pontoMaquina + " ponto(s)");

		jogar.addMouseListener(play);
		pausar.addMouseListener(pausarJogo);
		parar.addMouseListener(pararJogo);

		pausar.setEnabled(false);
		parar.setEnabled(false);

		janela.add(jogar);
		janela.add(pausar);
		janela.add(parar);
		janela.add(tempoRestante);
		janela.add(pontuacaoJogador);
		janela.add(pontuacaoMaquina);
		janela.add(mostraTurno);

		// Finalizo a criacao da janela
		janela.setBounds(100, 100, 1200, 720);
		janela.setResizable(false);
		janela.setVisible(true);
		janela.repaint();

		centerContainer(janela);

		Icon info = new ImageIcon("Images/info.png");
		JOptionPane
				.showMessageDialog(
						null,
						" MemeNaval - versão 4.0 ® \n\n"
								+ " Dicas para o jogo \n\n"
								+ " 1. Para melhor precisão na montagem de seu tabuleiro, arraste as embarcações a partir da borda superior para o mar. \n"
								+ " 2. Dê um clique duplo sobre a embarcação para rotacioná-la. \n\n"
								+ "Sobre:\n\n"
								+ "Trabalho Final de Computação II\n"
								+ "UFRRJ\n"
								+ "Professor Carlos Eduardo Mello\n\n"
								+ "Desenvolvido por:\n" + "Jefferson Rangel\n"
								+ "Raíza Santana\n" + "Válber Laux",
						"Projeto Final de Computação II - Dez/2011", 0, info);

	}

	// Verifica se existe peca sua posicao inicial e retorna true ou false
	public boolean existsPecaResetada() {
		boolean temPecaResetada = false;

		for (int i = 0; i < painel.getComponentCount(); i++) {
			if (((Peca) painel.getComponent(i)).resetada) {
				temPecaResetada = true;
				break;
			}
		}
		if (temPecaResetada)
			return true;
		else
			return false;
	}

	public void resetaPainelJogador() {
		painel.setEnabled(false);

		// Mutreta estupida pra conseguir remover as pecas do painel
		for (int i = 0; i < painel.getComponentCount(); i++)
			// garantir a remocao da primeira peca
			painel.remove(i);

		if (painel.getComponentCount() > 0) {
			for (int i = 0; i < painel.getComponentCount(); i++)
				// garantir a remocao da segunda peca
				painel.remove(i);
		}

		if (painel.getComponentCount() > 0) {
			for (int i = 0; i < painel.getComponentCount(); i++)
				// garantir a remocao da terceira peca
				painel.remove(i);
		}

		if (painel.getComponentCount() > 0) {
			for (int i = 0; i < painel.getComponentCount(); i++)
				// garantir a remocao da quarta peca
				painel.remove(i);
		}

		painel.resetaCelulas();
		janela.repaint();

		p2v = new Peca(0, 500, 2, 0, "Images/Navios/2dim", "H.png");
		p3v = new Peca(0, 570, 3, 0, "Images/Navios/3dim", "H.png");
		p4v = new Peca(250, 500, 4, 0, "Images/Navios/4dim", "H.png");
		p5v = new Peca(250, 570, 5, 0, "Images/Navios/5dim", "H.png");

		janela.repaint();
		painel.add(p2v);
		painel.add(p3v);
		painel.add(p4v);
		painel.add(p5v);

		painel.resetaCelulas();
		painel.repaint();
		janela.repaint();
		painel.setEnabled(true);

	}

	public void resetaPontuacao() {
		pontoJogador = 0;
		pontoMaquina = 0;

		pontuacaoJogador.setText("Você: " + pontoJogador + " ponto(s)");
		pontuacaoMaquina.setText("PC: " + pontoMaquina + " ponto(s)");
	}

	public void resetaPainelAux() {
		while (auxPainel.getComponentCount() > 0) {
			for (int i = 0; i < auxPainel.getComponentCount(); i++) {
				auxPainel.remove(i);
			}
		}
		auxPainel = new Painel();
		auxPainel.setBounds(painel.getX(), painel.getY(), painel.getWidth(),
				painel.getHeight());

		auxPainel.repaint();
		janela.repaint();
	}

	public void resetaMaquina() {
		while (maquina.getComponentCount() > 0) {
			for (int i = 0; i < maquina.getComponentCount(); i++) {
				maquina.remove(i);
			}
		}
		esconde = new PainelEsconde(maquina);
		maq.montaJogoMaquina();
		// esconde = new PainelEsconde();
		// auxMaquina.repaint();
		esconde.repaint();
		janela.repaint();
	}

	public boolean jogadorGanhou() {
		if (pontoJogador == 14)
			return true;
		else
			return false;
	}

	public boolean maquinaGanhou() {
		if (maq.pontoMaquina == 14)
			return true;
		else
			return false;
	}

	public void fimDeJogo() {
		relogio.stop();
		relogioAux.stop();
		verificaSeJogou.stop();

		tempoParaJogar = 10;
		tempoRestante.setText("Tempo: 10 seg.");

		jogar.setEnabled(true);
		pausar.setEnabled(false);
		parar.setEnabled(false);

		resetaPainelJogador();
		resetaPontuacao();
		resetaPainelAux();
		resetaMaquina();
		resetaAvatar();
	}

	// Metodo para mudar o painel após o tiro
	public void alterarPontuacao(int pontuacao, int turno) {
		if (turno == 0)
			pontuacaoJogador.setText("Jogador: " + pontuacao + " ponto(s)");
		else
			pontuacaoMaquina.setText("PC: " + pontuacao + " ponto(s)");
		
		mudaAvatar(pontoJogador, maq.pontoMaquina);
		
		if (jogadorGanhou()) {
			JOptionPane.showMessageDialog(null, "Aff! você ganhou -.-'");
			fimDeJogo();
		} else if (maquinaGanhou()) {
			JOptionPane.showMessageDialog(null, "Noooooob! Você perdeu :P");
			fimDeJogo();
		}

	}

	public Peca getPeca(int celula) {
		for (int i = 0; i < painel.getComponentCount(); i++) {
			if (((Peca) (painel.getComponent(i))).contemCelula(celula)) {
				return ((Peca) (painel.getComponent(i)));
			}
		}

		return null;
	}

	private void mudaAvatar(int pontuacaoJogador, int pontoMaquina) {

		if ((pontoMaquina != pontoMaquinaAnterior)
				|| (pontuacaoJogador != pontoJogadorAnterior)) {
			janela.remove(avatarJogador);
			janela.remove(avatarPC);
			janela.repaint();

			// Primeira pontuação
			if (pontoMaquina == 1 && pontoJogador == 0) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/poderface.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/marrento.png");
			}

			if (pontoMaquina == 0 && pontoJogador == 1) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/marrento.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/poderface.png");
			}

			// Maquina ou jogador com grande vantagem
			if ((pontoMaquina > 2 * pontoJogador) && (pontoMaquina > 1)) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/lol.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/fuckyeah.png");
			} else if ((pontoJogador > 2 * pontoMaquina) && (pontoJogador > 1)) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/fuckyeah.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/lol.png");
			}

			// Aproximacao de 2 pontos
			if (((pontoJogador - pontoMaquina) == 2) && (pontoMaquina != 0)) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/marrento.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/megusta.png");
			} else if (((pontoMaquina - pontoJogador) == 2)
					&& (pontoJogador != 0)) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/megusta.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/marrento.png");
			}

			// Aproximacao de 1 ponto
			if (((pontoJogador - pontoMaquina) == 1) && (pontoMaquina != 0)) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/poderface.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/megusta.png");
			} else if (((pontoMaquina - pontoJogador) == 1)
					&& (pontoJogador != 0)) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/megusta.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/poderface.png");
			}

			// Pontuacao igual e não zerada
			if ((pontoJogador == pontoMaquina) && (pontoJogador != 0)) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/poderface.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/poderface.png");
			}
			
			// WIN!!!
			if (pontoMaquina >= 14 && pontoJogador < 14) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/fu.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/awyeah.png");
			} else if (pontoMaquina < 14 && pontoJogador >= 14) {
				avatarJogador = new Avatar(330, 560,
						"Images/Avatar/Jogador/awyeah.png");
				avatarPC = new Avatar(80, 560, "Images/Avatar/PC/fu.png");
			}

			janela.add(avatarJogador);
			janela.add(avatarPC);
			janela.repaint();

			pontoMaquinaAnterior = pontoMaquina;
			pontoJogadorAnterior = pontoJogador;
		}// if

	}

	public void resetaAvatar() {
		janela.remove(avatarJogador);
		janela.remove(avatarPC);
		janela.repaint();

		avatarJogador = new Avatar(330, 560,
				"Images/Avatar/Jogador/challenge.png");
		avatarPC = new Avatar(80, 560, "Images/Avatar/PC/challenge.png");

		janela.add(avatarJogador);
		janela.add(avatarPC);
		janela.repaint();
	}
}
