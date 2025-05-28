package unab.cl.info.tarea_2.Prueba.backend;

import java.util.ArrayList;

public class VerdaderoFalso extends Item {
    private String justificacion; // Para almacenar la justificación si es falso

    public VerdaderoFalso(String enunciado, int nivelTaxonomico, int opcionCorrecta) {
        super();
        this.enunciado = enunciado;
        this.nivelTaxonomico = nivelTaxonomico;
        this.tipoPregunta = 2; // tipo 2 = verdadero/falso
        this.opcionCorrecta = opcionCorrecta;
        this.opciones = new ArrayList<>();
        this.opciones.add("Verdadero");
        this.opciones.add("Falso");
        this.cantidadAlternativas = 2;
        this.tiempo = "300"; // tiempo por defecto
        this.justificacion = "";
    }

    public VerdaderoFalso(String enunciado, int nivelTaxonomico, int tipoPregunta,
                          int cantidadAlternativas, String alternativas, int opcionCorrecta, String tiempo) {
        super(enunciado, nivelTaxonomico, tipoPregunta, cantidadAlternativas,
                alternativas, opcionCorrecta, tiempo);
        this.justificacion = "";
    }

    public boolean validarRespuesta(int respuestaUsuario) {
        return respuestaUsuario == this.getOpcionCorrecta();
    }

    public String obtenerRespuestaCorrecta() {
        return getOpcionCorrecta() == 1 ? "Verdadero" : "Falso";
    }

    public boolean esVerdadero() {
        return getOpcionCorrecta() == 1;
    }

    public boolean esFalso() {
        return getOpcionCorrecta() == 2;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public boolean requiereJustificacion(int respuestaUsuario) {
        return respuestaUsuario == 2; // Si el usuario responde "Falso"
    }
}