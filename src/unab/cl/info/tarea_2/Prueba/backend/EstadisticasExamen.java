package unab.cl.info.tarea_2.Prueba.backend;

import java.util.HashMap;
import java.util.Map;

public class EstadisticasExamen {
    private double porcentajeTotal;
    private int correctasTotal;
    private int totalPreguntas;
    private Map<Integer, Double> porcentajesPorNivel;
    private Map<Integer, Double> porcentajesPorTipo;

    public EstadisticasExamen() {
        this.porcentajesPorNivel = new HashMap<>();
        this.porcentajesPorTipo = new HashMap<>();
    }

    // Getters y Setters
    public double getPorcentajeTotal() {
        return porcentajeTotal;
    }

    public void setPorcentajeTotal(double porcentajeTotal) {
        this.porcentajeTotal = porcentajeTotal;
    }

    public int getCorrectasTotal() {
        return correctasTotal;
    }

    public void setCorrectasTotal(int correctasTotal) {
        this.correctasTotal = correctasTotal;
    }

    public int getTotalPreguntas() {
        return totalPreguntas;
    }

    public void setTotalPreguntas(int totalPreguntas) {
        this.totalPreguntas = totalPreguntas;
    }

    public void setPorcentajePorNivel(int nivel, double porcentaje) {
        porcentajesPorNivel.put(nivel, porcentaje);
    }

    public double getPorcentajePorNivel(int nivel) {
        return porcentajesPorNivel.getOrDefault(nivel, 0.0);
    }

    public void setPorcentajePorTipo(int tipo, double porcentaje) {
        porcentajesPorTipo.put(tipo, porcentaje);
    }

    public double getPorcentajePorTipo(int tipo) {
        return porcentajesPorTipo.getOrDefault(tipo, 0.0);
    }

    public Map<Integer, Double> getPorcentajesPorNivel() {
        return porcentajesPorNivel;
    }

    public Map<Integer, Double> getPorcentajesPorTipo() {
        return porcentajesPorTipo;
    }
}
