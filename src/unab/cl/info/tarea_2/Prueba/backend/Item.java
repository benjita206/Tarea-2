package unab.cl.info.tarea_2.Prueba.backend;

import java.util.ArrayList;

public class Item {
    private String enunciado;
    private int nivelTaxonomico;
    private int tipoPregunta; // 1 = Alternativas, 2 = Verdadero/Falso
    private int cantidadAlternativas;
    private String alternativas; // String con alternativas separadas por el delimitador
    private int opcionCorrecta;
    private String tiempo;
    private ArrayList<String> opciones;

    // Constructor que coincide con el llamado desde Archivos
    public Item(String enunciado, int nivelTaxonomico, int tipoPregunta,
                int cantidadAlternativas, String alternativas, int opcionCorrecta, String tiempo) {
        this.enunciado = enunciado;
        this.nivelTaxonomico = nivelTaxonomico;
        this.tipoPregunta = tipoPregunta;
        this.cantidadAlternativas = cantidadAlternativas;
        this.alternativas = alternativas;
        this.opcionCorrecta = opcionCorrecta;
        this.tiempo = tiempo;
        this.opciones = new ArrayList<>();

        // Procesar alternativas
        if (alternativas != null && !alternativas.isEmpty()) {
            String[] altArray = alternativas.split(","); // Detectara las alternatibas por el delimitador ','
            for (String alt : altArray) {
                this.opciones.add(alt.trim());
            }
        }

        // Si es verdadero/falso y no hay alternativas específicas, agregar V/F
        if (tipoPregunta == 2 && this.opciones.isEmpty()) {
            this.opciones.add("Verdadero");
            this.opciones.add("Falso");
        }
    }

    // Constructor alternativo
    public Item(String enunciado, int nivelTaxonomico, int tipoPregunta,
                ArrayList<String> opciones, int opcionCorrecta) {
        this.enunciado = enunciado;
        this.nivelTaxonomico = nivelTaxonomico;
        this.tipoPregunta = tipoPregunta;
        this.opciones = new ArrayList<>(opciones);
        this.opcionCorrecta = opcionCorrecta;
        this.cantidadAlternativas = opciones.size();
        this.tiempo = "300"; // tiempo por defecto
    }

    // Constructor vacío
    public Item() {
        this.opciones = new ArrayList<>();
    }

    // Getters y Setters
    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public int getNivelTaxonomico() {
        return nivelTaxonomico;
    }

    public void setNivelTaxonomico(int nivelTaxonomico) {
        this.nivelTaxonomico = nivelTaxonomico;
    }

    public int getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(int tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public int getOpcionCorrecta() {
        return opcionCorrecta;
    }

    public void setOpcionCorrecta(int opcionCorrecta) {
        this.opcionCorrecta = opcionCorrecta;
    }

    public ArrayList<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(ArrayList<String> opciones) {
        this.opciones = opciones;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public int getCantidadAlternativas() {
        return cantidadAlternativas;
    }

    public void setCantidadAlternativas(int cantidadAlternativas) {
        this.cantidadAlternativas = cantidadAlternativas;
    }

    public String getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(String alternativas) {
        this.alternativas = alternativas;
    }

    public String getTipoPreguntatexto() {
        return tipoPregunta == 1 ? "Alternativas" : "Verdadero/Falso";
    }

    public String getNivelTaxonomicoTexto() {
        switch (nivelTaxonomico) {
            case 1: return "Conocimiento";
            case 2: return "Comprensión";
            case 3: return "Aplicación";
            case 4: return "Análisis";
            case 5: return "Síntesis";
            case 6: return "Evaluación";
            default: return "No especificado";
        }
    }
}
