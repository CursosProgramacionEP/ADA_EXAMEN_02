package PracticaCalificada_2.Modelo;

import java.io.RandomAccessFile;

public class GestorContenedores {

    private RandomAccessFile archivo;
    // ID (4 bytes) + Tipo (20 chars * 2 = 40 bytes) = 44 bytes
    private static final int TAMANO_REGISTRO = 44;

    public GestorContenedores(String ruta) {
        try {
            this.archivo = new RandomAccessFile(ruta, "rw");
        } catch (Exception e) { e.printStackTrace(); }
    }

    // RETO 1: REGISTRAR
    public void registrarContenedor(int id, String tipo) {
        try {
            // 1. Calcular posición matemática
            long posicion = (long) (id - 1) * TAMANO_REGISTRO;

            // 2. Ir a la posición (Salto directo)
            archivo.seek(posicion);

            // 3. Escribir ID
            archivo.writeInt(id);

            // 4. Formatear String a 20 caracteres exactos
            // %-20s rellena con espacios a la derecha. substring asegura que no pase de 20.
            String textoFormateado = String.format("%-20s", tipo).substring(0, 20);
            archivo.writeChars(textoFormateado);

        } catch (Exception e) { e.printStackTrace(); }
    }

    // RETO 2: BUSCAR
    public String buscarContenedor(int id) {
        try {
            long posicion = (long) (id - 1) * TAMANO_REGISTRO;

            // Validar si la posición cae fuera del archivo actual
            if (posicion >= archivo.length()) return null;

            archivo.seek(posicion);

            int idLeido = archivo.readInt();

            // Si leemos un 0 (hueco vacío) o un ID distinto (colisión rara/error), retornamos null
            if (idLeido != id) return null;

            // Leer el nombre (20 caracteres uno por uno)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                sb.append(archivo.readChar());
            }

            return sb.toString().trim(); // Quitamos los espacios de relleno

        } catch (Exception e) { return null; }
    }

    // RETO 3: ACTUALIZAR ESTADO
    public void marcarComoDanado(int id) {
        try {
            long posicion = (long) (id - 1) * TAMANO_REGISTRO;

            // Validamos existencia rápida
            if (posicion >= archivo.length()) return;

            // Saltamos el ID (4 bytes) para no tocarlo, vamos directo al texto
            archivo.seek(posicion + 4);

            String textoDañado = String.format("%-20s", "DAÑADO").substring(0, 20);
            archivo.writeChars(textoDañado);

        } catch (Exception e) { e.printStackTrace(); }
    }

    // RETO 4: REPORTE DE ESPACIO
    public long calcularEspacioDesperdiciado() {
        long desperdicio = 0;
        try {
            archivo.seek(0); // Volver al inicio

            // Recorremos todo el archivo
            while (archivo.getFilePointer() < archivo.length()) {
                int idLeido = archivo.readInt();

                // Si el ID es 0, es un hueco vacío
                if (idLeido == 0) {
                    desperdicio += TAMANO_REGISTRO;
                }

                // Saltamos los 40 bytes del texto para ir rápido al siguiente registro
                archivo.skipBytes(40);
            }
        } catch (Exception e) { return -1; }
        return desperdicio;
    }

    // RETO 5: LISTAR ACTIVOS
    public void listarSoloActivos() {
        try {
            archivo.seek(0);

            while (archivo.getFilePointer() < archivo.length()) {
                long posActual = archivo.getFilePointer(); // Guardamos posición por si acaso

                int id = archivo.readInt();

                // Leemos el texto siempre para avanzar el puntero correctamente
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 20; i++) sb.append(archivo.readChar());

                // Solo imprimimos si es un contenedor real
                if (id != 0) {
                    System.out.println("ID: " + id + " | Contenido: " + sb.toString().trim());
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void cerrar() {
        try { archivo.close(); } catch (Exception e) {}
    }
}