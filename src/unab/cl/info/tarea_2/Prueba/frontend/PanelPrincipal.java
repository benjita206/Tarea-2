package unab.cl.info.tarea_2.Prueba.frontend;

// Clases Importadas
import unab.cl.info.tarea_2.Prueba.backend.Archivos;
import unab.cl.info.tarea_2.Prueba.backend.Item;

// Librerias Importadas
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelPrincipal extends JFrame{
    private JPanel Contenedor;
    private JButton atras;
    private JButton adelante;
    private JPanel Botonera;
    private JPanel Norte;
    private JPanel Este;
    private JPanel Centro;
    private Archivos backend;

    public PanelPrincipal(){
        this.setContentPane(this.Contenedor);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        int i = 1;
        for(Item I:this.backend.getItems()){
            FlowLayout flow =new FlowLayout();
            JPanel registroPanel = new JPanel(flow);
            registroPanel.add(new JLabel("Enunciado: " + I.getEnunciado()));
            registroPanel.add(new JLabel("Nivel Taxonomico" + I.getNivelTaxonomico()));
            registroPanel.add(new JLabel("Tipo Pregunta: "+ I.getTipoPregunta()));
            registroPanel.add(new JLabel("Opciones: "+ I.getOpciones()));
            registroPanel.add(new JLabel("Opcion Correcta: "+ I.getOpcionCorrecta()));
            registroPanel.add(new JLabel("Tiempo: "+ I.getTiempo()));

            // Agregar la tarjeta
            this.Centro.add(registroPanel,"Card_"+i);
            i++;
        }

        this.adelante.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout)Centro.getLayout()).next(Centro);
            }
        });

        this.atras.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout)Centro.getLayout()).previous(Centro);
            }
        });
    }


}
