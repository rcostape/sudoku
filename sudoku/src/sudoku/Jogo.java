package sudoku;

import java.util.ArrayList;
import java.util.Scanner;

public class Jogo {
	private Celula[][] tabuleiro = new Celula[9][9];
	
	
	private Jogo () {
		this.zeraTabuleirio();
		
	}
	
	private Jogo (ArrayList<Celula> posicoesIniciais) {
		this.zeraTabuleirio();
		
		for (Celula celulaPreenchida : posicoesIniciais) {
			this.tabuleiro[celulaPreenchida.getPosicaoX()][celulaPreenchida.getPosicaoY()].setPosicaoX(celulaPreenchida.getPosicaoX());
			this.tabuleiro[celulaPreenchida.getPosicaoX()][celulaPreenchida.getPosicaoY()].setPosicaoY(celulaPreenchida.getPosicaoY());
			this.tabuleiro[celulaPreenchida.getPosicaoX()][celulaPreenchida.getPosicaoY()].setValor(celulaPreenchida.getValor());	
			this.tabuleiro[celulaPreenchida.getPosicaoX()][celulaPreenchida.getPosicaoY()].setPermiteAlterar(false);	
			this.tabuleiro[celulaPreenchida.getPosicaoX()][celulaPreenchida.getPosicaoY()].setEstaVazio(false);
			this.tabuleiro[celulaPreenchida.getPosicaoX()][celulaPreenchida.getPosicaoY()].setGrupo(this.pegaGrupo(celulaPreenchida));
		}
	}
	
	private void zeraTabuleirio() {
		
		for( int i = 0; i < 9; i++ )
			for (int j = 0; j < 9; j++ )
				{
					{
						this.tabuleiro[i][j] = new Celula();
					}
				}
	}
	
	private int pegaGrupo (Celula celula) {
		int retorno = 0;
		if (celula.getPosicaoX() < 3 && celula.getPosicaoY() < 3) {retorno = 1;}
		if (celula.getPosicaoX() < 3 && (celula.getPosicaoY() > 2 && celula.getPosicaoY() < 6)) {retorno = 2;}
		if (celula.getPosicaoX() < 3 && (celula.getPosicaoY() > 5 && celula.getPosicaoY() < 9)) {retorno = 3;}
		if ((celula.getPosicaoX() > 2 && celula.getPosicaoX() < 6) && celula.getPosicaoY() < 3) {retorno = 4;}
		if ((celula.getPosicaoX() > 2 && celula.getPosicaoX() < 6) && (celula.getPosicaoY() > 2 && celula.getPosicaoY() < 6)) {retorno = 5;}
		if ((celula.getPosicaoX() > 2 && celula.getPosicaoX() < 6) && (celula.getPosicaoY() > 5 && celula.getPosicaoY() < 9)) {retorno = 6;}
		if (celula.getPosicaoX() > 5 && celula.getPosicaoY() < 3) {retorno = 7;}
		if (celula.getPosicaoX() > 5 && (celula.getPosicaoY() > 2 && celula.getPosicaoY() < 6)) {retorno = 8;}
		if (celula.getPosicaoX() > 5 && celula.getPosicaoY() > 5) {retorno = 9;}
		
		return retorno;
	}
	
	private int jogar (Celula celulaJogada) {
		int retorno = 0;
		if (posicaoLivre (celulaJogada) || podeSubstituir (celulaJogada)) {
			if (podeJogar(celulaJogada)) {
				realizaJogada(celulaJogada);
				retorno = 1;
			}
			else {
				retorno = -2;
			}
		}
		else{
			retorno = -1;
		}
		
		return retorno;
	}
	
	private boolean posicaoLivre (Celula celulaJogada) {
		boolean retorno = false;
		if(this.tabuleiro[celulaJogada.getPosicaoX()][celulaJogada.getPosicaoY()].isEstaVazio()) {
			retorno = true;			
		}
		return retorno;
	}
	
