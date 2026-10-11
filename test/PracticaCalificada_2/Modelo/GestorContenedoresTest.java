package PracticaCalificada_2.Modelo;

import java.io.File;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test unitario para GestorContenedores (JUnit 4)
 * Evalúa los 5 Retos del Caso "Logística Segura".
 * @author carlospalominovidal
 */
public class GestorContenedoresTest {

    // Archivo temporal para las pruebas
    private static final String RUTA_TEST = "test_junit.dat";
    private GestorContenedores instance;

    public GestorContenedoresTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // INDEPENDENCIA: Borramos cualquier rastro anterior antes de empezar un test
        new File(RUTA_TEST).delete();
        instance = new GestorContenedores(RUTA_TEST);
    }

    @After
    public void tearDown() {
        // INDEPENDENCIA: Limpiamos todo al terminar
        instance.cerrar();
        new File(RUTA_TEST).delete();
    }

    /**
     * Test 1: registrarContenedor
     * INDEPENDIENTE: Valida directamente en el archivo sin usar buscarContenedor
     */
    @Test
    public void testRegistrarContenedor() {
        System.out.println("TEST 1: registrarContenedor");

        // Usar archivo único para este test
        String rutaTest1 = "test_registrar.dat";
        File f1 = new File(rutaTest1);
        f1.delete();

        GestorContenedores gestor1 = new GestorContenedores(rutaTest1);
        int id = 10;
        String tipo = "Textiles";

        gestor1.registrarContenedor(id, tipo);
        gestor1.cerrar();

        // DEBUG: Verificar tamaño del archivo
        long tamanioArchivo = f1.length();
        long tamanioEsperado = id * 44;
        System.out.println("DEBUG - Tamaño archivo: " + tamanioArchivo + " bytes, Esperado: " + tamanioEsperado + " bytes");

        // Validación directa en el archivo
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(rutaTest1, "r")) {
            long posicion = (id - 1) * 44;

            // Verificar que el archivo sea suficientemente grande
            if (raf.length() < posicion + 44) {
                fail("El archivo es demasiado pequeño. Tamaño: " + raf.length() + ", Necesita al menos: " + (posicion + 44));
            }

            raf.seek(posicion);

            int idLeido = raf.readInt();
            assertEquals("El ID registrado no coincide", id, idLeido);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                sb.append(raf.readChar());
            }
            String tipoLeido = sb.toString().trim();

            assertEquals("El tipo registrado no coincide", tipo, tipoLeido);
        } catch (Exception e) {
            fail("Error al leer el archivo: " + e.getMessage());
        } finally {
            f1.delete();
        }
    }

    /**
     * Test 2: buscarContenedor
     * INDEPENDIENTE: Escribe directamente en el archivo sin usar registrarContenedor
     */
    @Test
    public void testBuscarContenedor() {
        System.out.println("TEST 2: buscarContenedor");

        // Usar archivo único para este test
        String rutaTest2 = "test_buscar.dat";
        File f2 = new File(rutaTest2);
        f2.delete();

        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(rutaTest2, "rw")) {
            // Escribir ID 5 con "Electronica"
            raf.seek((5 - 1) * 44);
            raf.writeInt(5);
            String texto1 = String.format("%-20s", "Electronica").substring(0, 20);
            raf.writeChars(texto1);

            // Escribir ID 10 con "Quimicos"
            raf.seek((10 - 1) * 44);
            raf.writeInt(10);
            String texto2 = String.format("%-20s", "Quimicos").substring(0, 20);
            raf.writeChars(texto2);
        } catch (Exception e) {
            fail("Error al preparar el archivo: " + e.getMessage());
        }

        // Crear nueva instancia para probar buscarContenedor
        GestorContenedores gestor2 = new GestorContenedores(rutaTest2);

        // Búsqueda exitosa
        assertEquals("Electronica", gestor2.buscarContenedor(5));
        assertEquals("Quimicos", gestor2.buscarContenedor(10));

        // Búsqueda de no existente (ID 99 nunca fue escrito)
        assertNull("Debería retornar null para IDs inexistentes", gestor2.buscarContenedor(99));

        // Búsqueda de ID en hueco vacío (ID 1 no existe)
        assertNull("Debería retornar null para huecos vacíos", gestor2.buscarContenedor(1));

        gestor2.cerrar();
        f2.delete();
    }

    /**
     * Test 3: marcarComoDanado
     * INDEPENDIENTE: Escribe el registro directamente y valida el cambio directamente
     */
    @Test
    public void testMarcarComoDanado() {
        System.out.println("TEST 3: marcarComoDanado");
        int id = 1;

        // Usar archivo único para este test
        String rutaTest3 = "test_marcar.dat";
        File f3 = new File(rutaTest3);
        f3.delete();

        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(rutaTest3, "rw")) {
            raf.seek((id - 1) * 44);
            raf.writeInt(id);
            String texto = String.format("%-20s", "Carga Frágil").substring(0, 20);
            raf.writeChars(texto);
        } catch (Exception e) {
            fail("Error al preparar el archivo: " + e.getMessage());
        }

        // Crear nueva instancia y marcar como dañado
        GestorContenedores gestor3 = new GestorContenedores(rutaTest3);
        gestor3.marcarComoDanado(id);
        gestor3.cerrar();

        // Validar directamente en el archivo
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(rutaTest3, "r")) {
            // Primero verificar que el ID no cambió
            raf.seek((id - 1) * 44);
            int idLeido = raf.readInt();
            assertEquals("El ID no debe cambiar al marcar como dañado", id, idLeido);

            // Ahora verificar el tipo
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                sb.append(raf.readChar());
            }
            String tipoLeido = sb.toString().trim();
            assertEquals("El contenedor debería estar marcado como DAÑADO", "DAÑADO", tipoLeido);
        } catch (Exception e) {
            fail("Error al validar el archivo: " + e.getMessage());
        } finally {
            f3.delete();
        }
    }

    /**
     * Test 4: calcularEspacioDesperdiciado
     * INDEPENDIENTE: Escribe directamente en el archivo para crear huecos
     */
    @Test
    public void testCalcularEspacioDesperdiciado() {
        System.out.println("TEST 4: calcularEspacioDesperdiciado");

        // Usar archivo único para este test
        String rutaTest4 = "test_calcular.dat";
        File f4 = new File(rutaTest4);
        f4.delete();

        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(rutaTest4, "rw")) {
            // Registro 1 - ocupado
            raf.seek(0);
            raf.writeInt(1);
            String texto1 = String.format("%-20s", "A").substring(0, 20);
            raf.writeChars(texto1);

            // Registro 2 - vacío (ID = 0)
            raf.seek(44);
            raf.writeInt(0);
            String texto2 = String.format("%-20s", "").substring(0, 20);
            raf.writeChars(texto2);

            // Registro 3 - ocupado
            raf.seek(88);
            raf.writeInt(3);
            String texto3 = String.format("%-20s", "B").substring(0, 20);
            raf.writeChars(texto3);
        } catch (Exception e) {
            fail("Error al preparar el archivo: " + e.getMessage());
        }

        // Crear nueva instancia y calcular
        GestorContenedores gestor4 = new GestorContenedores(rutaTest4);

        // 1 hueco * 44 bytes = 44 bytes esperados
        long expResult = 44L;
        long result = gestor4.calcularEspacioDesperdiciado();

        assertEquals("El cálculo de espacio desperdiciado falló", expResult, result);

        gestor4.cerrar();
        f4.delete();
    }

    /**
     * Test 5: listarSoloActivos
     * INDEPENDIENTE: Escribe directamente en el archivo y captura la consola para validar el output.
     */
    @Test
    public void testListarSoloActivos() {
        System.out.println("TEST 5: listarSoloActivos");

        // Usar archivo único para este test
        String rutaTest5 = "test_listar.dat";
        File f5 = new File(rutaTest5);
        f5.delete();

        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(rutaTest5, "rw")) {
            // Registro 1 - ocupado con "Textiles"
            raf.seek(0);
            raf.writeInt(1);
            String texto1 = String.format("%-20s", "Textiles").substring(0, 20);
            raf.writeChars(texto1);

            // Registro 2 - vacío (ID = 0)
            raf.seek(44);
            raf.writeInt(0);
            String texto2 = String.format("%-20s", "").substring(0, 20);
            raf.writeChars(texto2);

            // Registro 3 - ocupado con "Mineros"
            raf.seek(88);
            raf.writeInt(3);
            String texto3 = String.format("%-20s", "Mineros").substring(0, 20);
            raf.writeChars(texto3);
        } catch (Exception e) {
            fail("Error al preparar el archivo: " + e.getMessage());
        }

        // Crear nueva instancia
        GestorContenedores gestor5 = new GestorContenedores(rutaTest5);

        // Capturar consola
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            gestor5.listarSoloActivos();
        } catch (Exception e) {
            fail("Excepción al listar: " + e.getMessage());
        } finally {
            System.setOut(originalOut); // Restaurar consola pase lo que pase
            gestor5.cerrar();
            f5.delete();
        }

        String salida = outContent.toString();

        if (salida.trim().isEmpty()) {
            fail("El método no imprimió nada.");
        }
        if (!salida.contains("Textiles") || !salida.contains("Mineros")) {
            fail("Faltan datos en el listado.");
        }
        if (salida.contains("ID: 0") || salida.contains("ID: 2")) {
            fail("El listado está mostrando huecos vacíos (ID 0).");
        }
    }

    /**
     * Test Técnico: Cerrar
     */
    @Test
    public void testCerrar() {
        System.out.println("cerrar");
        try {
            instance.cerrar();
        } catch (Exception e) {
            fail("Error al cerrar");
        }
    }

    /**
     * TEST 6: ANTI-TRAMPA (Arquitectura)
     * Valida que el MODELO use RandomAccessFile correctamente y genere archivos de tamaño fijo.
     * Este test SÍ depende de registrarContenedor porque valida la arquitectura del modelo.
     */
    @Test
    public void testValidarEstructuraFisica() {
        System.out.println("TEST 6: Validar Estructura Física");

        // Usar archivo único para este test crítico
        String rutaTest6 = "test_arquitectura.dat";
        File archivoFisico = new File(rutaTest6);
        archivoFisico.delete();

        // Crear nueva instancia limpia
        GestorContenedores gestor6 = new GestorContenedores(rutaTest6);

        // Usar el método del MODELO para registrar el ID 100
        // Esto debe crear un archivo con 99 huecos + 1 registro = 4400 bytes
        gestor6.registrarContenedor(100, "Final");

        // IMPORTANTE: Cerrar para forzar el flush al disco
        gestor6.cerrar();

        // Validar el tamaño físico del archivo
        long pesoReal = archivoFisico.length();
        long pesoEsperado = 100 * 44; // 4400 bytes

        archivoFisico.delete();

        assertEquals("El archivo no respeta la estructura de Acceso Directo (RandomAccessFile). "
                + "El modelo debe usar seek() para saltar a posiciones específicas, "
                + "creando huecos automáticamente. Esperado: " + pesoEsperado + " bytes, Obtenido: " + pesoReal + " bytes",
                pesoEsperado, pesoReal);
    }
}