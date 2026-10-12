package ejercicio2;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/** Gestor con funcionalidades extra: filtrar/ordenar, aleatorios, estadísticas, importar y niveles. */
public class Gestor {
    private static final String[] NOMBRES_ALEATORIOS = {
        "Mago", "Paladin", "Ladron", "Clerigo", "Bardo", "Druida", "Ninja", "Samurai", "Vikingo", "Cazador"
    };

    private final List<Personaje> personajes = new ArrayList<>();
    private final String archivo;
    private final Random azar = new Random();

    public Gestor(String archivo) {
        this.archivo = archivo;
        cargar();
    }

    // ---------- Persistencia ----------

    private void cargar() {
        File f = new File(archivo);
        if (!f.exists()) {
            personajes.add(new Personaje("Caballero", 4, 2, 4, 2));
            personajes.add(new Personaje("Guerrero", 2, 4, 2, 4));
            personajes.add(new Personaje("Arquero", 2, 4, 1, 8));
            guardar();
            return;
        }
        leerArchivo(f, false);
    }

    /** Lee un archivo con el formato del gestor; devuelve cuántos personajes se añadieron. */
    private int leerArchivo(File f, boolean informar) {
        int agregados = 0, omitidos = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                try {
                    String[] p = linea.split(";");
                    int nivel = p.length >= 6 ? Integer.parseInt(p[5].trim()) : 1;
                    Personaje nuevo = new Personaje(p[0], Integer.parseInt(p[1].trim()),
                            Integer.parseInt(p[2].trim()), Integer.parseInt(p[3].trim()),
                            Integer.parseInt(p[4].trim()), nivel);
                    if (buscar(nuevo.getNombre()) == null) {
                        personajes.add(nuevo);
                        agregados++;
                    } else {
                        omitidos++;
                    }
                } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                    // IllegalArgumentException cubre también NumberFormatException
                    omitidos++;
                    System.out.println("Línea inválida omitida: " + linea);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo: " + f.getPath());
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        if (informar)
            System.out.println("Importación terminada: " + agregados + " añadidos, " + omitidos + " omitidos.");
        return agregados;
    }

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

    // ---------- Operaciones básicas ----------

    public boolean anadir(Personaje nuevo) {
        if (buscar(nuevo.getNombre()) != null) return false;
        personajes.add(nuevo);
        guardar();
        return true;
    }

    public void mostrar() {
        mostrarLista(personajes);
    }

    private void mostrarLista(List<Personaje> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay personajes registrados.");
            return;
        }
        for (Personaje p : lista) System.out.println(p);
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

    // ---------- Funcionalidades nuevas ----------

    /** Muestra los personajes ordenados de mayor a menor según un atributo. */
    public void mostrarOrdenadosPor(String atributo) {
        List<Personaje> copia = new ArrayList<>(personajes);
        copia.sort(Comparator.comparingInt((Personaje p) -> p.getAtributo(atributo)).reversed());
        mostrarLista(copia);
    }

    /** Añade n personajes aleatorios (nombres que no existan todavía). */
    public int cargarAleatorios(int cantidad) {
        int creados = 0;
        for (String base : NOMBRES_ALEATORIOS) {
            if (creados >= cantidad) break;
            if (buscar(base) != null) continue;
            personajes.add(new Personaje(base, 1 + azar.nextInt(10), 1 + azar.nextInt(10),
                    1 + azar.nextInt(10), 1 + azar.nextInt(10)));
            creados++;
        }
        if (creados > 0) guardar();
        return creados;
    }

    /** Actualiza solo un atributo de un personaje existente. */
    public boolean actualizarAtributo(String nombre, String atributo, int valor) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        p.setAtributo(atributo, valor);
        guardar();
        return true;
    }

    /** Muestra el total de personajes y el promedio de cada atributo. */
    public void mostrarEstadisticas() {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes para calcular estadísticas.");
            return;
        }
        double vida = 0, ataque = 0, defensa = 0, alcance = 0, nivel = 0;
        for (Personaje p : personajes) {
            vida += p.getVida();
            ataque += p.getAtaque();
            defensa += p.getDefensa();
            alcance += p.getAlcance();
            nivel += p.getNivel();
        }
        int n = personajes.size();
        System.out.println("Total de personajes: " + n);
        System.out.printf("Promedios -> Vida: %.2f | Ataque: %.2f | Defensa: %.2f | Alcance: %.2f | Nivel: %.2f%n",
                vida / n, ataque / n, defensa / n, alcance / n, nivel / n);
    }

    /** Importa personajes desde un archivo externo con formato nombre;vida;ataque;defensa;alcance[;nivel]. */
    public void importar(String ruta) {
        File f = new File(ruta);
        if (!f.exists() || !f.isFile()) {
            System.out.println("El archivo no existe: " + ruta);
            return;
        }
        if (leerArchivo(f, true) > 0) guardar();
    }

    /** Sube un nivel al personaje y mejora sus atributos. */
    public boolean subirNivel(String nombre) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        p.subirNivel();
        guardar();
        return true;
    }
}
