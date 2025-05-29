package unab.cl.info.tarea_2.Prueba.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControladorExamen {
    private ArrayList<Item> preguntas;
    private ArrayList<Integer> respuestasUsuario;
    private ArrayList<String> justificaciones;
    private int preguntaActual;
    private boolean examenTerminado;

    public ControladorExamen(ArrayList<Item> preguntas) {
        this.preguntas = preguntas;
        this.respuestasUsuario = new ArrayList<>();
        this.justificaciones = new ArrayList<>();
        this.preguntaActual = 0;
        this.examenTerminado = false;

        // Inicializar arrays con valores por defecto
        for (int i = 0; i < preguntas.size(); i++) {
            respuestasUsuario.add(-1); // -1 indica sin respuesta
            justificaciones.add("");
        }
    }

    public Item getPreguntaActual() {
        if (preguntaActual >= 0 && preguntaActual < preguntas.size()) {
            return preguntas.get(preguntaActual);
        }
        return null;
    }

    public int getIndicePreguntaActual() {
        return preguntaActual;
    }

    public void MostrarPreguntaActual() {
        System.out.println(preguntaActual);
    }

    public int getTotalPreguntas() {
        return preguntas.size();
    }

    public boolean puedeRetroceder() {
        return preguntaActual > 0;
    }

    public boolean puedeAvanzar() {
        return preguntaActual < preguntas.size() - 1;
    }

    public boolean esUltimaPregunta() {
        return preguntaActual == preguntas.size() - 1;
    }

    public boolean esPrimeraPregunta() {
        return preguntaActual == 0;
    }

    public void avanzar() {
        if (puedeAvanzar()) {
            preguntaActual++;
        }
    }

    public void retroceder() {
        if (puedeRetroceder()) {
            preguntaActual--;
        }
    }

    public void irAPregunta(int indice) {
        if (indice >= 0 && indice < preguntas.size()) {
            preguntaActual = indice;
        }
    }

    public void guardarRespuesta(int respuesta) {
        if (preguntaActual >= 0 && preguntaActual < respuestasUsuario.size()) {
            respuestasUsuario.set(preguntaActual, respuesta);
        }
    }

    public void guardarJustificacion(String justificacion) {
        if (preguntaActual >= 0 && preguntaActual < justificaciones.size()) {
            justificaciones.set(preguntaActual, justificacion);
        }
    }

    public Integer getRespuestaActual() {
        if (preguntaActual >= 0 && preguntaActual < respuestasUsuario.size()) {
            return respuestasUsuario.get(preguntaActual);
        }
        return -1;
    }

    public String getJustificacionActual() {
        if (preguntaActual >= 0 && preguntaActual < justificaciones.size()) {
            return justificaciones.get(preguntaActual);
        }
        return "";
    }

    public boolean todasLasPreguntasRespondidas() {
        for (Integer respuesta : respuestasUsuario) {
            if (respuesta == -1) {
                return false;
            }
        }
        return true;
    }

    public void terminarExamen() {
        this.examenTerminado = true;
    }

    public boolean isExamenTerminado() {
        return examenTerminado;
    }

    public EstadisticasExamen calcularEstadisticas() {
        EstadisticasExamen stats = new EstadisticasExamen();

        int correctasTotal = 0;
        Map<Integer, Integer> correctasPorNivel = new HashMap<>();
        Map<Integer, Integer> totalPorNivel = new HashMap<>();
        Map<Integer, Integer> correctasPorTipo = new HashMap<>();
        Map<Integer, Integer> totalPorTipo = new HashMap<>();

        // Inicializar contadores
        for (int i = 1; i <= 6; i++) {
            correctasPorNivel.put(i, 0);
            totalPorNivel.put(i, 0);
        }
        for (int i = 1; i <= 2; i++) {
            correctasPorTipo.put(i, 0);
            totalPorTipo.put(i, 0);
        }

        // Calcular estadísticas
        for (int i = 0; i < preguntas.size(); i++) {
            Item pregunta = preguntas.get(i);
            Integer respuesta = respuestasUsuario.get(i);

            if (respuesta != -1) {
                boolean esCorrecta = respuesta == pregunta.getOpcionCorrecta();

                if (esCorrecta) {
                    correctasTotal++;
                    correctasPorNivel.put(pregunta.getNivelTaxonomico(),
                            correctasPorNivel.get(pregunta.getNivelTaxonomico()) + 1);
                    correctasPorTipo.put(pregunta.getTipoPregunta(),
                            correctasPorTipo.get(pregunta.getTipoPregunta()) + 1);
                }

                totalPorNivel.put(pregunta.getNivelTaxonomico(),
                        totalPorNivel.get(pregunta.getNivelTaxonomico()) + 1);
                totalPorTipo.put(pregunta.getTipoPregunta(),
                        totalPorTipo.get(pregunta.getTipoPregunta()) + 1);
            }
        }

        // Establecer resultados
        stats.setPorcentajeTotal((double) correctasTotal / preguntas.size() * 100);
        stats.setCorrectasTotal(correctasTotal);
        stats.setTotalPreguntas(preguntas.size());

        // Porcentajes por nivel taxonómico
        for (int i = 1; i <= 6; i++) {
            if (totalPorNivel.get(i) > 0) {
                double porcentaje = (double) correctasPorNivel.get(i) / totalPorNivel.get(i) * 100;
                stats.setPorcentajePorNivel(i, porcentaje);
            }
        }

        // Porcentajes por tipo
        for (int i = 1; i <= 2; i++) {
            if (totalPorTipo.get(i) > 0) {
                double porcentaje = (double) correctasPorTipo.get(i) / totalPorTipo.get(i) * 100;
                stats.setPorcentajePorTipo(i, porcentaje);
            }
        }

        return stats;
    }

    public ArrayList<Integer> getRespuestasUsuario() {
        return respuestasUsuario;
    }

    public ArrayList<String> getJustificaciones() {
        return justificaciones;
    }
}

