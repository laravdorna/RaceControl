package control;

import java.util.ArrayList;

public class BaseControl {
	private ArrayList<?> items = new ArrayList<>();

	public BaseControl() {
		super();
	}
	
	public int getSiguienteNumero() {
		return ultimoId() + 1;
	}
	
	public int ultimoId() {
		return 0;

	}
	
	

}
