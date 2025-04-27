package gioco.belardi.bellucci;

public class Personaggio {

	private double coordinataX;
	private double coordinataY;
	
	public Personaggio (double coordinataX, double coordinataY) {
		super();
		this.coordinataX = coordinataX;
		this.coordinataY = coordinataY;
	}
	
	public double movimentoSu () {
		return getCoordinataY()+16;
	}
	
	public double movimentoGiu () {
		return getCoordinataY()-16;
	}
	
	public double movimentoDestra () {
		return getCoordinataX()+16;
	}
	
	public double movimentoSinistra () {
		return getCoordinataX()-16;
	}

	public double getCoordinataX() {
		return coordinataX;
	}

	public void setCoordinataX(double coordinataX) {
		this.coordinataX = coordinataX;
	}

	public double getCoordinataY() {
		return coordinataY;
	}

	public void setCoordinataY(double coordinataY) {
		this.coordinataY = coordinataY;
	}
	
	
}
