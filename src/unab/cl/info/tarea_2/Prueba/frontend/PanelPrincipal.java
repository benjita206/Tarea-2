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
    private int tiempoRestante;

    public PanelPrincipal() {
        initializeComponents();
        setupLayout();
        setupEventListeners();

        // Inicializar backend
        backend = new Archivos("archivos.txt"); // Cambiar por el nombre de tu archivo CSV

        if (backend.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar las preguntas del archivo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Inicializar controlador
        controlador = new ControladorExamen(backend.getItems());

        // Configurar la ventana
        this.setTitle("Sistema de Examen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Mostrar la primera pregunta
        mostrarPreguntaActual();
        iniciarTemporizador();
    }


    private void initializeComponents() {
        Contenedor = new JPanel(new BorderLayout());

        // Panel Norte
        Norte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPreguntaNumero = new JLabel();
        labelEnunciado = new JLabel();
        Norte.add(labelPreguntaNumero);
        Norte.add(Box.createHorizontalStrut(20));
        Norte.add(new JLabel("Enunciado: "));
        Norte.add(labelEnunciado);

        // Panel Este
        Este = new JPanel(new BorderLayout());
        labelTiempo = new JLabel("Tiempo: 00:00");
        labelTiempo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTiempo.setFont(new Font("Arial", Font.BOLD, 14));
        Este.add(labelTiempo, BorderLayout.CENTER);

        labelNivelTaxonomico = new JLabel();
        labelNivelTaxonomico.setHorizontalAlignment(SwingConstants.CENTER);
        Este.add(labelNivelTaxonomico, BorderLayout.SOUTH);

        // Panel Centro
        Centro = new JPanel(new BorderLayout());

        // Panel Botonera
        Botonera = new JPanel(new BorderLayout());
        atras = new JButton("Atrás");
        adelante = new JButton("Siguiente");
        entregarExamen = new JButton("Entregar Examen");
        entregarExamen.setBackground(Color.RED);
        entregarExamen.setForeground(Color.WHITE);

        JPanel botonesNavegacion = new JPanel(new FlowLayout());
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
        scrollJustificacion = new JScrollPane(areaJustificacion);
        scrollJustificacion.setBorder(BorderFactory.createTitledBorder("Justificación (requerida para respuesta 'Falso')"));
    }

    private void setupLayout() {
        Contenedor.add(Norte, BorderLayout.NORTH);
        Contenedor.add(Este, BorderLayout.EAST);
        Contenedor.add(Centro, BorderLayout.CENTER);
        Contenedor.add(Botonera, BorderLayout.SOUTH);

        this.setContentPane(Contenedor);
    }

    private void setupEventListeners() {
        atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRespuestaActual();
                retroceder;

            }
