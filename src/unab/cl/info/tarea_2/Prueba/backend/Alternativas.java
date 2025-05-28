package unab.cl.info.tarea_2.Prueba.backend;

import java.util.ArrayList;

public class Alternativas extends Item {

    public Alternativas(String enunciado, int nivelTaxonomico,
                        ArrayList<String> opciones, int opcionCorrecta) {
        super(enunciado, nivelTaxonomico, 1, opciones, opcionCorrecta); // tipo 1 = alternativas
    }

    public Alternativas(String enunciado, int nivelTaxonomico, int tipoPregunta,
                        int cantidadAlternativas, String alternativas, int opcionCorrecta, String tiempo) {
        super(enunciado, nivelTaxonomico, tipoPregunta, cantidadAlternativas,
                alternativas, opcionCorrecta, tiempo);
    }

    public boolean validarRespuesta(int respuestaUsuario) {
        return respuestaUsuario == this.getOpcionCorrecta();
    }

    public String obtenerRespuestaCorrecta() {
        if (getOpcionCorrecta() > 0 && getOpcionCorrecta() <= getOpciones().size()) {
            return getOpciones().get(getOpcionCorrecta() - 1);
        }
        return "No definida";
    }
}