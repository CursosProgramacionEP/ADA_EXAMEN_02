package PracticaCalificada_2.Modelo;

import java.io.RandomAccessFile;
//
public class GestorContenedoress {

    private RandomAccessFile archivo;
    // ID (4 bytes) + Tipo (20 chars * 2 = 40 bytes) = 44 bytes
    private static final int TAMANO_REGISTRO = 44;

    public GestorContenedoress(String ruta) {
        try {
            this.archivo = new RandomAccessFile(ruta, "rw");
        } catch (Exception e) { e.printStackTrace(); }
    }

    // RETO 1: REGISTRAR (Guardar ID y Tipo)
    // REGLA: El tipo debe guardarse ocupando exactamente 20 caracteres.
    // Si es más corto, rellenar con espacios. Si es más largo, cortar.
    public void registrarContenedor(int id, String tipo) {
        try {
            // --- CODIGO DEL ALUMNO AQUI ---

            // 1. Calcular posición: (id - 1) * 44
            // 2. Ir a la posición (seek)
            // 3. Escribir ID
            // 4. Formatear 'tipo' a 20 chars exactos y escribirlo (writeChars)

            // ------------------------------
        } catch (Exception e) { e.printStackTrace(); }
    }

    // RETO 2: BUSCAR (Recuperar Tipo por ID)
    // REGLA: Prohibido usar bucles. Debe ser acceso directo.
    public String buscarContenedor(int id) {
        try {
            // --- CODIGO DEL ALUMNO AQUI ---

            // 1. Calcular posición y hacer seek
            // 2. Leer ID (si es 0 o no coincide, retornar null)
            // 3. Leer los 20 caracteres del tipo
            // 4. Retornar el string sin espacios (trim)

            return null; // Cambiar
            // ------------------------------
        } catch (Exception e) { return null; }
    }

    // RETO 3: ACTUALIZAR ESTADO (Marcar como DAÑADO)
    // REGLA: Buscar el registro y sobrescribir SOLO el texto del tipo con "DAÑADO".
    // NO debe tocar el ID.
    public void marcarComoDanado(int id) {
        try {
            // --- CODIGO DEL ALUMNO AQUI ---

            // 1. Calcular posición del texto (Posición Inicio + 4 bytes del ID)
            // 2. Hacer seek a esa posición
            // 3. Escribir "DAÑADO             " (ajustado a 20 chars)

            // ------------------------------
        } catch (Exception e) { e.printStackTrace(); }
    }

    // RETO 4: REPORTE DE ESPACIO (Contar bytes vacíos)
    // REGLA: Recorrer el archivo y sumar 44 bytes por cada registro que tenga ID = 0.
    public long calcularEspacioDesperdiciado() {
        try {
            // --- CODIGO DEL ALUMNO AQUI ---

            // 1. Usar un bucle (aquí sí está permitido) para leer todo el archivo
            // 2. Si lee un ID == 0, sumar 44 al contador

            return -1; // Cambiar
            // ------------------------------
        } catch (Exception e) { return -1; }
    }

    // RETO 5: LISTAR ACTIVOS
    // REGLA: Imprimir en consola solo los IDs que sean diferentes de 0.
    public void listarSoloActivos() {
        try {
            // --- CODIGO DEL ALUMNO AQUI ---

            // 1. Bucle que recorre todo
            // 2. Si ID != 0, System.out.println("ID: " + id + " - Tipo: " + tipo)

            // ------------------------------
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void cerrar() {
        try { archivo.close(); } catch (Exception e) {}
    }
}