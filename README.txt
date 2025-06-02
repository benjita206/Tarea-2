# Sistema de Examen (Aplicación de Escritorio Java Swing)

Este proyecto implementa un sistema básico de examen interactivo utilizando Java Swing. Permite cargar preguntas desde un archivo CSV, presenta las preguntas una por una, gestiona las respuestas y justificaciones (para preguntas Verdadero/Falso), y calcula estadísticas de rendimiento al finalizar el examen. El examen cuenta con un temporizador global para todas las preguntas.

## Alcances del Proyecto

* Carga de Ítems: El sistema carga las preguntas desde un archivo CSV predefinido al inicio.
* Tipos de Pregunta: Soporta dos tipos de preguntas:
    * Alternativas: Múltiples opciones de respuesta, donde solo una es correcta.
    * Verdadero/Falso: Opciones predefinidas "Verdadero" y "Falso".
* Justificación: Las preguntas de Verdadero/Falso requieren una justificación obligatoria si la respuesta seleccionada es "Falso". El sistema valida esto antes de la entrega final.
* Navegación: Permite avanzar y retroceder entre las preguntas.
* Guardado de Respuestas: Las respuestas y justificaciones del usuario se guardan automáticamente al navegar entre preguntas.
* Temporizador Global: Un temporizador único y acumulativo para todo el examen, que se detiene al entregar el examen o al agotarse el tiempo, momento en el cual el examen se entrega automáticamente.
* Estadísticas de Rendimiento: Al finalizar el examen, se muestran estadísticas del puntaje total, preguntas correctas/incorrectas y rendimiento por nivel taxonómico y tipo de pregunta en porcentaje.
* Interfaz Gráfica: Desarrollado con Java Swing para una interfaz de usuario de escritorio.

## Supuestos Incorporados

* Formato de Archivo Fijo: Se asume que el archivo de ítems ("archivos.csv") siempre seguirá el formato y la estructura especificada a continuación. Cualquier desviación podría causar errores de carga o comportamiento inesperado.
* Existencia del Archivo: Se asume que el archivo 'archivos.csv' existe y es accesible en el directorio de ejecución del programa. Si el archivo no se encuentra o está vacío, la aplicación no se iniciará y mostrará un mensaje de error.
* Validez de Datos: Se asume que los datos dentro del archivo de ítems son válidos (ej. niveles taxonómicos entre 1 y 6, opciones correctas válidas para el número de alternativas). Errores en el formato de números (ej. tiempo no numérico) son manejados con un valor por defecto.
* Indización de Opciones: Las opciones de respuesta y la opción correcta se consideran indexadas a partir de '1' (es decir, la primera opción es '1', la segunda es '2', etc.).
* Comportamiento de Verdadero/Falso: Para las preguntas Verdadero/Falso, la opción '1' corresponde a "Verdadero" y la opción '2' a "Falso". La justificación es obligatoria solo si se selecciona la opción '2' ("Falso").
* Una Sola Respuesta: El sistema asume que solo una opción puede ser seleccionada por pregunta (implementado con 'ButtonGroup' para 'JRadioButton').
* No Persistencia Avanzada: Las respuestas del usuario y las estadísticas no se guardan permanentemente después de cerrar la aplicación.

## Instrucciones de Ejecución

Para ejecutar el sistema de examen, sigue los siguientes pasos:

1.  Requisitos:
    * Java Development Kit (JDK) 23.
    * Un entorno de desarrollo integrado (IDE) como IntelliJ IDEA, Eclipse o VS Code con soporte para Java.

2.  Configuración del Proyecto:
    * Clona o descarga el repositorio del proyecto.
    * Abre el proyecto en tu IDE preferido. Asegúrate de que todas las clases ('PanelPrincipal.java', 'ControladorExamen.java', 'Archivos.java', 'Item.java', 'Alternativas.java', 'VerdaderoFalso.java', 'EstadisticasExamen.java') estén correctamente ubicadas en sus respectivos paquetes ('frontend' y 'backend' dentro de 'unab.cl.info.tarea_2.Prueba').

3.  Carga del Archivo de Ítems ("archivos.csv"):
    * Crear el archivo: En la raíz de tu proyecto (al mismo nivel que la carpeta 'src'), crea un nuevo archivo llamado 'archivos.csv'.
    * Contenido: Copia y pega el contenido de ejemplo proporcionado en la sección "Especificación del Archivo de Ítems" en este archivo.

4.  Ejecución:
    * Desde tu IDE, navega a la clase 'PanelPrincipal.java'.
    * Ejecuta el método 'main' de 'PanelPrincipal.java'. Esto iniciará la interfaz gráfica del examen.

## Especificación del Archivo de Ítems ('archivos.csv')

El archivo de ítems debe ser un archivo de texto plano ('.csv') donde cada línea representa una pregunta individual. Los campos dentro de cada línea deben estar separados por un punto y coma (';').

Descripción de los Campos:

1.  'enunciado' (String):
    * El texto completo de la pregunta.
    * Importante: No debe contener el carácter ';' ya que es el delimitador principal y ',' ya que es el delimitador de las alternativas.

2.  'nivelTaxonomico' (Integer):
    * Un número entero que representa el nivel taxonómico de la pregunta.
    * Valores: '1' a '6'.
        * '1': Conocimiento
        * '2': Comprensión
        * '3': Aplicación
        * '4': Análisis
        * '5': Síntesis
        * '6': Evaluación

3.  'tipoPregunta' (Integer):
    * Un número entero que indica el tipo de pregunta.
    * Valores:
        * '1': Pregunta de Alternativas (selección múltiple).
        * '2': Pregunta de Verdadero/Falso.

4.  'cantidadAlternativas' (Integer):
    * El número total de opciones de respuesta para la pregunta.
    * Para 'tipoPregunta = 1' (Alternativas): Debe coincidir con el número de opciones listadas en el campo 'alternativas'.
    * Para 'tipoPregunta = 2' (Verdadero/Falso): Este valor siempre debe ser '2' (correspondiente a "Verdadero" y "Falso").

5.  'alternativas' (String):
    * Una cadena de texto que contiene todas las opciones de respuesta, separadas por comas (',').
    * Ejemplo para Alternativas: 'Opción A,Opción B,Opción C,Opción D'
    * Ejemplo para Verdadero/Falso: 'Verdadero,Falso'.
    * Importante: No debe contener el carácter ';' (punto y coma).

6.  'opcionCorrecta' (Integer):
    * El número de la opción correcta, basado en la posición de las alternativas (1-indexed).
    * Ejemplo para Alternativas: Si 'Opción C' es la correcta y está en tercer lugar, el valor sería '3'.
    * Ejemplo para Verdadero/Falso:
        * '1': Si la respuesta correcta es "Verdadero".
        * '2': Si la respuesta correcta es "Falso".

7.  'tiempo' (String):
    * El tiempo asignado a esta pregunta en segundos. Es un String en el archivo, pero el programa lo convierte a entero.
    * Ejemplo: '60' para 1 minuto, '120' para 2 minutos, '300' para 5 minutos.