	private boolean podeSubstituir (Celula celulaJogada) {
		boolean retorno = false;
		if(this.tabuleiro[celulaJogada.getPosicaoX()][celulaJogada.getPosicaoY()].isPermiteAlterar()) {
			retorno = true;			
		}
		return retorno;
	}
	
	private boolean podeJogar(Celula celulaJogada) {
		return verificaLinha(celulaJogada.getPosicaoX(), celulaJogada.getValor()) && 
				verificaColuna(celulaJogada.getPosicaoY(), celulaJogada.getValor()) && 
				verificaGrupo(pegaGrupo(celulaJogada), celulaJogada.getValor()) &&
				(celulaJogada.getValor() > 0 && celulaJogada.getValor() < 10);
	}
	
	private boolean verificaLinha(int linha, int valor) {
		boolean livre = true;
		for (int i = 0; i < 8; i++) {
			if (this.tabuleiro[linha][i].getValor() == valor) {
				livre = false;
				break;
			}			
		}
		return livre;
	}
	
	private boolean verificaColuna(int coluna, int valor) {
		boolean livre = true;
		for (int i = 0; i < 8; i++) {
			if (this.tabuleiro[i][coluna].getValor() == valor) {
				livre = false;
				break;
			}			
		}		
		return livre;
	}
	
	private boolean verificaGrupo(int grupo, int valor) {		
		return verificaExisteValorNoGrupo(retornaCelulasGrupo (grupo), valor);	
	
	}
	
	private Celula[][] retornaCelulasGrupo (int grupo){
		Celula[][] celulasGrupo = new Celula[3][3];
		switch (grupo) {
			case 1:
				for (int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						celulasGrupo[i][j] = this.tabuleiro[i][j];
					}
				}
				break;
			case 2:
				for (int i = 0; i < 3; i++) {
					for(int j = 3; j < 6; j++) {
						celulasGrupo[i][j-3] = this.tabuleiro[i][j];
					}
				}
				break;
			case 3:
				for (int i = 0; i < 3; i++) {
					for(int j = 6; j < 9; j++) {
						celulasGrupo[i][j-6] = this.tabuleiro[i][j];
					}
				}
				break;
			case 4:
				for (int i = 3; i < 6; i++) {
					for(int j = 0; j < 3; j++) {
						celulasGrupo[i-3][j] = this.tabuleiro[i][j];
					}
				}
				break;
			case 5:
				for (int i = 3; i < 6; i++) {
					for(int j = 3; j < 6; j++) {
						celulasGrupo[i-3][j-3] = this.tabuleiro[i][j];
					}
				}
				break;
			case 6:
				for (int i = 3; i < 6; i++) {
					for(int j = 6; j < 9; j++) {
						celulasGrupo[i-3][j-6] = this.tabuleiro[i][j];
					}
				}
				break;
			case 7:
				for (int i = 6; i < 9; i++) {
					for(int j = 0; j < 3; j++) {
						celulasGrupo[i-6][j] = this.tabuleiro[i][j];
					}
				}
				break;
			case 8:
				for (int i = 6; i < 9; i++) {
					for(int j = 3; j < 6; j++) {
						celulasGrupo[i-6][j-3] = this.tabuleiro[i][j];
					}
				}
				break;
			case 9:
				for (int i = 6; i < 9; i++) {
					for(int j = 6; j < 9; j++) {
						celulasGrupo[i-6][j-6] = this.tabuleiro[i][j];
					}
				}
				break;
		}
		
