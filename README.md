# 📋 EXAMEN PRÁCTICO CALIFICADO 02 - ADA

## ⚠️ REQUISITOS MÍNIMOS OBLIGATORIOS

### 🔧 Requisitos Técnicos
- **JDK 24**
- **JUnit 4**

> ⚠️ **PENALIZACIÓN:** Se restarán **4 puntos** en caso de no cumplir con estos requisitos.

---

### 📝 Requisitos de Control de Versiones

Se requiere **mínimo 5 commits con push** (uno por cada método implementado).

#### Requisitos de cada commit:
- Mensaje descriptivo explicando qué se implementó
- Nombre completo del estudiante como committer
- Un método por commit

> ⚠️ **PENALIZACIÓN:** Se restarán **2 puntos por cada pregunta afectada** en caso de no cumplir con los commits correspondientes.

---

### 🚫 Restricciones de Modificación

Solo debe modificar el archivo `GestorContenedores.java`

> ⚠️ **PENALIZACIÓN:** Se bajará puntaje según lo que haya modificado.

---

### 🎥 Supervisión

La cámara debe permanecer encendida durante todo el examen.

> ⚠️ **ADVERTENCIA:** La escuela podría anular el examen (nota: 0) cuando haga auditorías si no cumple este requisito.

---

### ✅ Verificación de Avance

Puede ejecutar los tests en cualquier momento para verificar su progreso.

---

## 📦 CASO: Sistema de Gestión de Contenedores Marítimos

Una empresa de logística marítima necesita gestionar el inventario de contenedores en sus terminales portuarias. El sistema implementa búsqueda por clave hash utilizando archivos de acceso directo con RandomAccessFile.

### Características del Sistema

- Cada contenedor tiene un ID único y un tipo
- Se usa acceso directo para búsquedas rápidas (O(1))
- Los registros tienen tamaño fijo de 44 bytes
- Permite actualizaciones in-situ sin reescribir todo el archivo

---

## 📚 PREGUNTAS DEL EXAMEN

### Pregunta 1: Registrar Contenedor **[4 puntos]**

Implementar el registro de un nuevo contenedor en el archivo. El método debe calcular la posición exacta usando el ID, realizar un salto directo a esa posición, y escribir el ID seguido del tipo formateado a exactamente 20 caracteres.

---

### Pregunta 2: Buscar Contenedor **[5 puntos]**

Implementar la búsqueda de un contenedor por su ID mediante acceso directo. El método debe calcular la posición matemáticamente, saltar directamente a ella, validar el ID leído y retornar el tipo si coincide, o null si no existe.

---

### Pregunta 3: Marcar Contenedor como Dañado **[3 puntos]**

Implementar la actualización del estado de un contenedor a "DAÑADO" mediante modificación in-situ. El método debe saltar directamente al campo tipo y sobrescribirlo sin modificar el ID.

---

### Pregunta 4: Calcular Espacio Desperdiciado **[5 puntos]**

Implementar un reporte que calcule cuántos bytes están desperdiciados por registros vacíos. El método debe recorrer el archivo, identificar registros con ID = 0 y sumar 44 bytes por cada uno.

---

### Pregunta 5: Listar Contenedores Activos **[3 puntos]**

Implementar un listado que imprima en consola únicamente los contenedores activos (ID diferente de 0). El método debe recorrer el archivo y mostrar el ID y tipo de cada contenedor activo.

---

## 🏆 CALIFICACIÓN

| Pregunta | Método | Puntos |
|----------|--------|--------|
| 1 | registrarContenedor() | 4 |
| 2 | buscarContenedor() | 5 |
| 3 | marcarComoDanado() | 3 |
| 4 | calcularEspacioDesperdiciado() | 5 |
| 5 | listarSoloActivos() | 3 |
| **TOTAL** | | **20** |

### Test Crítico de Arquitectura

Existe un test adicional que valida el uso correcto de RandomAccessFile:
- Si pasa: Los puntos se mantienen
- Si falla: Penalización del 100% (nota final = 0)

