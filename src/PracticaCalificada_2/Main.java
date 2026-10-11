package PracticaCalificada_2;

import PracticaCalificada_2.Controlador.ControladorContenedores;

/**
 * Clase Principal - Punto de entrada de la aplicación
 * Implementa el patrón MVC (Modelo-Vista-Controlador)
 */
public class Main {

    public static void main(String[] args) {
        // Ruta del archivo de datos
        String rutaArchivo = "contenedores.dat";

        // Crear el controlador (que crea el modelo y la vista)
        ControladorContenedores controlador = new ControladorContenedores(rutaArchivo);

        // Iniciar la aplicación
        System.out.println("Iniciando Sistema de Gestión de Contenedores...");
        System.out.println("Archivo de datos: " + rutaArchivo);

        controlador.iniciar();
    }
}
