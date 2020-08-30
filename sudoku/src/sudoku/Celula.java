package sudoku;

public class Celula {
	private int grupo;
	private boolean permiteAlterar;
	private int valor;
	private boolean estaVazio;
	private int posicaoX;
	private int posicaoY;
	
	public Celula() {
		this.permiteAlterar = true;
		this.valor = 0;
		this.estaVazio = true;
		this.posicaoX = -1;
		this.posicaoY = -1;			
	}
	
	public Celula(boolean permiteAlterar, int valor, boolean estaVazio, int posicaoX, int posicaoY) {
		this.permiteAlterar = permiteAlterar;
		this.valor = valor;
		this.estaVazio = estaVazio;
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;			
	}
	
	
	public int getPosicaoX() {
		return posicaoX;
	}
	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}
	public int getPosicaoY() {
		return posicaoY;
	}
	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}
	
	public int getGrupo() {
		return grupo;
	}
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
	public boolean isPermiteAlterar() {
		return permiteAlterar;
	}
	public void setPermiteAlterar(boolean permiteAlterar) {
		this.permiteAlterar = permiteAlterar;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public boolean isEstaVazio() {
		return estaVazio;
	}
	public void setEstaVazio(boolean estaVazio) {
		this.estaVazio = estaVazio;
	}
	
	@Override
	public String toString() {
		String retorno = "";
		if (this.valor == 0) {
			retorno = " ";			
		}
		else {
		//	if (this.permiteAlterar) {
			retorno = String.valueOf(this.valor);
		//	}
		//	else {
		//		retorno = String.valueOf(this.valor+"*");
		//	}
			
		}
		return retorno;		
	}
}
