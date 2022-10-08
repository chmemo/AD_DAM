package es.florida.AE1;

import java.awt.EventQueue;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.TextField;
import java.util.ArrayList;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class Interfaz extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private TextField directorioTrabajo;
    private TextArea areaEscritura;
    private JEditorPane areaLectura;
    private JComboBox<String> listaFicheros;
    private JComboBox<String> listaOpciones;
    private JButton botonAceptar;
    private JButton guardar;
    private JButton botonAyuda;
    private GestorFicheros gestor;
    private String nombreFichero, nombreDirectorio;

    /**
     * Metodo main() que ejecuta el programa de gestion de ficheros.
     * Lanza el programa y muestra un mensaje
     * 
     * @param args[]
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interfaz frame = new Interfaz();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Lanza la interfaz grafica de gestion de ficheros
     */
    public Interfaz() {
        crearInterfaz();
        seleccionDirectorio();
        seleccionFichero();
        establecerOpciones();
        ejecutarBotonAceptar();
    }

    /**
     * Instancia los elementos que componen la interfaz grafica
     */
    private void crearInterfaz() {
        Guia();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1039, 599);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        areaEscritura = new TextArea();
        areaEscritura.setBounds(283, 23, 719, 203);
        contentPane.add(areaEscritura);

        areaLectura = new JEditorPane();
        areaLectura.setBounds(283, 291, 728, 258);
        contentPane.add(areaLectura);

        guardar = new JButton("Guardar");
        guardar.setBounds(283, 232, 89, 23);
        contentPane.add(guardar);
        
        botonAyuda = new JButton("Ayuda");
        botonAyuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Guia();
            }
        });

        botonAyuda.setBounds(67, 526, 89, 23);
        contentPane.add(botonAyuda);

        JLabel lblNewLabel = new JLabel("Directorio de trabajo:");
        lblNewLabel.setBounds(33, 10, 136, 14);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Lista de ficheros:");
        lblNewLabel_1.setBounds(33, 70, 123, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2_1 = new JLabel("Opciones:");
        lblNewLabel_2_1.setBounds(33, 266, 65, 14);
        contentPane.add(lblNewLabel_2_1);

        JLabel lblNewLabel_2_3 = new JLabel("Lectura:");
        lblNewLabel_2_3.setBounds(283, 266, 66, 14);
        contentPane.add(lblNewLabel_2_3);

        JLabel lblNewLabel_2_2 = new JLabel("Escritura:");
        lblNewLabel_2_2.setBounds(283, 10, 66, 14);
        contentPane.add(lblNewLabel_2_2);
    }

    /**
     * Instancia un TextField, donde propocinar el nombre del directorio de trabajo.
     * Instancia un Objeto gestor.
     * Obtiene el String con el nombre del directorio.
     * Pasa como parametro el nombre al campo directorio del objeto gestor.
     * Obtiene un array con los ficheros dentro del directorio y los muestra por la
     * interfaz.
     */
    private void seleccionDirectorio() {
        directorioTrabajo = new TextField();
        directorioTrabajo.setBounds(33, 30, 136, 22);
        contentPane.add(directorioTrabajo);
        directorioTrabajo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaEscritura.setText("");
                gestor = new GestorFicheros("", "");
                String nombreDirectorio = directorioTrabajo.getText();
                gestor.setDirectorio(nombreDirectorio);
                String[] lista = gestor.ElegirDirectorio(nombreDirectorio);

                listaFicheros.removeAllItems();
                String lineas = "";

                for (String elemento : lista) {
                    lineas += elemento + "\n";
                    listaFicheros.addItem(elemento);
                }
                areaLectura
                        .setText("Lista de elementos dentro del directorio " + nombreDirectorio + ":" + "\n" + lineas);
            }
        });
    }

    /**
     * Instancia un JComboBox String con la lista de los ficheros del directorio.
     * Obtiene un String con el nombre del fichero seleccionado en la lista.
     * Asigna el nombre al campo fichero del objeto gestor
     */
    private void seleccionFichero() {
        listaFicheros = new JComboBox<String>();
        listaFicheros.addItem("_");
        listaFicheros.setBounds(33, 85, 201, 22);
        contentPane.add(listaFicheros);
        listaFicheros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreFichero = (String) listaFicheros.getSelectedItem();
                gestor.setFichero(nombreFichero);
            }
        });
    }

    /**
     * Instancia un JButton que al presionar ejecuta una tarea seleccionada y llama
     * a los metodos de la clase GestorFicheros
     */
    private void ejecutarBotonAceptar() {
        botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nombreDirectorio = gestor.getDirectorio();
                nombreFichero = gestor.getFichero();
                String eleccion = (String) listaOpciones.getSelectedItem();
                areaEscritura.setText("");
                areaLectura.setContentType("text/html");

                if (eleccion.equals("Info")) {
                    interfazInfo();

                } else if (eleccion.equals("Crear_fichero")) {
                    interfazCrearFichero(null, null);

                } else if (eleccion.equals("Mostrar_contenido")) {
                    interfazMostrarContenido();

                } else if (eleccion.equals("Renombrar")) {
                    interfazRenombrar();

                } else if (eleccion.equals("Copiar")) {
                    interfazCopiar();

                } else if (eleccion.equals("Eliminar")) {
                    interfazEliminar();
                } else if (eleccion.equals("Buscar")) {
                    ArrayList<String> contenido = gestor.MostrarContenido(nombreDirectorio, nombreFichero);
                    MostrarTextArea(contenido, 1);
                    interfazBuscar();

                } else if (eleccion.equals("Editar")) {
                    interfazEditar();

                } else if (eleccion.equals("Reemplazar_palabra")) {
                    ArrayList<String> contenido = gestor.MostrarContenido(nombreDirectorio, nombreFichero);
                    MostrarTextArea(contenido, 1);
                    interfazReemplazar();
                }
            }
        });
        botonAceptar.setBounds(67, 345, 89, 23);
        contentPane.add(botonAceptar);
    }

    /**
     * Instancia un JcomboBox String con una lista de opciones pa gestionar un
     * fichero
     */
    private void establecerOpciones() {
        listaOpciones = new JComboBox<String>();
        listaOpciones.setBounds(35, 291, 161, 33);
        contentPane.add(listaOpciones);
        listaOpciones.addItem("_");
        listaOpciones.addItem("Info");
        listaOpciones.addItem("Crear_fichero");
        listaOpciones.addItem("Editar");
        listaOpciones.addItem("Mostrar_contenido");
        listaOpciones.addItem("Buscar");
        listaOpciones.addItem("Reemplazar_palabra");
        listaOpciones.addItem("Copiar");
        listaOpciones.addItem("Renombrar");
        listaOpciones.addItem("Eliminar");
    }

    /**
     * Muestra las caracteristicas principales de un fichero en la interfaz grafica
     */
    private void interfazInfo() {
        ArrayList<String> info = gestor.InfoFichero(nombreDirectorio, nombreFichero);
        MostrarTextArea(info, 2);
    }

    /**
     * Obtiene un String con el nombre de un fichero mediante un Pop Up para crear
     * un fichero nuevo, con el contenido pasado por parametro.
     * Si los parametros son null, crea un fichero vacio
     * 
     * @param ficheroNuevo
     * @param contenidoFichero
     */
    private void interfazCrearFichero(String ficheroNuevo, ArrayList<String> contenidoFichero) {

        if (ficheroNuevo == null && contenidoFichero == null) {
            String nombreFicheroNuevo = JOptionPane.showInputDialog(null, "Introduce nombre para el fichero");
            gestor.CrearFichero(nombreDirectorio, nombreFicheroNuevo, null);
            listaFicheros.addItem(nombreFicheroNuevo);
        } else {
            gestor.CrearFichero(nombreDirectorio, ficheroNuevo, contenidoFichero);
            listaFicheros.addItem(ficheroNuevo);
        }
    }

    /**
     * Muestra el contenido de un fichero en la interfaz grafica
     */
    private void interfazMostrarContenido() {
        ArrayList<String> lineas = gestor.MostrarContenido(nombreDirectorio, nombreFichero);
        MostrarTextArea(lineas, 1);
    }

    /**
     * Renombra un fichero existente, pidiendo la confirmacion de dicha accion
     * Obtiene el nuevo nombre, mediante un Pop Up
     * Cambia el nombre en la lista de ficherosnm de la interfaz
     */
    private void interfazRenombrar() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "Desea Renombrar el fichero!!!", null,
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (confirmacion == 0) {
            String nuevoNombre = JOptionPane.showInputDialog(null, "Introduce el nuevo nombre para el fichero");
            gestor.RenombrarFichero(nombreDirectorio, nombreFichero, nuevoNombre);
            String nombreAnterior = (String) listaFicheros.getSelectedItem();
            listaFicheros.removeItem(nombreAnterior);
            listaFicheros.addItem(nuevoNombre);
        }
    }

    /**
     * Copia un fichero y lo añade a la lista de ficheros de la interfaz
     */
    private void interfazCopiar() {
        ArrayList<String> contenidoFichero = gestor.MostrarContenido(nombreDirectorio, nombreFichero);
        String ficheroNuevo = gestor.CopiarFichero(nombreDirectorio, nombreFichero, contenidoFichero);
        listaFicheros.addItem(ficheroNuevo);
    }

    /**
     * Elimina un fichero existente, pidiendo la confirmacion de dicha accion.
     * Quita el fichero de la lista de ficheros de la interfaz
     */
    private void interfazEliminar() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "Desea eliminar " + nombreFichero, "Eliminar!!!",
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (confirmacion == 0) {
            gestor.SuprimirFichero(nombreDirectorio, nombreFichero);
            listaFicheros.removeItem(nombreFichero);
        }
    }

    /**
     * Obtiene ArrayList String con el contenido de un fichero y lo muestra por la
     * interfaz, para que pueda ser editado.
     * Llama al addActionListener del JButton guardar, para gestionar la
     * confirmacion y opciones para
     * guardar los cambios del fichero editado.
     * Proporcionan la opcion de editar unfichero existente o crear un nuevo fichero
     * con el contenido escrito mediante la interfaz
     */
    private void interfazEditar() {
        JOptionPane.showMessageDialog(new JFrame(), "Edite el fichero en la tabla Escitura y presione (Guardar).",
                "Editar", JOptionPane.INFORMATION_MESSAGE);
        ArrayList<String> contenidoFichero = gestor.MostrarContenido(nombreDirectorio, nombreFichero);
        MostrarTextArea(contenidoFichero, 1);

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<String> lineasTablero1 = new ArrayList<String>();
                lineasTablero1.add(areaEscritura.getText());
                String[] opciones = { "Escribir en fichero existente", "Crear fichero nuevo" };
                int eleccion = JOptionPane.showOptionDialog(null, "Escoge una opcion:", "Edicion:",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

                if (eleccion == 0) {
                    gestor.EscribirFichero(nombreDirectorio, nombreFichero, lineasTablero1);
                } else if (eleccion == 1) {
                    String nombreFicheroNuevo = JOptionPane.showInputDialog(null,
                            "Introduzca el nombre del nuevo fichero:");
                    interfazCrearFichero(nombreFicheroNuevo, lineasTablero1);
                }
            }
        });

    }

    /**
     * Obtiene una String mediante un mensaje Pop Up y para buscarlo en el fichero y
     * mostrarlo en negrita por la interfaz
     */
    private void interfazBuscar() {
        String palabraBuscada = JOptionPane.showInputDialog("Busqueda");
        ArrayList<String> contenido = gestor.MostrarContenido(nombreDirectorio, nombreFichero);
        ArrayList<String> contenidoFiltro = gestor.BuscarOcurrencias(contenido, palabraBuscada);

        for (String string : contenidoFiltro) {
            System.out.println("Linea en Interfaz: " + string);
        }
        MostrarTextArea(contenidoFiltro, 2);
    }

    /**
     * Obtiene 2 Strings mediante Pop Up´s para buscar una palabra y reemplazarla en
     * un fichero
     * Muestra el resultado en la interfaz grafica
     */
    private void interfazReemplazar() {
        String palabraBuscada = JOptionPane.showInputDialog("Busqueda");
        String palabraReemplazar = JOptionPane.showInputDialog(null, "Reemplazar");
        ArrayList<String> ficheroOriginal = gestor.MostrarContenido(nombreDirectorio, nombreFichero);
        MostrarTextArea(ficheroOriginal, 1);
        gestor.Reemplazar(nombreDirectorio, nombreFichero, palabraBuscada, palabraReemplazar);
        ArrayList<String> textoReemplazado = gestor.MostrarContenido(nombreDirectorio, nombreFichero);
        MostrarTextArea(textoReemplazado, 2);
    }

    /**
     * Muestra informacion en una de los dos tableros der la interfaz, segun el
     * numero de tablero que pasemos por parametro
     * 
     * @param contenido
     * @param textArea
     */
    private void MostrarTextArea(ArrayList<String> contenido, int textArea) {

        String lineas = "";
        for (String linea : contenido) {
            if (textArea == 1) {
                areaEscritura.append(linea + "\n");
            } else {
                lineas += linea + "\n";
                areaLectura.setText(lineas + "\n");

            }

        }
    }
    
    /**
     * Muestra un mensaje Pop Up, con la guia de la interfaz
     */
    private void Guia() {
        JOptionPane.showMessageDialog(new JFrame(),
                "- Escriba el nombre del directorio en el campo especificado y presione ENTER. " + "\n"
                        + "- Seleccione un fichero de la lista" + "\n"
                        + "- Seleccione la opcion deseada y pulse aceptar para ejecutarla." + "\n"
                        + "- Edite el contenido del fichero en el campo (Escritura) y presione Guardar, para aplicar los cambios." + "\n"
                        + "- El campo (Lectura) muestra el resultado de alguna de las opciones." + "\n"
                        + "- Presione ayuda, para volver a ver la guia.",
                "Guia de la interfaz: ", JOptionPane.INFORMATION_MESSAGE);
    }

}
