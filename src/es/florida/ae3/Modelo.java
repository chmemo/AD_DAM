package es.florida.ae3;

import static com.mongodb.client.model.Filters.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.bson.Document;
import org.json.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.ByteArrayInputStream;
import org.apache.commons.codec.binary.Base64;
import java.util.regex.Pattern;

public class Modelo {
	private static MongoClient mongoClient;
	private static MongoDatabase dataBase;
	private static MongoCollection<Document> coleccion;
	private static MongoCursor<Document> cursor;
	private static ArrayList<Libro> listaLibros;
	private static ArrayList<String> coleccionRespuestaBD;
	private static ImageIcon imagenIcono;
	private static boolean comprobarID = false;

	/**
	 * Convierte una imagen en String base 64.
	 * 
	 * @param Ruta de la imagen.
	 * @return Imagen codificada a base 64.
	 */
	private static String imgToBase64(String ruta) {
		String imagenCodificada = "";
		try {
			File ficheroImagen = new File(ruta);
			byte[] contenidoFichero = Files.readAllBytes(ficheroImagen.toPath());
			imagenCodificada = Base64.encodeBase64String(contenidoFichero);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imagenCodificada;
	}

	/**
	 * Convierte un String en base 64 a una imagen .
	 * 
	 * @param String en base 64.
	 * @return Un BufferedImage.
	 */
	private static BufferedImage base64ToImg(String imagenCodificada) {

		BufferedImage imagenDecodificada = null;
		try {
			byte[] contenidoImagenCodificada = Base64.decodeBase64(imagenCodificada);
			imagenDecodificada = ImageIO.read(new ByteArrayInputStream(contenidoImagenCodificada));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imagenDecodificada;
	}

	/**
	 * Proporciona una una imagen a partir de un BufferedImage.
	 * 
	 * @param BufferedImage.
	 */
	public void obtenerImagen(BufferedImage imagenDecodificada) {
		Image imagen = imagenDecodificada.getScaledInstance(500, 400, Image.SCALE_DEFAULT);
		imagenIcono = new ImageIcon(imagen);
	}

	/**
	 * Codifica la contraseña en texto plano en SHA256
	 * 
	 * @param Contraseña en texto plano.
	 * @return Contraseña encriptada.
	 */
	public static String codificarSHA256(String password) {

		String encriptacion = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			StringBuffer sb = new StringBuffer();

			for (byte b : hash) {
				sb.append(String.format("%02x", b));
				encriptacion = sb.toString();
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

		return encriptacion;
	}

	/**
	 * Realiza lo conexion a una base de datos en MongoDB, Leyendo los parametros de
	 * conexion desde un fichero JSON.
	 * 
	 * @return True si la conexion se ha realizado con exito.
	 */
	public boolean conectar() {

		boolean conectado = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader("conexion.json"));
			String linea, lineasJson = "", coleccionBD, host, bd;
			int puerto;

			while ((linea = br.readLine()) != null)
				lineasJson += linea;
			br.close();

			JSONObject jsO = new JSONObject(lineasJson);
			host = jsO.getString("host");
			puerto = jsO.getInt("port");
			bd = jsO.getString("dataBase");
			coleccionBD = jsO.getString("collection");

			mongoClient = new MongoClient(host, puerto);
			dataBase = mongoClient.getDatabase(bd);
			coleccion = dataBase.getCollection(coleccionBD);
			conectado = true;
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return conectado;
	}

	/**
	 * Obtiene un usuario y contraseña de la base de datos y los coteja con los
	 * datos introducidos por el usuario, encriptandolos a hash SHA-256
	 * 
	 * @param usuario
	 * @param contrasenya
	 * @return aut: true si coinciden los datos o false si no coinciden.
	 */
	public boolean login(String usuario, String contrasenya) {
		boolean aut = false;
		String passBD = "", encriptacion = "";
		cursor = coleccion.find(eq("user", usuario)).iterator();
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			passBD = obj.getString("pass");
		}

		encriptacion = codificarSHA256(contrasenya);

		if (passBD.equals(encriptacion)) {
			obtenerListaLibros();
			aut = true;
		}

		return aut;
	}

	/**
	 * Obtiene los documentos existentes de la base de datos y los convierte en
	 * objetos de tipo Libro, se añaden los objetos Libro a una lista de Libros.
	 * Este metodo se utilizara constantemente, para obtener la lista actualizada de
	 * Libros(Documentos de la coleccion y lista Objetos Libro)
	 */
	public void obtenerListaLibros() {

		listaLibros = new ArrayList<Libro>();
		coleccion = dataBase.getCollection("Books");
		cursor = coleccion.find().iterator();
		while (cursor.hasNext()) {
			Libro libro = new Libro();
			JSONObject obj = new JSONObject(cursor.next().toJson());
			libro.setId(String.valueOf(obj.getInt("Id")));
			libro.setTitulo(obj.getString("Titulo"));
			libro.setAutor(obj.getString("Autor"));

			try {
				if (obj.getInt("Anyo_nacimiento") == 0) {
					libro.setAnyo_nac("Desconocido");
				} else {
					libro.setAnyo_nac(String.valueOf(obj.getInt("Anyo_nacimiento")));
				}
			} catch (Exception e) {
				libro.setAnyo_nac("Desconocido");
			}

			if (obj.getInt("Anyo_publicacion") == 0) {
				libro.setAnyo_pub("Desconocido");
			} else {
				libro.setAnyo_pub(String.valueOf(obj.getInt("Anyo_publicacion")));
			}

			libro.setEditorial(obj.getString("Editorial"));

			if (obj.getInt("Numero_paginas") == 0) {
				libro.setPaginas("Desconocido");
			} else {
				libro.setPaginas(String.valueOf(obj.getInt("Numero_paginas")));
			}
			libro.setPortada(obj.getString("Thumbnail"));
			listaLibros.add(libro);
		}
	}

	/**
	 * Obtiene la lista actualizada de libros.
	 * Devuelve un String con la informacion de un Libro, indicado por el usuario a
	 * travez de su ID. Si el libro contiene una portada, proporciona la imagen de
	 * dicha portada.
	 * 
	 * @param Id del libro.
	 * @return Info del libro.
	 */
	public String infoDetallada(String id) {
		String info = "";
		obtenerListaLibros();
		for (Libro libro : listaLibros) {
			if (id.equals(libro.getId())) {
				info = libro.toString();
				if (!libro.getPortada().equals("Sin portada")) {
					BufferedImage bI = base64ToImg(libro.getPortada());
					obtenerImagen(bI);
				}
				comprobarID = true;
				break;
			}
		}
		return info;
	}

	/**
	 * Crea un documento nuevo, controlando los posibles datos erroneos que el
	 * usuario pueda introducir. Obtiene la lista actualizada de libros.
	 * 
	 * @param Id.
	 * @param Titulo.
	 * @param Autor.
	 * @param Año        de nacimiento.
	 * @param Año        de publicación.
	 * @param Editorial.
	 * @param Paginas.
	 * @param Ruta       con la imagen de portada.
	 */
	public void anyadirLibro(String titulo, String autor, String anyoNac, String anyoPub, String editorial,
			String paginas, String rutaImagen) {

		obtenerListaLibros();
		int idInt = 0;
		for (Libro libro : listaLibros) {
			idInt = Integer.parseInt(libro.getId()) + 1;
		}

		int anyoNacInt = 0;
		int anyoPubInt = 0;
		int paginasInt = 0;
		String imagenBase64 = "";
		Pattern esNumero = Pattern.compile("[0-9]+");

		if (titulo == null || titulo == "")
			titulo = "Desconocido";
		if (autor == null || autor == "")
			autor = "Desconocido";
		if (anyoNac != null && anyoNac != "" && esNumero.matcher(anyoNac).find())
			anyoNacInt = Integer.parseInt(anyoNac);
		if (anyoPub != null && anyoPub != "" && esNumero.matcher(anyoPub).find())
			anyoPubInt = Integer.parseInt(anyoPub);
		if (editorial == null || editorial == "")
			editorial = "Desconocido";
		if (paginas != null && paginas != "" && esNumero.matcher(paginas).find())
			paginasInt = Integer.parseInt(paginas);

		try {
			imagenBase64 = imgToBase64(rutaImagen);
		} catch (Exception e) {
			imagenBase64 = "Sin portada";
		}

		Document doc = new Document();
		doc.append("Id", idInt);
		doc.append("Titulo", titulo);
		doc.append("Autor", autor);
		doc.append("Anyo_nacimiento", anyoNacInt);
		doc.append("Anyo_publicacion", anyoPubInt);
		doc.append("Editorial", editorial);
		doc.append("Numero_paginas", paginasInt);
		doc.append("Thumbnail", imagenBase64);
		coleccion.insertOne(doc);
		obtenerListaLibros();
	}

	/**
	 * Actualiza un documento existente controlando los posibles datos erroneos que
	 * el usuario pueda introducir. Obtiene la lista actualizada de libros.
	 * 
	 * @param Id.
	 * @param Titulo.
	 * @param Autor.
	 * @param Año        de nacimiento.
	 * @param Año        de publicación.
	 * @param Editorial.
	 * @param Paginas.
	 * @param Ruta       con la imagen de portada.
	 */
	public void editarLibro(String id, String titulo, String autor, String anyoNac, String anyoPub, String editorial,
			String paginas, String rutaImagen) {

		obtenerListaLibros();
		int anyoNacInt = 0;
		int anyoPubInt = 0;
		int paginasInt = 0;
		String imagenBase64 = "Sin portada";
		Pattern esNumero = Pattern.compile("[0-9]+");

		for (Libro libro : listaLibros) {
			System.out.println("MODELO: ID(OBJETO LIBRO): " + libro.getId());
			System.out.println("MODELO: ID(USUARIO): " + id);

			if (id.equals(libro.getId())) {

				System.out.println("MODELO: 01");
				if (titulo == null || titulo.equals(""))
					titulo = libro.getTitulo();

				if (autor == null || autor.equals(""))
					autor = libro.getAutor();

				if (anyoNac == null || esNumero.matcher(anyoNac).find() == false || anyoNac.equals("")) {
					if (libro.getAnyo_nac() == "Desconocido")
						libro.setAnyo_nac("0");
					anyoNacInt = Integer.parseInt(libro.getAnyo_nac());
					libro.setAnyo_nac("Desconocido");
				} else {
					anyoNacInt = Integer.parseInt(anyoNac);
				}

				if (anyoPub == null || esNumero.matcher(anyoPub).find() == false || anyoPub.equals("")) {
					if (libro.getAnyo_pub() == "Desconocido")
						libro.setAnyo_pub("0");
					anyoPubInt = Integer.parseInt(libro.getAnyo_pub());
					libro.setAnyo_pub("Desconocido");
				} else {
					anyoPubInt = Integer.parseInt(anyoPub);
				}

				if (editorial == null || editorial.equals(""))
					editorial = libro.getEditorial();

				if (paginas == null || esNumero.matcher(paginas).find() || paginas.equals("")) {
					if (libro.getPaginas() == "Desconocido")
						libro.setPaginas("0");
					paginasInt = Integer.parseInt(libro.getPaginas());
					libro.setPaginas("Desconocido");
				} else {
					paginasInt = Integer.parseInt(paginas);
				}

				try {
					imagenBase64 = imgToBase64(rutaImagen);
				} catch (Exception e) {
					if (rutaImagen == null || !libro.getPortada().equals("Sin portada")) {
						libro.setPortada("Sin portada");
						imagenBase64 = libro.getPortada();
					}
				}
				Document doc = new Document();
				doc.append("Titulo", titulo);
				doc.append("Autor", autor);
				doc.append("Anyo_nacimiento", anyoNacInt);
				doc.append("Anyo_publicacion", anyoPubInt);
				doc.append("Editorial", editorial);
				doc.append("Numero_paginas", paginasInt);
				doc.append("Thumbnail", imagenBase64);
				coleccion.updateOne(eq("Id", Integer.parseInt(id)), new Document("$set", doc));
				obtenerListaLibros();
				comprobarID = true;
				break;
			}
		}
	}

	/**
	 * Elimina un documento indicado por el usuario. Obtiene la lista actualizada de
	 * libros.
	 * 
	 * @param Id.
	 */
	public void eliminarLibro(String id) {
		obtenerListaLibros();
		for (Libro libro : listaLibros) {
			if (id.equals(libro.getId())) {
				int idInt = Integer.parseInt(id);
				coleccion.deleteOne(eq("Id", idInt));
				comprobarID = true;
				obtenerListaLibros();
				break;
			}
		}
	}

	/**
	 * Permite al usuario hacer consultas directamente contra la base de datos. Los
	 * filtros de consulta son de tipo (igual, mayor que y menor que)
	 * 
	 * @param Filtro de busqueda.
	 * @param Campo  de busqueda.
	 * @param Valor  de busqueda.
	 * @return Devuelve un ArrayList con el resultado de la busqueda.
	 */
	public ArrayList<String> respuestaConsulta(String filtro, String campo, String valor) {

		Pattern esNumero = Pattern.compile("[0-9]+");
		switch (filtro) {
		case "Igual":
			if (esNumero.matcher(valor).find()) {
				cursor = coleccion.find(eq(campo, Integer.parseInt(valor))).iterator();
			} else {
				cursor = coleccion.find(eq(campo, valor)).iterator();
			}
			leerBD();
			break;
		case "Mayor o Igual":
			if (esNumero.matcher(valor).find()) {
				cursor = coleccion.find(gte(campo, Integer.parseInt(valor))).iterator();
			} else {
				cursor = coleccion.find(gte(campo, valor)).iterator();
			}
			leerBD();
			break;
		case "Menor o Igual":
			if (esNumero.matcher(valor).find()) {
				cursor = coleccion.find(lte(campo, Integer.parseInt(valor))).iterator();
			} else {
				cursor = coleccion.find(lte(campo, valor)).iterator();
			}
			leerBD();
			break;
		}
		return coleccionRespuestaBD;
	}

	/**
	 * Lee la coleccion y añade los resultados a un ArrayList.
	 */
	private static void leerBD() {
		coleccionRespuestaBD = new ArrayList<String>();
		coleccionRespuestaBD.add("Documentos de la coleccion: " + "\n" + "\n");
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			coleccionRespuestaBD.add("-Id: " + String.valueOf(obj.getNumber("Id")) + "\n");
			coleccionRespuestaBD.add("-Titulo: " + obj.getString("Titulo") + "\n");
			coleccionRespuestaBD.add("-Autor: " + obj.getString("Autor") + "\n");
			try {
				coleccionRespuestaBD
						.add("-Anyo_nacimiento: " + String.valueOf(obj.getNumber("Anyo_nacimiento")) + "\n");
			} catch (Exception e) {

			}
			coleccionRespuestaBD.add("-Anyo_publicacion: " + String.valueOf(obj.getNumber("Anyo_publicacion")) + "\n");
			coleccionRespuestaBD.add("-Editorial: " + obj.getString("Editorial") + "\n");
			coleccionRespuestaBD.add("-Numero_paginas: " + String.valueOf(obj.getNumber("Numero_paginas")) + "\n");
			coleccionRespuestaBD.add("-Thumbnail: " + obj.getString("Thumbnail") + "\n");
			coleccionRespuestaBD.add("\n");
		}
	}

	/**
	 * Finaliza la conexión con la base de datos.
	 **/
	public void desconectar() {
		mongoClient.close();
	}

	/**
	 * @return lista actualizada de libros.
	 **/
	public ArrayList<Libro> getListaLibros() {
		return listaLibros;
	}

	/**
	 * @return Imagen de la portada.
	 **/
	public ImageIcon getImagenIcono() {
		return imagenIcono;
	}

	/**
	 * Una variable booleana que se establece a True cuando en los metodos que pidan
	 * un Id, se ejecute todo correctamente. Esta variable se utilizara en la clase
	 * controlador, para enviar un mensaje de "ID incorrecto", si el Id
	 * proporcionado por el usuario no existe o no es correcto.
	 * 
	 * @return comprobarID: True si es correcto, False si no lo es.
	 **/
	public boolean getComprobarID() {
		return comprobarID;
	}

	/**
	 * Permite Cambiar el valor de la variable desde las clases que la llamen.
	 * 
	 * @param true o false.
	 */
	public void setComprobarID(boolean bool) {
		comprobarID = bool;
	}
}
