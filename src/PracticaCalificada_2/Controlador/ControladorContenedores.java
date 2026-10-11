package PracticaCalificada_2.Controlador;

import PracticaCalificada_2.Modelo.GestorContenedores;
import PracticaCalificada_2.Vista.VistaContenedores;

/**
 * Controlador - Maneja la lógica de negocio y coordina entre el Modelo y la Vista
 */
public class ControladorContenedores {

    private GestorContenedores modelo;
    private VistaContenedores vista;

    public ControladorContenedores(String rutaArchivo) {
        this.modelo = new GestorContenedores(rutaArchivo);
        this.vista = new VistaContenedores();
    }

    /**
     * Inicia la aplicación y muestra el menú principal
     */
    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            int opcion = vista.mostrarMenuPrincipal();

            switch (opcion) {
                case 1:
                    registrarNuevoContenedor();
                    break;
                case 2:
                    buscarContenedorPorId();
                    break;
                case 3:
                    marcarContenedorComoDanado();
                    break;
                case 4:
                    mostrarEspacioDesperdiciado();
                    break;
                case 5:
                    listarContenedoresActivos();
                    break;
                case 0:
                    continuar = false;
                    cerrarAplicacion();
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida. Intente nuevamente.");
            }
        }
    }

    /**
     * Registra un nuevo contenedor solicitando datos al usuario
     */
    private void registrarNuevoContenedor() {
        vista.mostrarTitulo("=== REGISTRAR CONTENEDOR ===");

        int id = vista.solicitarId("Ingrese el ID del contenedor");
        if (id <= 0) {
            vista.mostrarError("El ID debe ser un número positivo.");
            return;
        }

        String tipo = vista.solicitarTipo("Ingrese el tipo de contenedor");
        if (tipo == null || tipo.trim().isEmpty()) {
            vista.mostrarError("El tipo no puede estar vacío.");
            return;
        }

        modelo.registrarContenedor(id, tipo);
        vista.mostrarExito("Contenedor registrado exitosamente.");
    }

    /**
     * Busca un contenedor por su ID y muestra el resultado
     */
    private void buscarContenedorPorId() {
        vista.mostrarTitulo("=== BUSCAR CONTENEDOR ===");

        int id = vista.solicitarId("Ingrese el ID del contenedor a buscar");
        if (id <= 0) {
            vista.mostrarError("El ID debe ser un número positivo.");
            return;
        }

        String tipo = modelo.buscarContenedor(id);

        if (tipo != null) {
            vista.mostrarResultadoBusqueda(id, tipo);
        } else {
            vista.mostrarError("Contenedor no encontrado o el registro está vacío.");
        }
    }

    /**
     * Marca un contenedor como dañado
     */
    private void marcarContenedorComoDanado() {
        vista.mostrarTitulo("=== MARCAR COMO DAÑADO ===");

        int id = vista.solicitarId("Ingrese el ID del contenedor a marcar como dañado");
        if (id <= 0) {
            vista.mostrarError("El ID debe ser un número positivo.");
            return;
        }

        // Verificar si existe antes de marcar
        String tipoActual = modelo.buscarContenedor(id);
        if (tipoActual == null) {
            vista.mostrarError("El contenedor con ID " + id + " no existe o está vacío.");
            return;
        }

        modelo.marcarComoDanado(id);
        vista.mostrarExito("Contenedor marcado como DAÑADO exitosamente.");
    }

    /**
     * Calcula y muestra el espacio desperdiciado en el archivo
     */
    private void mostrarEspacioDesperdiciado() {
        vista.mostrarTitulo("=== REPORTE DE ESPACIO DESPERDICIADO ===");

        long espacioVacio = modelo.calcularEspacioDesperdiciado();

        if (espacioVacio >= 0) {
            vista.mostrarEspacioDesperdiciado(espacioVacio);
        } else {
            vista.mostrarError("Error al calcular el espacio desperdiciado.");
        }
    }

    /**
     * Lista todos los contenedores activos (ID != 0)
     */
    private void listarContenedoresActivos() {
        vista.mostrarTitulo("=== CONTENEDORES ACTIVOS ===");

        modelo.listarSoloActivos();

        vista.mostrarMensaje("\n--- Fin del listado ---");
    }

    /**
     * Cierra el archivo y la aplicación
     */
    private void cerrarAplicacion() {
        modelo.cerrar();
        vista.mostrarMensaje("Aplicación cerrada. ¡Hasta pronto!");
        vista.cerrar();
    }
}
