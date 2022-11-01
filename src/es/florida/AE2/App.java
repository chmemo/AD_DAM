package es.florida.AE2;

public class App {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Modelo modelo = new Modelo();
		Vista vista = new Vista();
		Controlador control = new Controlador(modelo, vista);
	}

}
