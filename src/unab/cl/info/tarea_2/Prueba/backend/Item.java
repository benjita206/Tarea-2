package unab.cl.info.tarea_2.Prueba.backend;

import java.util.ArrayList;

public class Item {
    private String enunciado;
    private int nivelTaxonomico;
    private int tipoPregunta;
    private ArrayList<String> opciones;
    private int opcionCorrecta;

    public Item(String enunciado, int nivelTaxonomico, int tipoPregunta, ArrayList<String> opciones, int opcionCorrecta) {
        this.enunciado = enunciado;
        this.nivelTaxonomico = nivelTaxonomico;
        this.tipoPregunta = tipoPregunta;
        this.opciones = new ArrayList<>();
        this.opcionCorrecta = opcionCorrecta;
    }

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


}
