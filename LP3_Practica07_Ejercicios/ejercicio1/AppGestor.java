package ejercicio1;

import java.util.Scanner;

/** Menú de prueba del Gestor de personajes. */
public class AppGestor {
    private static final Scanner sc = new Scanner(System.in);

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida, escriba un número entero.");
            }
        }
    }

    public static void main(String[] args) {
        Gestor gestor = new Gestor("personajes.txt");
        int opcion;
        do {
            System.out.println("\n--- GESTOR DE PERSONAJES ---");
            System.out.println("1. Mostrar  2. Añadir  3. Modificar  4. Borrar  0. Salir");
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> gestor.mostrar();
                    case 2 -> {
                        System.out.print("Nombre: ");
                        String n = sc.nextLine();
                        Personaje p = new Personaje(n, leerEntero("Vida: "), leerEntero("Ataque: "),
                                leerEntero("Defensa: "), leerEntero("Alcance: "));
                        System.out.println(gestor.anadir(p) ? "Personaje añadido."
                                : "Ya existe un personaje con ese nombre; no se creó.");
                    }
                    case 3 -> {
                        System.out.print("Nombre a modificar: ");
                        String n = sc.nextLine();
                        boolean ok = gestor.modificar(n, leerEntero("Nueva vida: "),
                                leerEntero("Nuevo ataque: "), leerEntero("Nueva defensa: "),
                                leerEntero("Nuevo alcance: "));
                        System.out.println(ok ? "Personaje modificado." : "No existe ese personaje.");
                    }
                    case 4 -> {
                        System.out.print("Nombre a borrar: ");
                        System.out.println(gestor.borrar(sc.nextLine()) ? "Personaje borrado."
                                : "No existe ese personaje.");
                    }
                    case 0 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }
}
