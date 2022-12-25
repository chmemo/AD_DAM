package es.florida.ae3;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	private ActionListener aLConectar, aLLogin, aLDesconectar, aLResumen, aLMostrar, aLCrear, aLActualizar, aLEliminar,
			aLConsultas;
	private boolean conectado, login;

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
		desconectarBd();
	}

	/**
	 * Realiza la conexion a la base de datos a partir del boton Conectar.
	 */
	private void conexionBD() {
		aLConectar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectado = modelo.conectar();

				if (conectado) {
					JOptionPane.showMessageDialog(null, "Conexion correcta");
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Conexion fallida", null, JOptionPane.ERROR_MESSAGE);
				}
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
				if (conectado) {
					String usuario = vista.getTfUsuario().getText();
					String contrasenya = vista.getTfContrasenya().getText();
					login = modelo.login(usuario, contrasenya);

					if (login) {
						JOptionPane.showMessageDialog(new JFrame(), "Acceso concedido", null,
								JOptionPane.INFORMATION_MESSAGE);
						resumenColeccion();
						mostrarDocumento();
						crearDocumento();
						editarDocumento();
						eliminarDocumento();
						consultarBD();
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "Usuario y/o contraseña incorrectos", null,
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Debe conectarse a la base de datos.", null,
							JOptionPane.ERROR_MESSAGE);
				}

			}
		};
		vista.getBtLogin().addActionListener(aLLogin);
	}

	/**
	 * Muestra en el textArea la cantidad de libros en la biblioteca, junto con sus
	 * Id´s y titulos
	 */
	private void resumenColeccion() {
		aLResumen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Libro> coleccion = modelo.getListaLibros();
				int numeroDocumentos = coleccion.size();
				vista.getTxResultados().setText("");
				vista.getTxResultados()
						.append("Numero de libros en la biblioteca: " + String.valueOf(numeroDocumentos) + "\n");
				for (Libro libro : coleccion) {
					vista.getTxResultados().append("(ID):" + libro.getId() + "   " + libro.getTitulo() + "\n");
				}
			}
		};
		vista.getBtnResumenColeccion().addActionListener(aLResumen);

	}

	/**
	 * Muestra en el textArea la informacion completa de un libro especificado.
	 * Lanza una ventana con la imagen de la portada.
	 */
	private void mostrarDocumento() {
		aLMostrar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = JOptionPane.showInputDialog("¿Indique el ID del libro?");
				String info = modelo.infoDetallada(id);

				if (modelo.getComprobarID()) {
					vista.getTxResultados().setText("");
					vista.getTxResultados().append(info);
					JFrame ventanaImagen = new JFrame("Portada");
					ventanaImagen.setLayout(new FlowLayout());
					ventanaImagen.setSize(500, 500);
					JLabel jLabel = new JLabel();
					jLabel.setIcon(modelo.getImagenIcono());
					ventanaImagen.add(jLabel);
					ventanaImagen.setVisible(true);
					modelo.setComprobarID(false);
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "ID incorrecto", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		vista.getBtnMostrarDocumento().addActionListener(aLMostrar);
	}

	/**
	 * Permite al usuario añadir un nuevo libro a la biblioteca. Pide los valores de
	 * los campos por mensajes PopUp.
	 */
	private void crearDocumento() {
		aLCrear = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Rellene todos los campos o presione cancelar");
				String titulo = JOptionPane.showInputDialog("Titulo:");
				String autor = JOptionPane.showInputDialog("Autor:");
				String anyoNac = JOptionPane.showInputDialog("Año de nacimiento:");
				String anyoPub = JOptionPane.showInputDialog("Año de publicación:");
				String editorial = JOptionPane.showInputDialog("Editorial:");
				String numPag = JOptionPane.showInputDialog("Número de páginas:");
				String portada = JOptionPane.showInputDialog("Imagen de portada(Ruta del archivo!):");
				modelo.anyadirLibro(titulo, autor, anyoNac, anyoPub, editorial, numPag, portada);
				JOptionPane.showMessageDialog(null, "Libro añadido a la biblioteca.");
			}
		};
		vista.getBtnCrearDocumento().addActionListener(aLCrear);
	}

	/**
	 * Permite al usuario actualizar un libro existente. Pide los valores de los
	 * campos por mensajes PopUp. Controla que el Id sea correcto.
	 */
	private void editarDocumento() {
		aLActualizar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Rellene todos los campos o presione cancelar");
				String id = JOptionPane.showInputDialog("¿Indique el ID del libro?");
				String titulo = JOptionPane.showInputDialog("Titulo:");
				String autor = JOptionPane.showInputDialog("Autor:");
				String anyoNac = JOptionPane.showInputDialog("Año de nacimiento:");
				String anyoPub = JOptionPane.showInputDialog("Año de publicación:");
				String editorial = JOptionPane.showInputDialog("Editorial:");
				String numPag = JOptionPane.showInputDialog("Número de páginas:");
				String rutaImagen = JOptionPane.showInputDialog("Imagen de portada(Ruta del archivo!):");
				int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro?", "Alerta!",
						JOptionPane.YES_NO_OPTION);
				if (confirmacion == 0) {
					modelo.editarLibro(id, titulo, autor, anyoNac, anyoPub, editorial, numPag, rutaImagen);
					if (!modelo.getComprobarID()) {
						JOptionPane.showMessageDialog(new JFrame(), "ID incorrecto", null, JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Documento con ID " + id + " actualizado.");
						modelo.setComprobarID(false);
					}

				}
			}
		};
		vista.getBtnEditarDocumento().addActionListener(aLActualizar);
	}

	/**
	 * Permite al usuario eliminar un libro existente. Controla que el Id sea
	 * correcto.
	 */
	private void eliminarDocumento() {
		aLEliminar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = JOptionPane.showInputDialog("¿Indique el ID del libro?");
				int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro?", "Alerta!",
						JOptionPane.YES_NO_OPTION);

				if (confirmacion == 0) {
					modelo.eliminarLibro(id);
					if (!modelo.getComprobarID()) {
						JOptionPane.showMessageDialog(new JFrame(), "ID incorrecto", null, JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Documento con ID " + id + " eliminado.");
						modelo.setComprobarID(false);
					}
				}
			}
		};
		vista.getBtnEliminarDocumento().addActionListener(aLEliminar);
	}

	/**
	 * Permite al usuario Realizar concultas contra la coleccion. Pidiendo que
	 * seleccione un campo, un filtro y que introduzca un valor.
	 **/
	private void consultarBD() {
		aLConsultas = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(),
						"1-) Seleccione el campo que quiera buscar dentro del documento." + "\n"
								+ "2-) Seleccione un filtro (Igual, Mayor o Igual o Menor o Igual)." + "\n"
								+ "3-) Introduzca el valor que desea buscar.",
						"Guia", JOptionPane.INFORMATION_MESSAGE);
				String[] filtros = { "Igual", "Mayor o Igual", "Menor o Igual" };
				String[] campos = { "Id", "Titulo", "Autor", "Anyo_nacimiento", "Anyo_publicacion", "Editorial",
						"Numero_paginas", "Thumbnail" };
				int seleccionCampo = JOptionPane.showOptionDialog(null, "Seleccione un campo de busqueda", "Campos",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, campos, campos[0]);
				int seleccionFiltro = JOptionPane.showOptionDialog(null, "Seleccione un filtro de busqueda", "Filtros",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, filtros, filtros[0]);
				String filtro = filtros[seleccionFiltro];
				String campo = campos[seleccionCampo];
				String valor = JOptionPane.showInputDialog("Indique el valor de busqueda: ");

				ArrayList<String> respuesta = modelo.respuestaConsulta(filtro, campo, valor);
				vista.getTxResultados().setText("");
				for (String linea : respuesta)
					vista.getTxResultados().append(linea);
			}
		};
		vista.getBtnConsultas().addActionListener(aLConsultas);
	}

	/**
	 * Permite al usuario desconectarse de la base de datos.
	 */
	private void desconectarBd() {
		aLDesconectar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (conectado) {
					modelo.desconectar();
					conectado = false;
					JOptionPane.showMessageDialog(null, "Conexion finalizada");
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "No está conectado a la base de datos", null,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		vista.getBtDesconectar().addActionListener(aLDesconectar);
	}
}
