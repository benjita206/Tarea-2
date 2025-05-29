import unab.cl.info.tarea_2.Prueba.backend.Archivos;
import unab.cl.info.tarea_2.Prueba.frontend.PanelPrincipal;

public class Main {
    public static void main(String[] args) {
        Archivos archivos = new Archivos();
        PanelPrincipal frontend = new PanelPrincipal(archivos);
        frontend.setVisible(true);
    }
}