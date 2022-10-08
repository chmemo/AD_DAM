package es.florida.AE1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GestorFicheros {

    String directorio, fichero;

    /**
     * Metodo construntor de la clase, recibe como parametros los nombres del directorio y el fichero
     * @param directorio
     * @param fichero
     */
    public GestorFicheros(String directorio, String fichero) {
        this.directorio = directorio;
        this.fichero = fichero;
    }

    /**
     * Crea un File directorio y devuelve una lista con el contenido de dicho directorio
     * @param nombreDir
     * @return array[] String
     */
    public String[] ElegirDirectorio(String nombreDir) {

        File directorio = new File(nombreDir);
        String[] lista = null;

        if (directorio.isDirectory() && directorio.exists()) {
            lista = directorio.list();
        } else {
            JOptionPane.showMessageDialog(new JFrame(), directorio.getName() + " no existe o no es un directorio", null,
                    JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }

    /**
     * Obtiene la informacion de las caracteristicas de un fichero y devuelve una lista con la informacion
     * @param nombreDir
     * @param nombreFichero
     * @return ArrayList Strings mensajes
     */
    public ArrayList<String> InfoFichero(String nombreDir, String nombreFichero) {

        File fichero = new File(nombreDir + "\\" + nombreFichero);
        ArrayList<String> mensajes = new ArrayList<String>();

        if (fichero.isFile() && fichero.exists()) {
            int punto = nombreFichero.indexOf(".");
            String extension = nombreFichero.substring(punto);

            mensajes.add("Informacion del fichero " + fichero.getName() + ":");
            mensajes.add("- Extension del fichero: " + extension);
            mensajes.add("- Ruta absoluta: " + fichero.getAbsolutePath());
            mensajes.add("- Ruta relativa: " + fichero.getPath());
            mensajes.add("- Tamaño en bytes: " + fichero.length() + " B");
            mensajes.add("- Fecha de creacion/modificacion del fichero: " + new Date(fichero.lastModified()));
            mensajes.add("- Es ejecutable: " + fichero.canExecute());
            mensajes.add("- Se puede escribir: " + fichero.canWrite());
            mensajes.add("- Se puede leer: " + fichero.canRead());
            mensajes.add("- Esta oculto: " + fichero.isHidden());
        } else {
            JOptionPane.showMessageDialog(new JFrame(), fichero.getName() + " no existe o no es un fichero", null,
                    JOptionPane.ERROR_MESSAGE);

        }
        return mensajes;
    }

    /**
     * Crea un fichero vacio o con contenido, si el parametro ArrayList es null
     * @param nombreDir
     * @param nuevoFichero
     * @param contenido o null
     */
    public void CrearFichero(String nombreDir, String nuevoFichero, ArrayList<String> contenido) {

        File fichero = new File(nombreDir + "\\" + nuevoFichero);

        if (!fichero.exists()) {
            if (contenido == null) {
                try {
                    fichero.createNewFile();
                    JOptionPane.showMessageDialog(new JFrame(),
                            fichero.getName() + " creado correctamente en el directorio ", null,
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    FileWriter fw = new FileWriter(fichero, true);
                    BufferedWriter bw = new BufferedWriter(fw);

                    for (String linea : contenido) {
                        bw.write(linea);
                        bw.newLine();
                    }
                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Error al intentar crear " + fichero.getName(), null,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Devuelve una lista con el contenido de un fichero
     * @param nombreDir
     * @param nombreFichero
     * @return ArrayList String contenidoFichero
     */
    public ArrayList<String> MostrarContenido(String nombreDir, String nombreFichero) {

        ArrayList<String> contenidoFichero = new ArrayList<String>();
        File fichero = new File(nombreDir + "\\" + nombreFichero);

        if (fichero.exists() && fichero.isFile()) {

            try {
                FileReader fr = new FileReader(fichero);
                BufferedReader br = new BufferedReader(fr);
                String linea = br.readLine();

                while (linea != null) {
                    contenidoFichero.add(linea);
                    linea = br.readLine();
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "El elemento no existe o no es un fichero", null,
                    JOptionPane.ERROR_MESSAGE);
        }
        return contenidoFichero;
    }

    /**
     * Renombra un fichero existente
     * @param nombreDir
     * @param nombreFichero
     * @param nuevoNombre
     */
    public void RenombrarFichero(String nombreDir, String nombreFichero, String nuevoNombre) {

        File fichero = new File(nombreDir + "\\" + nombreFichero);
        File nuevoFichero = new File(nombreDir + "\\" + nuevoNombre);

        if (fichero.exists() && fichero.isFile()) {

            fichero.renameTo(nuevoFichero);
            JOptionPane.showMessageDialog(new JFrame(), fichero.getName() + " ahora se llama " + nuevoFichero.getName(),
                    null, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), fichero.getName() + " no existe o no es un fichero", null,
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Copia un fichero existente
     * @param nombreDir
     * @param nombreFichero
     * @param contenidoFichero
     * @return Nombre del fichero nuevo
     */
    public String CopiarFichero(String nombreDir, String nombreFichero, ArrayList<String> contenidoFichero) {

        String nombreCopia = "";
        File copia = null;
        try {
            int punto = nombreFichero.indexOf(".");
            String nombreSinExtension = nombreFichero.substring(0, punto);
            String extension = nombreFichero.substring(punto);
            nombreCopia = nombreSinExtension + "_copia" + extension;
            copia = new File(nombreDir + "\\" + nombreCopia);
            FileWriter fw = new FileWriter(copia);
            BufferedWriter bw = new BufferedWriter(fw);

            for (String linea : contenidoFichero) {
                bw.write(linea);
                bw.newLine();
            }
            JOptionPane.showMessageDialog(new JFrame(), nombreCopia + " creado con exito", null,
                    JOptionPane.INFORMATION_MESSAGE);
            bw.close();
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Error al copiar el fichero " + nombreCopia, null,
                    JOptionPane.ERROR_MESSAGE);
        }
        return copia.getName();
    }

    /**
     * Elimina un fichero existente
     * @param nombreDir
     * @param nombreFichero
     */
    public void SuprimirFichero(String nombreDir, String nombreFichero) {

        File fichero = new File(nombreDir + "\\" + nombreFichero);

        if (fichero.exists()) {

            fichero.delete();
            JOptionPane.showMessageDialog(new JFrame(), fichero.getName() + " ha sido eliminado", null,
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR!!! el elemento no existe o no es un fichero", null,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Escribe lineas en un fichero existente
     * @param nombreDir
     * @param nombreFichero
     * @param nuevoContenido
     */
    public void EscribirFichero(String nombreDir, String nombreFichero, ArrayList<String> nuevoContenido) {

        File ficheroOriginal = new File(nombreDir + "\\" + nombreFichero);

        if (ficheroOriginal.exists() && ficheroOriginal.isFile()) {

            try {
                ficheroOriginal.delete();
                File ficheroEditado = new File(nombreDir + "\\" + nombreFichero);
                FileWriter fw = new FileWriter(ficheroEditado);
                BufferedWriter bw = new BufferedWriter(fw);

                for (String linea : nuevoContenido) {
                    bw.write(linea);
                }
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR!!! el elemento no existe o no es un fichero", null,
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Lee el contenido de un fichero y busca la palabra pasada por parametro.
     * @param contenidoFichero
     * @param busqueda
     * @return ArrayList String ocurrencias
     */
    public ArrayList<String> BuscarOcurrencias(ArrayList<String> contenidoFichero, String busqueda) {

        ArrayList<String> ocurrencias = new ArrayList<String>();
        String palabraBuscada = "<font color='red'><strong>" + busqueda + "</strong></font>";
        
        for (String linea : contenidoFichero) {
            if (linea.toLowerCase().contains(busqueda) || linea.toUpperCase().contains(busqueda))
                linea = linea.replaceAll(busqueda, palabraBuscada);
                
            System.out.println("Linea en Gestor: " +  linea);
            ocurrencias.add(linea);
        }
        return ocurrencias;
    }

    /**
     * Busca la palabra pasada por parametro y la reemplaza en el fichero, por otra palabra, pasada por parametro
     * @param nombreDir
     * @param nombreFichero
     * @param busqueda
     * @param reemplazar
     */
    public void Reemplazar(String nombreDir, String nombreFichero, String busqueda, String reemplazar) {

        File fichero = new File(nombreDir + "\\" + nombreFichero);
        ArrayList<String> lineas = new ArrayList<String>();

        if (fichero.exists() && fichero.isFile()) {
            try {
                FileReader fr = new FileReader(fichero);
                BufferedReader br = new BufferedReader(fr);
                String linea = br.readLine();

                while (linea != null) {

                    if (linea.contains(busqueda.toLowerCase()) || linea.contains(busqueda.toUpperCase())) {
                        linea = linea.replaceAll(busqueda, reemplazar);
                    }
                    lineas.add(linea);
                    linea = br.readLine();
                }
                fichero.delete();
                br.close();
                fr.close();
                File ficheroCopia = new File(nombreDir + "\\" + nombreFichero);
                FileWriter fw = new FileWriter(ficheroCopia);
                BufferedWriter bw = new BufferedWriter(fw);

                for (String lineaRemplazada : lineas) {
                    bw.write(lineaRemplazada);
                    bw.newLine();
                }
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR!!! el elemento no existe o no es un fichero", null,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Devuelve el nombre del campo directorio
     * @return String directorio
     */
    public String getDirectorio() {
        return directorio;
    }

    /**
     * Devuelve el nombre del campo fichero
     * @return String fichero
     */
    public String getFichero() {
        return fichero;
    }

    /**
     * Asigna valor al campo fichero
     * @param fichero
     */
    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    /**
     * Asigna valor al campo directorio
     * @param directorio
     */
    public void setDirectorio(String directorio) {
        this.directorio = directorio;
    }

}
