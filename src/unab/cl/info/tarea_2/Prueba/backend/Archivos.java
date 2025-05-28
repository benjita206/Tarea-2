package unab.cl.info.tarea_2.Prueba.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Archivos {
    private ArrayList<Item> items;

    public Archivos() {
        items = new ArrayList<>();
        this.abrirArchivo();
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public void abrirArchivo() {
        try{
            BufferedReader br = new BufferedReader(new FileReader("archivos.txt"));
            String linea = br.readLine();
            while (linea != null) {
                String[] datos = linea.split(";");
                Item nuevoItem = new Item(datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), datos[4], Integer.parseInt(datos[5]), datos[6]);
                this.items.add(nuevoItem);
                linea = br.readLine();


            }
        }
        catch(Exception e){
            System.out.println("Error al abrir el archivo.\n");
        }

    }
}
