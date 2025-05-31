// Clases Importada
import unab.cl.info.tarea_2.Prueba.backend.Archivos;
import unab.cl.info.tarea_2.Prueba.frontend.PanelPrincipal;

public class Main {
    public static void main(String[] args) {
        Archivos backend = new Archivos("prueba.csv");
        PanelPrincipal frontend = new PanelPrincipal(backend);
        frontend.setVisible(true);
    }
}