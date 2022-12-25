package es.florida.ae3;

public class Libro {
	private String id, titulo, autor, anyo_nac, anyo_pub, editorial, paginas, portada;

	public Libro(String id, String titulo, String autor, String anyo_nac, String anyo_pub, String editorial,
			String paginas, String portada) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.anyo_nac = anyo_nac;
		this.anyo_pub = anyo_pub;
		this.editorial = editorial;
		this.paginas = paginas;
		this.portada = portada;
	}

	public Libro(String titulo, String autor, String anyo_nac, String anyo_pub, String editorial, String paginas, String portada) {
		this.titulo = titulo;
		this.autor = autor;
		this.anyo_nac = anyo_nac;
		this.anyo_pub = anyo_pub;
		this.editorial = editorial;
		this.paginas = paginas;
		this.portada = portada;
	}

	public Libro() {
	}

	public String getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public String getAnyo_nac() {
		return anyo_nac;
	}

	public String getAnyo_pub() {
		return anyo_pub;
	}

	public String getEditorial() {
		return editorial;
	}

	public String getPaginas() {
		return paginas;
	}

	public String getPortada() {
		return portada;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setAnyo_nac(String anyo_nac) {
		this.anyo_nac = anyo_nac;
	}

	public void setAnyo_pub(String anyo_pub) {
		this.anyo_pub = anyo_pub;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public void setPaginas(String paginas) {
		this.paginas = paginas;
	}
	
	public void setPortada(String portada) {
		this.portada = portada;
	}
	
	@Override
	public String toString() {
		return "-ID: " + id + "\n" + "-Titulo: " + titulo + "\n" + "-Autor: " + autor + "\n" + "-Año de nacimiento: "
				+ anyo_nac + "\n" + "-Año de publicacion: " + anyo_pub + "\n" + "-Editorial: " + editorial + "\n"
				+ "-Paginas: " + paginas;
	}
}
