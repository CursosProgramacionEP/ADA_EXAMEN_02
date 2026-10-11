package PracticaCalificada_2.Vista;

import java.util.Scanner;

/**
 * Vista - Maneja toda la interacción con el usuario (consola)
 */
public class VistaContenedores {

    private Scanner scanner;

    public VistaContenedores() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal y retorna la opción seleccionada
     */
    public int mostrarMenuPrincipal() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE CONTENEDORES     ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        System.out.println("1. Registrar Contenedor");
        System.out.println("2. Buscar Contenedor por ID");
        System.out.println("3. Marcar Contenedor como Dañado");
        System.out.println("4. Calcular Espacio Desperdiciado");
        System.out.println("5. Listar Contenedores Activos");
        System.out.println("0. Salir");
        System.out.println("═══════════════════════════════════════════");
        System.out.print("Seleccione una opción: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Opción inválida
        }
    }

    /**
     * Solicita un ID al usuario
     */
    public int solicitarId(String mensaje) {
        System.out.print(mensaje + ": ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Solicita un tipo de contenedor al usuario
     */
    public String solicitarTipo(String mensaje) {
        System.out.print(mensaje + ": ");
        return scanner.nextLine().trim();
    }

    /**
     * Muestra un título formateado
     */
    public void mostrarTitulo(String titulo) {
        System.out.println("\n" + titulo);
    }

    /**
     * Muestra un mensaje general
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Muestra un mensaje de éxito
     */
    public void mostrarExito(String mensaje) {
        System.out.println("✓ " + mensaje);
    }

    /**
     * Muestra un mensaje de error
     */
    public void mostrarError(String mensaje) {
        System.out.println("✗ ERROR: " + mensaje);
    }

    /**
     * Muestra el resultado de una búsqueda
     */
    public void mostrarResultadoBusqueda(int id, String tipo) {
        System.out.println("─────────────────────────────────────────");
        System.out.println("Contenedor encontrado:");
        System.out.println("  ID: " + id);
        System.out.println("  Tipo: " + tipo);
        System.out.println("─────────────────────────────────────────");
    }

    /**
     * Muestra el reporte de espacio desperdiciado
     */
    public void mostrarEspacioDesperdiciado(long bytes) {
        System.out.println("─────────────────────────────────────────");
        System.out.println("Espacio desperdiciado: " + bytes + " bytes");

        if (bytes > 0) {
            long registrosVacios = bytes / 44; // TAMANO_REGISTRO = 44
            System.out.println("Registros vacíos: " + registrosVacios);
            System.out.println("Espacio en KB: " + String.format("%.2f", bytes / 1024.0) + " KB");
        } else {
            System.out.println("No hay espacio desperdiciado. ¡Archivo optimizado!");
        }
        System.out.println("─────────────────────────────────────────");
    }

    /**
     * Cierra el scanner
     */
    public void cerrar() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
