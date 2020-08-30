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
	};
	
	private boolean podeSubstituir (Celula celulaJogada) {
		boolean retorno = false;
		if(this.tabuleiro[celulaJogada.getPosicaoX()][celulaJogada.getPosicaoY()].isPermiteAlterar()) {
			retorno = true;			
		}
		return retorno;
	};
	
	private boolean podeJogar(Celula celulaJogada) {
		return verificaLinha(celulaJogada.getPosicaoX(), celulaJogada.getValor()) && 
				verificaColuna(celulaJogada.getPosicaoY(), celulaJogada.getValor()) && 
				verificaGrupo(pegaGrupo(celulaJogada), celulaJogada.getValor());
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Celula celula1 = new Celula(false, 7, false, 0, 0);
		Celula celula2 = new Celula(false, 1, false, 1, 0);
		Celula celula3 = new Celula(false, 4, false, 2, 0);
		Celula celula4 = new Celula(false, 3, false, 5, 3);
		Celula celula5 = new Celula(false, 5, false, 5, 4);
		Celula celula6 = new Celula(false, 9, false, 5, 5);
		Celula celula7 = new Celula(false, 1, false, 6, 6);
		Celula celula8 = new Celula(false, 3, false, 6, 7);
		Celula celula9 = new Celula(false, 6, false, 6, 8);
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
		Jogo jogo = new Jogo(celulasIniciais);
		jogo.printTabuleiro();
		
		int x = 1;
		int y = 1;
		int valor = 1;
        while(x > -1 && y > -1 && valor > -1)
        {
            System.out.println("Entre com a posição X");
            Scanner entradaX = new Scanner(System.in);
            x = entradaX.nextInt();
            
            System.out.println("Entre com a posição Y");
            Scanner entradaY = new Scanner(System.in);
            y = entradaY.nextInt();
            
            System.out.println("Entre com o Valor");
            Scanner entradaValor = new Scanner(System.in);
            valor = entradaValor.nextInt();
            
            
            if (x > -1 && y > -1 && valor > -1)            	
            	jogo.jogar(new Celula (true, valor, false, x, y));
            
            jogo.printTabuleiro();
        }
		
	}

}
