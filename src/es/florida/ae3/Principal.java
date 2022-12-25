package es.florida.ae3;

public class Principal {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Modelo modelo = new Modelo();
		Vista vista = new Vista();
		Controlador control = new Controlador(modelo, vista);
	}
}