		return celulasGrupo;		
	}
	
	private boolean verificaExisteValorNoGrupo(Celula[][] grupo, int valor){
		boolean livre = true;
		for (int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(grupo[i][j].getValor() == valor) {
				livre = false;
				break;
				}
			}
		}				
		return livre;		
	}

	
	private void realizaJogada(Celula celulaJogada) {
		this.tabuleiro[celulaJogada.getPosicaoX()][celulaJogada.getPosicaoY()].setPosicaoX(celulaJogada.getPosicaoX());
		this.tabuleiro[celulaJogada.getPosicaoX()][celulaJogada.getPosicaoY()].setPosicaoY(celulaJogada.getPosicaoY());
		this.tabuleiro[celulaJogada.getPosicaoX()][celulaJogada.getPosicaoY()].setValor(celulaJogada.getValor());	
		this.tabuleiro[celulaJogada.getPosicaoX()][celulaJogada.getPosicaoY()].setPermiteAlterar(true);	
		this.tabuleiro[celulaJogada.getPosicaoX()][celulaJogada.getPosicaoY()].setEstaVazio(false);
		this.tabuleiro[celulaJogada.getPosicaoX()][celulaJogada.getPosicaoY()].setGrupo(this.pegaGrupo(celulaJogada));
		
	}

	private void printTabuleiro() {
		System.out.println("_______________________________________");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("| "+this.tabuleiro[0][0]+" | "+this.tabuleiro[0][1]+" | "+this.tabuleiro[0][2]+" || "+this.tabuleiro[0][3]+" | "+this.tabuleiro[0][4]+" | "+this.tabuleiro[0][5]+" || "+this.tabuleiro[0][6]+" | "+this.tabuleiro[0][7]+" | "+this.tabuleiro[0][8]+" |");
		System.out.println("|___|___|___||___|___|___||___|___|___|");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("| "+this.tabuleiro[1][0]+" | "+this.tabuleiro[1][1]+" | "+this.tabuleiro[1][2]+" || "+this.tabuleiro[1][3]+" | "+this.tabuleiro[1][4]+" | "+this.tabuleiro[1][5]+" || "+this.tabuleiro[1][6]+" | "+this.tabuleiro[1][7]+" | "+this.tabuleiro[1][8]+" |");
		System.out.println("|___|___|___||___|___|___||___|___|___|");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("| "+this.tabuleiro[2][0]+" | "+this.tabuleiro[2][1]+" | "+this.tabuleiro[2][2]+" || "+this.tabuleiro[2][3]+" | "+this.tabuleiro[2][4]+" | "+this.tabuleiro[2][5]+" || "+this.tabuleiro[2][6]+" | "+this.tabuleiro[2][7]+" | "+this.tabuleiro[2][8]+" |");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("|---|---|---||---|---|---||---|---|---|");
		System.out.println("|---|---|---||---|---|---||---|---|---|");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("| "+this.tabuleiro[3][0]+" | "+this.tabuleiro[3][1]+" | "+this.tabuleiro[3][2]+" || "+this.tabuleiro[3][3]+" | "+this.tabuleiro[3][4]+" | "+this.tabuleiro[3][5]+" || "+this.tabuleiro[3][6]+" | "+this.tabuleiro[3][7]+" | "+this.tabuleiro[3][8]+" |");
		System.out.println("|___|___|___||___|___|___||___|___|___|");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("| "+this.tabuleiro[4][0]+" | "+this.tabuleiro[4][1]+" | "+this.tabuleiro[4][2]+" || "+this.tabuleiro[4][3]+" | "+this.tabuleiro[4][4]+" | "+this.tabuleiro[4][5]+" || "+this.tabuleiro[4][6]+" | "+this.tabuleiro[4][7]+" | "+this.tabuleiro[4][8]+" |");
		System.out.println("|___|___|___||___|___|___||___|___|___|");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("| "+this.tabuleiro[5][0]+" | "+this.tabuleiro[5][1]+" | "+this.tabuleiro[5][2]+" || "+this.tabuleiro[5][3]+" | "+this.tabuleiro[5][4]+" | "+this.tabuleiro[5][5]+" || "+this.tabuleiro[5][6]+" | "+this.tabuleiro[5][7]+" | "+this.tabuleiro[5][8]+" |");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("|---|---|---||---|---|---||---|---|---|");
		System.out.println("|---|---|---||---|---|---||---|---|---|");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("| "+this.tabuleiro[6][0]+" | "+this.tabuleiro[6][1]+" | "+this.tabuleiro[6][2]+" || "+this.tabuleiro[6][3]+" | "+this.tabuleiro[6][4]+" | "+this.tabuleiro[6][5]+" || "+this.tabuleiro[6][6]+" | "+this.tabuleiro[6][7]+" | "+this.tabuleiro[6][8]+" |");
		System.out.println("|___|___|___||___|___|___||___|___|___|");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("| "+this.tabuleiro[7][0]+" | "+this.tabuleiro[7][1]+" | "+this.tabuleiro[7][2]+" || "+this.tabuleiro[7][3]+" | "+this.tabuleiro[7][4]+" | "+this.tabuleiro[7][5]+" || "+this.tabuleiro[7][6]+" | "+this.tabuleiro[7][7]+" | "+this.tabuleiro[7][8]+" |");
		System.out.println("|___|___|___||___|___|___||___|___|___|");
		System.out.println("|   |   |   ||   |   |   ||   |   |   |");
		System.out.println("| "+this.tabuleiro[8][0]+" | "+this.tabuleiro[8][1]+" | "+this.tabuleiro[8][2]+" || "+this.tabuleiro[8][3]+" | "+this.tabuleiro[8][4]+" | "+this.tabuleiro[8][5]+" || "+this.tabuleiro[8][6]+" | "+this.tabuleiro[8][7]+" | "+this.tabuleiro[8][8]+" |");
		System.out.println("|___|___|___||___|___|___||___|___|___|");
			
	}

	public void imprimeMsg(int msg) {
		if (msg < 0)
			System.out.println("Jogada não permitida!");		
	}
	
	public boolean finalizouJogo() {
		boolean retorno = false;
		for (int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(!(0 < this.tabuleiro[i][j].getValor() && this.tabuleiro[i][j].getValor() < 10)) {
					retorno = false;
					break;
				}
				else {
					retorno = true;
				}
			}
		}
		return retorno;
	}
	
	public static void main(String[] args) {
		
		Celula celula2 = new Celula(false, 5, false, 0, 0);
		Celula celula3 = new Celula(false, 6, false, 1, 0);
		Celula celula10 = new Celula(false, 1, false, 2, 0);
		Celula celula12 = new Celula(false, 3, false, 0, 1);
		Celula celula13 = new Celula(false, 7, false, 1, 1);
		Celula celula11 = new Celula(false, 9, false, 2, 1);
		Celula celula14 = new Celula(false, 4, false, 0, 2);
		Celula celula15 = new Celula(false, 2, false, 1, 2);
		Celula celula1 = new Celula(false, 8, false, 2, 2);
		
		Celula celula16 = new Celula(false, 6, false, 0, 3);
		Celula celula17 = new Celula(false, 1, false, 1, 3);
		Celula celula18 = new Celula(false, 3, false, 2, 3);
		Celula celula19 = new Celula(false, 7, false, 0, 4);
		Celula celula20 = new Celula(false, 9, false, 1, 4);
		Celula celula21 = new Celula(false, 4, false, 2, 4);
		Celula celula22 = new Celula(false, 8, false, 0, 5);
		Celula celula23 = new Celula(false, 5, false, 1, 5);
		Celula celula24 = new Celula(false, 2, false, 2, 5);
		
		Celula celula26 = new Celula(false, 9, false, 0, 6);
		Celula celula27 = new Celula(false, 3, false, 1, 6);
		Celula celula28 = new Celula(false, 5, false, 2, 6);
		Celula celula29 = new Celula(false, 1, false, 0, 7);
		Celula celula30 = new Celula(false, 4, false, 1, 7);
		Celula celula31 = new Celula(false, 6, false, 2, 7);
		Celula celula32 = new Celula(false, 2, false, 0, 8);
		Celula celula33 = new Celula(false, 8, false, 1, 8);
		Celula celula34 = new Celula(false, 7, false, 2, 8);
		
		Celula celula4 = new Celula(false, 8, false, 3, 0);
		Celula celula5 = new Celula(false, 4, false, 4, 0);
		Celula celula6 = new Celula(false, 7, false, 5, 0);
		Celula celula7 = new Celula(false, 5, false, 3, 1);
		Celula celula8 = new Celula(false, 2, false, 4, 1);
		Celula celula9 = new Celula(false, 1, false, 5, 1);
		Celula celula25 = new Celula(false, 9, false, 3, 2);
		Celula celula35 = new Celula(false, 6, false, 4, 2);
		Celula celula36 = new Celula(false, 3, false, 5, 2);
		
		Celula celula40 = new Celula(false, 7, false, 3, 3);
		Celula celula41 = new Celula(false, 8, false, 4, 3);
		Celula celula42 = new Celula(false, 9, false, 5, 3);
		Celula celula37 = new Celula(false, 6, false, 3, 4);
		Celula celula38 = new Celula(false, 5, false, 4, 4);
		Celula celula39 = new Celula(false, 2, false, 5, 4);
		Celula celula43 = new Celula(false, 1, false, 3, 5);
		Celula celula44 = new Celula(false, 3, false, 4, 5);
		Celula celula45 = new Celula(false, 4, false, 5, 5);
		
		Celula celula46 = new Celula(false, 4, false, 3, 6);
		Celula celula47 = new Celula(false, 7, false, 4, 6);
		Celula celula48 = new Celula(false, 8, false, 5, 6);
		Celula celula49 = new Celula(false, 2, false, 3, 7);
		Celula celula50 = new Celula(false, 9, false, 4, 7);
		Celula celula51 = new Celula(false, 5, false, 5, 7);
		Celula celula52 = new Celula(false, 3, false, 3, 8);
		Celula celula53 = new Celula(false, 1, false, 4, 8);
		Celula celula54 = new Celula(false, 6, false, 5, 8);
				
		Celula celula55 = new Celula(false, 9, false, 6, 0);
		Celula celula56 = new Celula(false, 2, false, 7, 0);
		Celula celula57 = new Celula(false, 3, false, 8, 0);
		Celula celula58 = new Celula(false, 6, false, 6, 1);
		Celula celula59 = new Celula(false, 8, false, 7, 1);
		Celula celula60 = new Celula(false, 4, false, 8, 1);
		Celula celula61 = new Celula(false, 1, false, 6, 2);
		Celula celula62 = new Celula(false, 7, false, 7, 2);
		Celula celula63 = new Celula(false, 5, false, 8, 2);
		
		Celula celula64 = new Celula(false, 5, false, 6, 3);
		Celula celula65 = new Celula(false, 4, false, 7, 3);
		Celula celula66 = new Celula(false, 2, false, 8, 3);
		Celula celula67 = new Celula(false, 3, false, 6, 4);
		Celula celula68 = new Celula(false, 1, false, 7, 4);
		Celula celula69 = new Celula(false, 8, false, 8, 4);
		Celula celula70 = new Celula(false, 7, false, 6, 5);
		Celula celula71 = new Celula(false, 9, false, 7, 5);
		Celula celula72 = new Celula(false, 6, false, 8, 5);
		
		Celula celula73 = new Celula(false, 2, false, 6, 6);
		Celula celula74 = new Celula(false, 6, false, 7, 6);
		Celula celula75 = new Celula(false, 1, false, 8, 6);
		Celula celula76 = new Celula(false, 8, false, 6, 7);
		Celula celula77 = new Celula(false, 3, false, 7, 7);
		Celula celula78 = new Celula(false, 7, false, 8, 7);
		Celula celula79 = new Celula(false, 4, false, 6, 8);
		//Celula celula80 = new Celula(false, 5, false, 1, 8);
		//Celula celula81 = new Celula(false, 9, false, 2, 8);
		
		
		ArrayList<Celula> celulasIniciais = new ArrayList<>();
		
		celulasIniciais.add(celula1);
		celulasIniciais.add(celula2);
		celulasIniciais.add(celula3);
		celulasIniciais.add(celula4);
		celulasIniciais.add(celula5);
		celulasIniciais.add(celula6);
		celulasIniciais.add(celula7);
		celulasIniciais.add(celula8);
		celulasIniciais.add(celula9);
		celulasIniciais.add(celula10);
		celulasIniciais.add(celula11);
		celulasIniciais.add(celula12);
		celulasIniciais.add(celula13);
		celulasIniciais.add(celula14);
		celulasIniciais.add(celula15);
		celulasIniciais.add(celula16);
		celulasIniciais.add(celula17);
		celulasIniciais.add(celula18);
		celulasIniciais.add(celula19);
		celulasIniciais.add(celula20);
		celulasIniciais.add(celula21);
		celulasIniciais.add(celula22);
		celulasIniciais.add(celula23);
		celulasIniciais.add(celula24);
		celulasIniciais.add(celula25);
		celulasIniciais.add(celula26);
		celulasIniciais.add(celula27);
		celulasIniciais.add(celula28);
		celulasIniciais.add(celula29);
		celulasIniciais.add(celula30);
		celulasIniciais.add(celula31);
		celulasIniciais.add(celula32);
		celulasIniciais.add(celula33);
		celulasIniciais.add(celula34);
		celulasIniciais.add(celula35);
		celulasIniciais.add(celula36);
		celulasIniciais.add(celula37);
		celulasIniciais.add(celula38);
		celulasIniciais.add(celula39);
		celulasIniciais.add(celula40);
		celulasIniciais.add(celula41);
		celulasIniciais.add(celula42);
		celulasIniciais.add(celula43);
		celulasIniciais.add(celula44);
		celulasIniciais.add(celula45);
		celulasIniciais.add(celula46);
		celulasIniciais.add(celula47);
		celulasIniciais.add(celula48);
		celulasIniciais.add(celula49);
		celulasIniciais.add(celula50);
		celulasIniciais.add(celula51);
		celulasIniciais.add(celula52);
		celulasIniciais.add(celula53);
		celulasIniciais.add(celula54);
		celulasIniciais.add(celula55);
		celulasIniciais.add(celula56);
		celulasIniciais.add(celula57);
		celulasIniciais.add(celula58);
		celulasIniciais.add(celula59);
		celulasIniciais.add(celula60);
		celulasIniciais.add(celula61);
		celulasIniciais.add(celula62);
		celulasIniciais.add(celula63);
		celulasIniciais.add(celula64);
		celulasIniciais.add(celula65);
		celulasIniciais.add(celula66);
		celulasIniciais.add(celula67);
		celulasIniciais.add(celula68);
		celulasIniciais.add(celula69);
		celulasIniciais.add(celula70);
		celulasIniciais.add(celula71);
		celulasIniciais.add(celula72);
		celulasIniciais.add(celula73);
		celulasIniciais.add(celula74);
		celulasIniciais.add(celula75);
		celulasIniciais.add(celula76);
		celulasIniciais.add(celula77);
		celulasIniciais.add(celula78);
		celulasIniciais.add(celula79);
	//	celulasIniciais.add(celula80);
	//	celulasIniciais.add(celula81);

		Jogo jogo = new Jogo(celulasIniciais);
		jogo.printTabuleiro();
		
		int x = 1;
		int y = 1;
		int valor = 1;
        while(true)
        {
        	System.out.println("Bem vindo ao Sudoku. Para sair pressione -1.");
            System.out.println("Entre com a posição X");
            Scanner entradaX = new Scanner(System.in);
            x = entradaX.nextInt();
            
            if (x == -1){
            	System.out.println("Até a próxima!");
            	break;
            }
            
            System.out.println("Entre com a posição Y");
            Scanner entradaY = new Scanner(System.in);
            y = entradaY.nextInt();
            
            if (y == -1){
            	System.out.println("Até a próxima!");
            	break;
            }
            
            System.out.println("Entre com o Valor");
            Scanner entradaValor = new Scanner(System.in);
            valor = entradaValor.nextInt();
            
            if (valor == -1) {
            	System.out.println("Até a próxima!");
            	break;
            }
            
            int msg = 0;
            if (x > -1 && y > -1 && valor > 0)            	
            	msg = jogo.jogar(new Celula (true, valor, false, x, y));
            
            
            jogo.imprimeMsg(msg);                        
            jogo.printTabuleiro();
            
            if(jogo.finalizouJogo()) {
            	
            	System.out.println("Parabéns!");
            	break;
            }
            
            
        }
		
	}

}
