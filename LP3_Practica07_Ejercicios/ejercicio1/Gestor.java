package ejercicio1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** Administra los personajes y los mantiene sincronizados con un archivo de texto. */
public class Gestor {
    private final List<Personaje> personajes = new ArrayList<>();
    private final String archivo;

    public Gestor(String archivo) {
        this.archivo = archivo;
        cargar();
    }

    /** Lee el archivo; si no existe, crea los personajes iniciales. */
    private void cargar() {
        File f = new File(archivo);
        if (!f.exists()) {
            personajes.add(new Personaje("Caballero", 4, 2, 4, 2));
            personajes.add(new Personaje("Guerrero", 2, 4, 2, 4));
            personajes.add(new Personaje("Arquero", 2, 4, 1, 8));
            guardar();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                try {
                    String[] p = linea.split(";");
                    personajes.add(new Personaje(p[0], Integer.parseInt(p[1].trim()),
                            Integer.parseInt(p[2].trim()), Integer.parseInt(p[3].trim()),
                            Integer.parseInt(p[4].trim())));
                } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                    // IllegalArgumentException cubre también NumberFormatException
                    System.out.println("Línea inválida omitida: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /** Escribe todos los personajes en el archivo (se llama tras cada cambio). */
    private void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Personaje p : personajes) pw.println(p.aLinea());
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    private Personaje buscar(String nombre) {
        for (Personaje p : personajes)
            if (p.getNombre().equalsIgnoreCase(nombre.trim())) return p;
        return null;
    }

    /** Añade un personaje; devuelve false si ya existe uno con ese nombre. */
    public boolean anadir(Personaje nuevo) {
        if (buscar(nuevo.getNombre()) != null) return false;
        personajes.add(nuevo);
        guardar();
        return true;
    }

    public void mostrar() {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes registrados.");
            return;
        }
        for (Personaje p : personajes) System.out.println(p);
    }

    public boolean modificar(String nombre, int vida, int ataque, int defensa, int alcance) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        p.setVida(vida);
        p.setAtaque(ataque);
        p.setDefensa(defensa);
        p.setAlcance(alcance);
        guardar();
        return true;
    }

    public boolean borrar(String nombre) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        personajes.remove(p);
        guardar();
        return true;
    }
}
