package es.florida.ae3;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import java.awt.Font;

public class Vista {

	private JFrame framePrincipal;
	private JTextField tfUsuario;
	private JPasswordField tfContrasenya;
	private JButton btnConectar;
	private JButton btLogin;
	private JButton btnDesconectar;
	private JButton btnMostrarDocumento;
	private JButton btnResumenColeccion;
	private JButton btnCrearDocumento;
	private JButton btnEditarDocumento;
	private JButton btnEliminarDocumento;
	private JTextArea txResultados;
	private JButton btnConsultasBd;

	/**
	 * Lanza la interfaz grafica.
	 */
	public Vista() {
		initialize();
	}

	/**
	 * Instancia e inicializa los distintos componentes de la interfaz grafica.
	 */
	private void initialize() {
		framePrincipal = new JFrame();
		framePrincipal.setBounds(100, 100, 1212, 647);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePrincipal.getContentPane().setLayout(null);

		txResultados = new JTextArea();
		txResultados.setFont(new Font("Lucida Bright", Font.PLAIN, 18));
		txResultados.setEditable(false);
		JScrollPane scrollPane2 = new JScrollPane(txResultados);
		scrollPane2.setBounds(277, 26, 764, 555);
		framePrincipal.getContentPane().add(scrollPane2);

		tfUsuario = new JTextField();
		tfUsuario.setBounds(42, 107, 141, 20);
		framePrincipal.getContentPane().add(tfUsuario);
		tfUsuario.setColumns(10);

		tfContrasenya = new JPasswordField();
		tfContrasenya.setBounds(42, 151, 141, 20);
		framePrincipal.getContentPane().add(tfContrasenya);
		tfContrasenya.setColumns(10);

		btnConectar = new JButton("Conectar");
		btnConectar.setBounds(42, 46, 141, 23);
		framePrincipal.getContentPane().add(btnConectar);

		btLogin = new JButton("Identificar");
		btLogin.setBounds(42, 182, 141, 23);
		framePrincipal.getContentPane().add(btLogin);

		btnResumenColeccion = new JButton("Resumen Biblioteca");
		btnResumenColeccion.setBounds(42, 273, 156, 23);
		framePrincipal.getContentPane().add(btnResumenColeccion);

		btnEliminarDocumento = new JButton("Eliminar Libro");
		btnEliminarDocumento.setBounds(42, 373, 156, 23);
		framePrincipal.getContentPane().add(btnEliminarDocumento);

		btnMostrarDocumento = new JButton("Mostrar Libro");
		btnMostrarDocumento.setBounds(42, 298, 156, 23);
		framePrincipal.getContentPane().add(btnMostrarDocumento);

		btnCrearDocumento = new JButton("Añadir Libro");
		btnCrearDocumento.setBounds(42, 323, 156, 23);
		framePrincipal.getContentPane().add(btnCrearDocumento);

		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setBounds(42, 547, 141, 23);
		framePrincipal.getContentPane().add(btnDesconectar);

		btnEditarDocumento = new JButton("Actualizar Libro");
		btnEditarDocumento.setBounds(42, 348, 156, 23);
		framePrincipal.getContentPane().add(btnEditarDocumento);

		btnConsultasBd = new JButton("Consultar BD");
		btnConsultasBd.setBounds(42, 398, 156, 23);
		framePrincipal.getContentPane().add(btnConsultasBd);

		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setBounds(42, 95, 68, 14);
		framePrincipal.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Contraseña:");
		lblNewLabel_1.setBounds(42, 138, 89, 14);
		framePrincipal.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("Resultados:");
		lblNewLabel_3.setBounds(277, 11, 68, 14);
		framePrincipal.getContentPane().add(lblNewLabel_3);

		this.framePrincipal.setVisible(true);
	}

	/**
	 * @return Devuelve el textField para introducir el nombre de usuario.
	 */
	public JTextField getTfUsuario() {
		return tfUsuario;
	}

	/**
	 * @return Devuelve el textPassword para introducir la contraseña.
	 */
	public JTextField getTfContrasenya() {
		return tfContrasenya;
	}

	/**
	 * @return Devuelve el JButton para realizar la conexion.
	 */
	public JButton getBtnConectar() {
		return btnConectar;
	}

	/**
	 * @return Devuelve el JButton para loguearse.
	 */
	public JButton getBtLogin() {
		return btLogin;
	}

	/**
	 * @return Devuelve el JButton para mostrar el contenido de la coleccion.
	 */
	public JButton getBtnResumenColeccion() {
		return btnResumenColeccion;
	}

	/**
	 * @return Devuelve el JButton para mostrar el contenido de un documento. 
	 */
	public JButton getBtnMostrarDocumento() {
		return btnMostrarDocumento;
	}

	/**
	 * @return Devuelve el JButton para crear un documento nuevo. 
	 */
	public JButton getBtnCrearDocumento() {
		return btnCrearDocumento;
	}

	/**
	 * @return Devuelve el JButton para eliminar un documento nuevo. 
	 */
	public JButton getBtnEliminarDocumento() {
		return btnEliminarDocumento;
	}

	/**
	 * @return Devuelve el JButton para actualizar un documento. 
	 */
	public JButton getBtnEditarDocumento() {
		return btnEditarDocumento;
	}

	/**
	 * @return Devuelve el JButton para realizar consultas en la base de datos. 
	 */
	public JButton getBtnConsultas() {
		return btnConsultasBd;
	}

	/**
	 * @return Devuelve el JButton para desconectarse de la base de datos.
	 */
	public JButton getBtDesconectar() {
		return btnDesconectar;
	}

	/**
	 * @return Devuelve el JTextArea donde se muestra el resultado de las distintas
	 *         funcionalidades.
	 */
	public JTextArea getTxResultados() {
		return txResultados;
	}

}
