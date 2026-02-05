package PracticaCalificada_2.Modelo;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test unitario para GestorContenedores (JUnit 4)
 * Evalúa los 5 Retos del Caso "Logística Segura".
 * * @author carlospalominovidal
 */
public class GestorContenedoresTest {
    
    // Archivo temporal para las pruebas (se borra al final)
    private static final String RUTA_TEST = "test_junit.dat";
    private GestorContenedores instance;
    
    public GestorContenedoresTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        // Opcional: Código que corre una vez antes de todos los tests
    }
    
    @AfterClass
    public static void tearDownClass() {
        // Opcional: Limpieza final
    }
    
    @Before
    public void setUp() {
        // IMPORTANTE: Antes de cada test, borramos el archivo viejo y creamos uno nuevo.
        // Esto asegura que cada prueba inicie con la base de datos vacía.
        new File(RUTA_TEST).delete();
        instance = new GestorContenedores(RUTA_TEST);
    }
    
    @After
    public void tearDown() {
        // Cerramos el archivo para liberar el recurso y lo borramos
        instance.cerrar();
        new File(RUTA_TEST).delete();
    }

    /**
     * Test of registrarContenedor method.
     * Verifica que se guarde sin errores y que el formato sea recuperable.
     */
    @Test
    public void testRegistrarContenedor() {
        System.out.println("TEST 1: registrarContenedor");
        int id = 10;
        String tipo = "Textiles"; // Menos de 20 caracteres
        
        // 1. Ejecutamos el registro
        instance.registrarContenedor(id, tipo);
        
        // 2. Verificamos que exista buscándolo
        String resultado = instance.buscarContenedor(id);
        
        // 3. El resultado debe ser igual al ingresado (sin espacios extra)
        assertNotNull("El registro no debe ser null", resultado);
        assertEquals("Textiles", resultado);
    }

    /**
     * Test of buscarContenedor method.
     * Verifica la búsqueda directa por ID (Hashing).
     */
    @Test
    public void testBuscarContenedor() {
        System.out.println("TEST 2: buscarContenedor");
        
        // Preparamos datos: Guardamos ID 5 y ID 10
        instance.registrarContenedor(5, "Electronica");
        instance.registrarContenedor(10, "Quimicos");
        
        // 1. Buscamos el ID 5
        String resultado = instance.buscarContenedor(5);
        assertEquals("Electronica", resultado);
        
        // 2. Buscamos un ID que NO existe (debería ser null)
        String noExiste = instance.buscarContenedor(99);
        assertNull("Debería retornar null para IDs inexistentes", noExiste);
    }

    /**
     * Test of marcarComoDanado method.
     * Verifica la actualización in-situ sin tocar el ID.
     */
    @Test
    public void testMarcarComoDanado() {
        System.out.println("TEST 3: marcarComoDanado");
        int id = 1;
        
        // 1. Registramos normal
        instance.registrarContenedor(id, "Carga Frágil");
        
        // 2. Marcamos como dañado
        instance.marcarComoDanado(id);
        
        // 3. Recuperamos y verificamos que diga "DAÑADO"
        String resultado = instance.buscarContenedor(id);
        
        // Usamos contains por si acaso quedan espacios, aunque el trim debería limpiarlo
        assertTrue("El contenedor debería estar marcado como DAÑADO", resultado.contains("DAÑADO"));
    }

    /**
     * Test of calcularEspacioDesperdiciado method.
     * Verifica el cálculo matemático de los huecos vacíos.
     */
    @Test
    public void testCalcularEspacioDesperdiciado() {
        System.out.println("TEST 4: calcularEspacioDesperdiciado");
        
        // Escenario:
        // Guardamos ID 1.
        // Guardamos ID 3.
        // El ID 2 queda vacío (Hueco).
        instance.registrarContenedor(1, "A");
        instance.registrarContenedor(3, "B");
        
        // Cálculo esperado: 1 hueco * 44 bytes = 44 bytes.
        long expResult = 44L;
        long result = instance.calcularEspacioDesperdiciado();
        
        assertEquals("El cálculo de espacio desperdiciado falló", expResult, result);
    }

    /**
     * Test of listarSoloActivos method.
     * Verifica que el método corra sin lanzar excepciones.
     */
    @Test
    public void testListarSoloActivos() {
        System.out.println("TEST 5: listarSoloActivos");
        
        instance.registrarContenedor(1, "Uno");
        instance.registrarContenedor(2, "Dos");
        
        try {
            // Como este método imprime en consola, solo validamos que no explote (Exception)
            instance.listarSoloActivos();
        } catch (Exception e) {
            fail("El método listarSoloActivos lanzó una excepción: " + e.getMessage());
        }
    }

    /**
     * Test of cerrar method.
     */
    @Test
    public void testCerrar() {
        System.out.println("cerrar");
        try {
            instance.cerrar();
            // Si llega aquí sin error, pasó
        } catch (Exception e) {
            fail("Error al cerrar el archivo");
        }
    }
    
    /**
     * TEST ANTI-TRAMPA: Valida que estén usando RandomAccessFile con huecos reales.
     * Si el alumno usa un ArrayList o escribe secuencialmente sin dejar espacios,
     * el archivo pesará menos y este test fallará.
     */
    @Test
    public void testValidarEstructuraFisica() {
        System.out.println("TEST 6: Validar Estructura Física del Archivo");
        
        // Guardamos solo el ID 100.
        // Si es Acceso Directo, el archivo debe tener espacio para los 99 anteriores.
        // Tamaño esperado: 100 registros * 44 bytes = 4400 bytes.
        instance.registrarContenedor(100, "Final");
        
        File archivoFisico = new File(RUTA_TEST);
        long pesoReal = archivoFisico.length();
        
        // Damos un margen de error de 0 bytes. Tiene que ser exacto.
        long pesoEsperado = 100 * 44; 
        
        assertEquals("El archivo no tiene la estructura de Acceso Directo (faltan los huecos vacíos). " +
                     "¿Estás usando seek() para calcular la posición?", 
                     pesoEsperado, pesoReal);
    }
}