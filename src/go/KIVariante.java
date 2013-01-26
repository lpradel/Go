package go;

public class KIVariante {
	private Variante var;
	
	public KIVariante(Variante var){
		this.var = var;
	}
	
	public RegelBruch istZulaessig(Spielzug s){
		return this.var.istZulaessig(s);
	}
}
