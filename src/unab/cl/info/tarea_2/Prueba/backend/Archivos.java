package unab.cl.info.tarea_2.Prueba.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Archivos {
    private ArrayList<Item> items;

    public Archivos(String nombreArchivo) {
        items = new ArrayList<>();
        this.abrirArchivo(nombreArchivo);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void abrirArchivo(String nombreArchivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            String linea = br.readLine();

            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(";");

                    if (datos.length >= 7) {
                        String enunciado = datos[0];
                        int nivelTaxonomico = Integer.parseInt(datos[1]);
                        int tipoPregunta = Integer.parseInt(datos[2]);
                        int cantidadAlternativas = Integer.parseInt(datos[3]);
                        String alternativas = datos[4];
                        int opcionCorrecta = Integer.parseInt(datos[5]);
                        String tiempo = datos[6];

                        Item nuevoItem;

                        // Crear el tipo de item apropiado
                        if (tipoPregunta == 1) {
                            // Alternativas
                            nuevoItem = new Alternativas(enunciado, nivelTaxonomico, tipoPregunta,
                                    cantidadAlternativas, alternativas, opcionCorrecta, tiempo);
                        } 
                        
                        else if (tipoPregunta == 2) {
                            // Verdadero/Falso
                            nuevoItem = new VerdaderoFalso(enunciado, nivelTaxonomico, tipoPregunta,
                                    cantidadAlternativas, alternativas, opcionCorrecta, tiempo);
                        } 
                        
                        else {
                            // Item genérico
                            nuevoItem = new Item(enunciado, nivelTaxonomico, tipoPregunta,
                                    cantidadAlternativas, alternativas, opcionCorrecta, tiempo);
                        }

                        this.items.add(nuevoItem);
                    }
                }
                linea = br.readLine();
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Error al abrir el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getTotalItems() {
        return items.size();
    }

    public Item getItem(int indice) {
        if (indice >= 0 && indice < items.size()) {
            return items.get(indice);
        }
        return null;
    }
}
