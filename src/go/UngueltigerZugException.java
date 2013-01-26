package go;


public class UngueltigerZugException extends Exception {
	private RegelBruch regelBruch;
	
	public UngueltigerZugException (String arg0, RegelBruch regelBruch)
	{
		super(arg0);
		this.regelBruch = regelBruch;
	}
	
	public RegelBruch gibRegelBruch(){
		return regelBruch;
	}
}