package unab.cl.info.tarea_2.Prueba.frontend;

// Clases Importadas
import unab.cl.info.tarea_2.Prueba.backend.*;

// Librerías Importadas
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelPrincipal extends JFrame {
    private JPanel Contenedor;
    private JButton atras;
    private JButton adelante;
    private JButton entregarExamen;
    private JPanel Botonera;
    private JPanel Norte;
    private JPanel Este;
    private JPanel Centro;
    private JLabel labelEnunciado;
    private JLabel labelTiempo;
    private JLabel labelPreguntaNumero;
    private JLabel labelNivelTaxonomico;

    private Archivos backend;
    private ControladorExamen controlador;
    private ArrayList<JRadioButton> opcionesRadio;
    private ButtonGroup grupoRespuestas;
    private JTextArea areaJustificacion;
    private JScrollPane scrollJustificacion;
    private Timer timerExamen;
    private int tiempoRestante; // This now represents the time for the *current* question

    public PanelPrincipal() {
        initializeComponents();
        setupLayout();
        setupEventListeners();

        // Inicializar backend
        backend = new Archivos("archivos.txt"); // Cambiar por el nombre de tu archivo CSV

        if (backend.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar las preguntas del archivo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Sale de la aplicación si no hay preguntas
        }

        // Inicializar controlador
        controlador = new ControladorExamen(backend.getItems());

        // Configurar la ventana
        this.setTitle("Sistema de Examen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null); // Centra la ventana

        // Mostrar la primera pregunta
        mostrarPreguntaActual();
        // El temporizador ahora se inicia/reinicia dentro de mostrarPreguntaActual
        // cuando se carga una nueva pregunta.
    }

    private void initializeComponents() {
        Contenedor = new JPanel(new BorderLayout());

        // Panel Norte: Número de pregunta, enunciado y nivel taxonómico
        Norte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPreguntaNumero = new JLabel();
        labelEnunciado = new JLabel();
        labelNivelTaxonomico = new JLabel(); // Se movió aquí para estar más cerca del enunciado

        Norte.add(labelPreguntaNumero);
        Norte.add(Box.createHorizontalStrut(20)); // Espacio entre elementos
        Norte.add(new JLabel("Enunciado: "));
        Norte.add(labelEnunciado);
        Norte.add(Box.createHorizontalStrut(20));
        Norte.add(labelNivelTaxonomico);


        // Panel Este: Temporizador
        Este = new JPanel(new BorderLayout());
        labelTiempo = new JLabel("Tiempo: 00:00");
        labelTiempo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTiempo.setFont(new Font("Arial", Font.BOLD, 16)); // Fuente un poco más grande

        Este.add(labelTiempo, BorderLayout.NORTH); // Lo ponemos al norte del panel Este

        // Panel Centro: Opciones de respuesta y justificación
        Centro = new JPanel(new BorderLayout());
        Centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

        // Panel Botonera: Botones de navegación y entregar
        Botonera = new JPanel(new BorderLayout());
        atras = new JButton("Atrás");
        adelante = new JButton("Siguiente");
        entregarExamen = new JButton("Entregar Examen");
        entregarExamen.setBackground(new Color(200, 50, 50)); // Rojo más oscuro
        entregarExamen.setForeground(Color.WHITE);
        entregarExamen.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel botonesNavegacion = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonesNavegacion.add(atras);
        botonesNavegacion.add(adelante);

        Botonera.add(botonesNavegacion, BorderLayout.CENTER);
        Botonera.add(entregarExamen, BorderLayout.EAST);

        // Inicializar componentes para respuestas
        opcionesRadio = new ArrayList<>();
        grupoRespuestas = new ButtonGroup();

        // Área de justificación
        areaJustificacion = new JTextArea(4, 30);
        areaJustificacion.setLineWrap(true);
        areaJustificacion.setWrapStyleWord(true);
        areaJustificacion.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Borde sutil
        scrollJustificacion = new JScrollPane(areaJustificacion);
        scrollJustificacion.setBorder(BorderFactory.createTitledBorder("Justificación (requerida si responde 'Falso')"));
    }

    private void setupLayout() {
        Contenedor.add(Norte, BorderLayout.NORTH);
        Contenedor.add(Este, BorderLayout.EAST); // Este puede ser más pequeño si solo contiene el tiempo
        Contenedor.add(Centro, BorderLayout.CENTER);
        Contenedor.add(Botonera, BorderLayout.SOUTH);

        this.setContentPane(Contenedor);
    }

    private void setupEventListeners() {
        atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRespuestaActual();
                retroceder();
            }
        });

        adelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRespuestaActual();
                avanzar();
            }
        });

        entregarExamen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entregarExamen();
            }
        });
    }

    private void mostrarPreguntaActual() {
        Item preguntaActual = controlador.getPreguntaActual();
        if (preguntaActual == null) {
            // Esto no debería pasar si el controlador fue inicializado con preguntas
            JOptionPane.showMessageDialog(this, "Error: No se encontró la pregunta actual.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Detener el temporizador si está corriendo para la pregunta anterior
        if (timerExamen != null && timerExamen.isRunning()) {
            timerExamen.stop();
        }

        // Actualizar información de la pregunta
        labelPreguntaNumero.setText("Pregunta " + (controlador.getIndicePreguntaActual() + 1) +
                " de " + controlador.getTotalPreguntas());
        labelEnunciado.setText("<html><body style='width: 450px'>" + preguntaActual.getEnunciado() + "</body></html>");
        labelNivelTaxonomico.setText("Nivel: " + preguntaActual.getNivelTaxonomicoTexto());

        // Limpiar panel central
        Centro.removeAll();

        // Crear panel para opciones
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setBorder(BorderFactory.createTitledBorder("Opciones")); // Título para las opciones

        // Limpiar opciones anteriores del grupo
        opcionesRadio.clear();
        grupoRespuestas = new ButtonGroup(); // Crear un nuevo ButtonGroup para asegurar que solo una opción sea seleccionable

        // Crear botones de radio para las opciones
        for (int i = 0; i < preguntaActual.getOpciones().size(); i++) {
            JRadioButton radioButton = new JRadioButton((i + 1) + ". " + preguntaActual.getOpciones().get(i));
            radioButton.setActionCommand(String.valueOf(i + 1)); // El valor de acción es 1-based index
            opcionesRadio.add(radioButton);
            grupoRespuestas.add(radioButton);
            panelOpciones.add(radioButton);
            panelOpciones.add(Box.createVerticalStrut(8)); // Espacio entre radio buttons
        }

        Centro.add(panelOpciones, BorderLayout.NORTH); // Opciones arriba en el panel central

        // Si es verdadero/falso, añadir área de justificación
        if (preguntaActual.getTipoPregunta() == 2) {
            Centro.add(scrollJustificacion, BorderLayout.CENTER); // Justificación en el centro del panel central
            areaJustificacion.setText(controlador.getJustificacionActual()); // Cargar justificación guardada
            areaJustificacion.setEnabled(controlador.getRespuestaActual() == 2); // Habilitar solo si la respuesta guardada es Falso
            scrollJustificacion.setVisible(true);
        } else {
            areaJustificacion.setText(""); // Limpiar texto si no es V/F
            areaJustificacion.setEnabled(false); // Deshabilitar
            scrollJustificacion.setVisible(false); // Ocultar
        }

        // Añadir listener para habilitar/deshabilitar justificación al cambiar respuesta
        for (JRadioButton rb : opcionesRadio) {
            rb.addActionListener(e -> {
                if (preguntaActual.getTipoPregunta() == 2) {
                    // Habilitar justificación si la respuesta seleccionada es "Falso" (opción 2)
                    areaJustificacion.setEnabled(e.getActionCommand().equals("2"));
                }
            });
        }

        // Restaurar la respuesta anterior si existe
        Integer respuestaAnterior = controlador.getRespuestaActual();
        if (respuestaAnterior != null && respuestaAnterior != -1 && respuestaAnterior <= opcionesRadio.size()) {
            opcionesRadio.get(respuestaAnterior - 1).setSelected(true);
            // Si la respuesta restaurada es "Falso", habilitar la justificación
            if (preguntaActual.getTipoPregunta() == 2 && respuestaAnterior == 2) {
                areaJustificacion.setEnabled(true);
            }
        }


        // Actualizar estados de los botones
        atras.setEnabled(controlador.puedeRetroceder());
        adelante.setText(controlador.esUltimaPregunta() ? "Finalizar" : "Siguiente");

        // Configurar tiempo para esta pregunta
        try {
            tiempoRestante = Integer.parseInt(preguntaActual.getTiempo());
        } catch (NumberFormatException e) {
            tiempoRestante = 300; // 5 minutos por defecto si hay un error en el archivo
            System.err.println("Advertencia: Tiempo de pregunta inválido. Usando 300 segundos. " + e.getMessage());
        }
        actualizarDisplayTiempo(); // Actualizar display inmediatamente para la nueva pregunta
        iniciarTemporizador(); // Iniciar temporizador para la nueva pregunta

        // Repintar la interfaz
        Centro.revalidate();
        Centro.repaint();
        Contenedor.revalidate();
        Contenedor.repaint();
    }

    private void guardarRespuestaActual() {
        // Guardar respuesta seleccionada
        int selectedOption = -1;
        for (int i = 0; i < opcionesRadio.size(); i++) {
            if (opcionesRadio.get(i).isSelected()) {
                selectedOption = i + 1; // Las opciones son 1-based en el archivo
                break;
            }
        }
        controlador.guardarRespuesta(selectedOption);

        // Guardar justificación si es necesario (solo para preguntas Verdadero/Falso)
        Item preguntaActual = controlador.getPreguntaActual();
        if (preguntaActual != null && preguntaActual.getTipoPregunta() == 2) {
            // Si el usuario seleccionó "Falso" (opción 2 para V/F)
            if (selectedOption == 2) {
                controlador.guardarJustificacion(areaJustificacion.getText().trim());
            } else {
                controlador.guardarJustificacion(""); // Borrar justificación si no es "Falso"
            }
        } else {
            controlador.guardarJustificacion(""); // Borrar justificación para otros tipos de pregunta
        }
    }

    private void avanzar() {
        if (controlador.esUltimaPregunta()) {
            entregarExamen();
        } else {
            controlador.avanzar();
            mostrarPreguntaActual();
        }
    }

    private void retroceder() {
        if (controlador.puedeRetroceder()) {
            controlador.retroceder();
            mostrarPreguntaActual();
        }
    }

    private void entregarExamen() {
        guardarRespuestaActual(); // Asegurar que la respuesta de la última pregunta se guarde

        // Validación de justificación para respuestas "Falso" en V/F
        for (int i = 0; i < controlador.getTotalPreguntas(); i++) {
            Item question = backend.getItem(i);
            Integer userAnswer = controlador.getRespuestasUsuario().get(i);
            String userJustification = controlador.getJustificaciones().get(i);

            // Si es Verdadero/Falso y el usuario respondió "Falso" (opción 2)
            if (question.getTipoPregunta() == 2 && userAnswer == 2) {
                if (userJustification == null || userJustification.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "La pregunta " + (i + 1) + " (Verdadero/Falso con respuesta 'Falso') requiere justificación.",
                            "Justificación Requerida", JOptionPane.WARNING_MESSAGE);
                    controlador.irAPregunta(i); // Regresar a la pregunta que necesita justificación
                    mostrarPreguntaActual();
                    return; // Detener el proceso de envío
                }
            }
        }

        // Verificar si todas las preguntas han sido respondidas
        if (!controlador.todasLasPreguntasRespondidas()) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "No todas las preguntas han sido respondidas.\n¿Está seguro que desea entregar el examen?",
                    "Confirmar entrega",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (respuesta != JOptionPane.YES_OPTION) {
                return; // Si el usuario no confirma, no hacer nada
            }
        }

        // Detener el temporizador
        if (timerExamen != null) {
            timerExamen.stop();
        }

        // Calcular y mostrar estadísticas
        controlador.terminarExamen();
        mostrarResultados();

        // Cierra la aplicación después de mostrar los resultados
        System.exit(0);
    }

    private void mostrarResultados() {
        EstadisticasExamen stats = controlador.calcularEstadisticas();

        StringBuilder resultados = new StringBuilder();
        resultados.append("=== RESULTADOS DEL EXAMEN ===\n\n");
        resultados.append(String.format("Puntaje Total: %.1f%%\n", stats.getPorcentajeTotal()));
        resultados.append(String.format("Preguntas Correctas: %d de %d\n\n",
                stats.getCorrectasTotal(), stats.getTotalPreguntas()));

        resultados.append("Resultados por Nivel Taxonómico:\n");
        // Iterar a través de los niveles y mostrar resultados
        for (int i = 1; i <= 6; i++) {
            double porcentaje = stats.getPorcentajePorNivel(i);
            String nivel = obtenerNombreNivel(i);
            // Mostrar solo si hay preguntas para ese nivel o si quieres mostrar 0%
            resultados.append(String.format("- %s: %.1f%%\n", nivel, porcentaje));
        }

        resultados.append("\nResultados por Tipo de Pregunta:\n");
        resultados.append(String.format("- Alternativas: %.1f%%\n", stats.getPorcentajePorTipo(1)));
        resultados.append(String.format("- Verdadero/Falso: %.1f%%\n", stats.getPorcentajePorTipo(2)));

        JTextArea areaResultados = new JTextArea(resultados.toString());
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollResultados, "Resultados del Examen",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private String obtenerNombreNivel(int nivel) {
        switch (nivel) {
            case 1: return "Conocimiento";
            case 2: return "Comprensión";
            case 3: return "Aplicación";
            case 4: return "Análisis";
            case 5: return "Síntesis";
            case 6: return "Evaluación";
            default: return "No especificado";
        }
    }

    private void iniciarTemporizador() {
        if (timerExamen != null && timerExamen.isRunning()) {
            timerExamen.stop(); // Detener cualquier temporizador existente
        }
        timerExamen = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                actualizarDisplayTiempo();

                if (tiempoRestante <= 0) {
                    // Tiempo agotado para esta pregunta, avanzar automáticamente
                    guardarRespuestaActual();
                    if (controlador.esUltimaPregunta()) {
                        entregarExamen();
                    } else {
                        avanzar();
                    }
                }
            }
        });
        timerExamen.start();
    }

    private void actualizarDisplayTiempo() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        String tiempoTexto = String.format("Tiempo: %02d:%02d", minutos, segundos);
        labelTiempo.setText(tiempoTexto);

        // Cambiar color si queda poco tiempo
        if (tiempoRestante <= 30) {
            labelTiempo.setForeground(Color.RED);
        } else if (tiempoRestante <= 60) {
            labelTiempo.setForeground(Color.ORANGE);
        } else {
            labelTiempo.setForeground(Color.BLACK);
        }
    }

    public static void main(String[] args) {
        // Asegurar que las actualizaciones de la GUI se hagan en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PanelPrincipal().setVisible(true);
            }
        });
    }
}