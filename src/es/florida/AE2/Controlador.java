package es.florida.AE2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	private ActionListener aLConectar, aLLogin, aLAceptar, aLDesconectar, aLContTabla, aLDescBd, aLDescTablas;
	private boolean login;

	/**
	 * inicializa los objetos Modelo y Vista para acceder a sus propiedades. llama
	 * al metodo control().
	 * 
	 * @param modelo
	 * @param vista
	 */
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		control();
	}

	/**
	 * Llama a los metodos que Establecen, controlan y ejecutan los eventos de los
	 * ActionListener.
	 */
	public void control() {
		conexionBD();
		administradorBD();
		desconexionBD();
	}

	/**
	 * Realiza la conexion a la base de datos a partir del boton Conectar
	 */
	private void conexionBD() {
		aLConectar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelo.conectar();
			}
		};
		vista.getBtnConectar().addActionListener(aLConectar);
	}

	/**
	 * Realiza la autentificacion del usuario a partir del boton Login y los datos
	 * introducidos por el usuario. Si el usuario esta autorizado, permite usar el
	 * resto de funcionalidades del programa.
	 */
	private void administradorBD() {
		aLLogin = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = vista.getTfUsuario().getText();
				String contrasenya = vista.getTfContrasenya().getText();
				login = modelo.login(usuario, contrasenya);

				if (login) {
					JOptionPane.showMessageDialog(new JFrame(), "Acceso concedido", null,
							JOptionPane.INFORMATION_MESSAGE);
					mostrarDescripcionBD();
					mostrarDescripcionTabla();
					mostrarContenidoTabla();
					mostrarResultadoSQL();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Usuario y/o contraseña incorrectos", null,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		vista.getBtLogin().addActionListener(aLLogin);
	}

	/**
	 * Muestra el contenido de la base de datos en la interfaz a apartir del boton
	 * describirBD
	 */
	private void mostrarDescripcionBD() {
		aLDescBd = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tablasBD = modelo.describirBD();
				mostrarTextArea("Tablas de la base de datos: " + "\n" + tablasBD);
			}
		};
		vista.getBtnDescBD().addActionListener(aLDescBd);
	}

	/**
	 * Muestra el contenido de una tabla en la interfaz a partir del boton Contenido
	 * Tabla con los datos del textField
	 */
	private void mostrarContenidoTabla() {
		aLContTabla = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreTabla = vista.getTfTabla().getText();
				String columas = modelo.contenidoTabla(nombreTabla);
				mostrarTextArea("Columnas de la tabla " + nombreTabla + ":" + "\n" + columas);
			}
		};
		vista.getBtnContenidoTabla().addActionListener(aLContTabla);
	}

	/**
	 * Muestra la descripcion de una tabla en la interfaz a partir del boton
	 * Describir tabla con los datos del textField
	 */
	private void mostrarDescripcionTabla() {
		aLDescTablas = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreTabla = vista.getTfTabla().getText();
				String descripcionTabla = modelo.describirTabla(nombreTabla);
				mostrarTextArea("Descripcion de la tabla " + nombreTabla + ":" + "\n" + descripcionTabla);
			}
		};
		vista.getBtnDescTabla().addActionListener(aLDescTablas);
	}

	/**
	 * Muestra por la interfaz los resultados de la consulta SQL realizada por el
	 * usuario.
	 */
	private void mostrarResultadoSQL() {
		aLAceptar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sentenciaSQL = vista.getTxSQL().getText();
				String resultadoSentencia = modelo.gestionSentenciasSQL(sentenciaSQL);
				mostrarTextArea(resultadoSentencia);
			}
		};
		vista.getBtAceptar().addActionListener(aLAceptar);
	}

	/**
	 * Realiza la desconexion de la base de datos a partir del boton desconectar.
	 */

	private void desconexionBD() {
		aLDesconectar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelo.desconectar();
			}
		};
		vista.getBtDesconectar().addActionListener(aLDesconectar);
	}

	/**
	 * Metodo utilizado para mostrar por el TextField Resultado un contenido
	 * especificado restablece el TextField en cada llamada.
	 * 
	 * @param contenido a mostrar
	 */
	
	private void mostrarTextArea(String contenido) {
		vista.getTxResultados().setText("");
		vista.getTxResultados().append(contenido);
	}
}
