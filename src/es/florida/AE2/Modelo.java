package es.florida.AE2;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Modelo {

	private Connection connection;

	/**
	 * Realiza la conexion de la base de datos a partir de un fichero XML
	 */
	public void conectar() {
		String url = "", usuario = "", contrasenya = "";

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("bbdd.xml"));
			NodeList nodeList = document.getElementsByTagName("login");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Element element = (Element) node;
				url = String.valueOf(element.getElementsByTagName("url").item(0).getTextContent());
				usuario = String.valueOf(element.getElementsByTagName("usuario").item(0).getTextContent());
				contrasenya = String.valueOf(element.getElementsByTagName("contrasenya").item(0).getTextContent());
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, usuario, contrasenya);

			if (connection.isValid(1))
				JOptionPane.showMessageDialog(new JFrame(), "Conectado", null, JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception error) {
			System.err.println(error);
			JOptionPane.showMessageDialog(new JFrame(), "Conexion fallida", null, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Obtiene el un usuario y contraseña de la base de datos y los coteja con los
	 * datos introducidos por el usuario, encriptandolos a hashMD5
	 * 
	 * @param usuario
	 * @param contrasenya
	 * @return aut: true si coinciden los datos o false si no coinciden.
	 */
	public boolean login(String usuario, String contrasenya) {
		boolean aut = false;
		String passBD = "";

		try {
			String contrasenyaEncriptada = hashMD5(contrasenya);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT pass FROM users WHERE user = " + "'" + usuario + "'");

			while (rs.next()) {
				passBD = rs.getString(1);
				if (passBD.equals(contrasenyaEncriptada)) {
					aut = true;
					break;
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return aut;
	}

	/**
	 * Encripta una contraseña de texto plano a hashMD5
	 * 
	 * @param contrasenya
	 * @return contraseña encriptada
	 */
	private String hashMD5(String contrasenya) throws NoSuchAlgorithmException {
		String encriptacion = "";
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(contrasenya.getBytes());
		encriptacion = new BigInteger(1, md.digest()).toString(16);
		return encriptacion;
	}

	/**
	 * Describe la base de datos con sus tablas y caracteristicas.
	 * 
	 * @return String con la descripcion de las tablas.
	 */
	public String describirBD() {
		String descipcionBD = "";

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SHOW FULL TABLES");
			while (rs.next()) {
				descipcionBD += rs.getString(1) + "\t" + rs.getString(2) + "\n";
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return descipcionBD;
	}

	/**
	 * Describe una tabla con sus caracteristicas.
	 * 
	 * @param nombreTabla
	 * @return String con la descripcion de la tabla.
	 */
	public String describirTabla(String nombreTabla) {
		String descripcionTabla = "";
		String aux = "";
		try {
			PreparedStatement ps = connection.prepareStatement("DESCRIBE " + nombreTabla);
			ResultSet rs = ps.executeQuery();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				descripcionTabla += rs.getMetaData().getColumnName(i) + "\t";

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
					aux += rs.getString(i) + "\t";
				descripcionTabla += "\n" + aux;
				aux = "";
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return descripcionTabla;
	}

	/**
	 * Muestra el contenido de una tabla.
	 * 
	 * @param nombreTabla
	 * @return String con el contenido de la tabla.
	 */
	public String contenidoTabla(String nombreTabla) {
		String contenidoTabla = "";
		String aux = "";
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * from " + nombreTabla);
			ResultSet rs = ps.executeQuery();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				contenidoTabla += rs.getMetaData().getColumnName(i) + "\t";

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
					aux += rs.getString(i) + "\t";
				contenidoTabla += "\n" + aux;
				aux = "";
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return contenidoTabla;
	}

	/**
	 * Gestiona y ejcuta las sentencias SQL realizadas por el ususario sobre la base
	 * de datos
	 * 
	 * @param sentencia
	 * @return String con el resultado de la sentencia SELECT
	 */
	public String gestionSentenciasSQL(String sentencia) {
		String resultadoSentencia = "";

		try {

			sentencia.toLowerCase();
			PreparedStatement ps;
			ResultSet rs;

			if (sentencia.contains("select")) {
				String aux = "";
				ps = connection.prepareStatement(sentencia);
				rs = ps.executeQuery();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
					resultadoSentencia += rs.getMetaData().getColumnName(i) + "\t";

				while (rs.next()) {
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
						aux += rs.getString(i) + "\t";
					resultadoSentencia += "\n" + aux;
					aux = "";
				}

			} else if (sentencia.contains("insert") || sentencia.contains("update") || sentencia.contains("delete")) {
				int confirmacion = 1;
				ps = connection.prepareStatement(sentencia);
				confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea continuar? ", "SQL",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (confirmacion == 0) {
					int resultado = ps.executeUpdate();
					if (resultado == 1)
						JOptionPane.showMessageDialog(new JFrame(), "Se ha modificado la tabla", null,
								JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Error en la sentencia SQL", null,
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Error en la sentencia SQL", null, JOptionPane.ERROR_MESSAGE);
		}
		return resultadoSentencia;
	}

	/**
	 * Realiza la desconexion de la base de datos, despues de una confirmacion.
	 */
	public void desconectar() {
		try {
			int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea continuar? ", "Deconectar",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (confirmacion == 0) {
				connection.close();
				JOptionPane.showMessageDialog(new JFrame(), "Desconectado", null, JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
