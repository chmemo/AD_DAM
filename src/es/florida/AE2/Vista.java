package es.florida.AE2;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import java.awt.Font;

public class Vista {

	private JFrame frame;
	private JTextField tfUsuario;
	private JPasswordField tfContrasenya;
	private JTextField tfTabla;
	private JButton btnConectar;
	private JButton btLogin;
	private JButton btAceptar;
	private JButton btDesconectar;
	private JButton btnDescBD;
	private JButton btnDescTabla;
	private JButton btnContenidoTabla;
	private JTextArea txSQL;
	private JTextArea txResultados;
	
	
	/**
	 * Lanza la interfaz frafica.
	 */
	public Vista() {
		initialize();
	}
	
	/**
	 * Instancia e inicializa los distintos componentes de la interfaz grafica.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1212, 647);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txSQL = new JTextArea();
		txSQL.setFont(new Font("Lucida Bright", Font.PLAIN, 18));
		JScrollPane scrollPane1 = new JScrollPane(txSQL);
		scrollPane1.setBounds(277, 35, 764, 53);
		frame.getContentPane().add(scrollPane1);
		
		txResultados = new JTextArea();
		txResultados.setFont(new Font("Lucida Bright", Font.PLAIN, 18));
		txResultados.setEditable(false);
		JScrollPane scrollPane2 = new JScrollPane(txResultados);
		scrollPane2.setBounds(277, 150, 764, 431);
		frame.getContentPane().add(scrollPane2);
		
		tfUsuario = new JTextField();
		tfUsuario.setBounds(42, 107, 141, 20);
		frame.getContentPane().add(tfUsuario);
		tfUsuario.setColumns(10);
		
		tfContrasenya = new JPasswordField();
		tfContrasenya.setBounds(42, 151, 141, 20);
		frame.getContentPane().add(tfContrasenya);
		tfContrasenya.setColumns(10);
		
		tfTabla = new JTextField();
		tfTabla.setBounds(42, 399, 141, 20);
		frame.getContentPane().add(tfTabla);
		tfTabla.setColumns(10);
		
		btnConectar = new JButton("Conectar");
		btnConectar.setBounds(42, 46, 141, 23);
		frame.getContentPane().add(btnConectar);
		
		btLogin = new JButton("Login");
		btLogin.setBounds(42, 182, 141, 23);
		frame.getContentPane().add(btLogin);
		
		btAceptar = new JButton("Aceptar");
		btAceptar.setBounds(900, 99, 141, 23);
		frame.getContentPane().add(btAceptar);
		
		btnContenidoTabla = new JButton("Contenido Tabla");
		btnContenidoTabla.setBounds(42, 430, 141, 23);
		frame.getContentPane().add(btnContenidoTabla);
		
		btnDescTabla = new JButton("Describir tabla");
		btnDescTabla.setBounds(42, 456, 141, 23);
		frame.getContentPane().add(btnDescTabla);
		
		btnDescBD = new JButton("Describir BD");
		btnDescBD.setBounds(42, 482, 141, 23);
		frame.getContentPane().add(btnDescBD);
		
		btDesconectar = new JButton("Desconectar");
		btDesconectar.setBounds(42, 547, 141, 23);
		frame.getContentPane().add(btDesconectar);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setBounds(42, 95, 68, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Contraseña:");
		lblNewLabel_1.setBounds(42, 138, 89, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("SQL:");
		lblNewLabel_2.setBounds(277, 21, 89, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Resultados:");
		lblNewLabel_3.setBounds(277, 127, 68, 14);
		frame.getContentPane().add(lblNewLabel_3);
							
		JLabel lblNewLabel_4 = new JLabel("Nombre de la tabla:");
		lblNewLabel_4.setBounds(41, 387, 122, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		this.frame.setVisible(true);
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
	 * @return Devuelve el textField para introducir el nombre de una tabla.
	 */
	public JTextField getTfTabla() {
		return tfTabla;
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
	 * @return Devuelve el JButton para confirmar la sentencia SQL.
	 */
	public JButton getBtAceptar() {
		return btAceptar;
	}
	
	/**
	 * @return Devuelve el JButton para desconectarse.
	 */
	public JButton getBtDesconectar() {
		return btDesconectar;
	}
	
	/**
	 * @return Devuelve el JButton para describir la base de datos.
	 */
	public JButton getBtnDescBD() {
		return btnDescBD;
	}
	
	/**
	 * @return Devuelve el JButton para describir una tabla de la base de datos.
	 */
	public JButton getBtnDescTabla() {
		return btnDescTabla;
	}
	
	/**
	 * @return Devuelve el JButton para mostrar el contenido de una tabla de la base de datos.
	 */
	public JButton getBtnContenidoTabla() {
		return btnContenidoTabla;
	}
	
	/**
	 * @return Devuelve el JTextArea donde se escribe la consulta SQL.
	 */
	public JTextArea getTxSQL() {
		return txSQL;
	}
	
	/**
	 * @return Devuelve el JTextArea donde se lee el resultado de las consultas SQL y las acciones del los demas botones.
	 */
	public JTextArea getTxResultados
	() {
		return txResultados;
	}
}
