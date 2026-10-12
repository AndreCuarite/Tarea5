package ejercicio2;

import java.util.Scanner;

/** Menú de prueba del gestor con nuevas funcionalidades. */
public class AppGestorAvanzado {
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

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public static void main(String[] args) {
        Gestor gestor = new Gestor("personajes2.txt");

        String resp = leerTexto("¿Cargar personajes aleatorios al iniciar? (s/n): ");
        if (resp.equalsIgnoreCase("s")) {
            int n = gestor.cargarAleatorios(leerEntero("¿Cuántos? "));
            System.out.println("Se añadieron " + n + " personajes aleatorios.");
        }

        int opcion;
        do {
            System.out.println("\n--- GESTOR DE PERSONAJES (AVANZADO) ---");
            System.out.println("1. Mostrar            2. Añadir           3. Modificar todo");
            System.out.println("4. Borrar             5. Ordenar por atributo");
            System.out.println("6. Actualizar un atributo               7. Estadísticas");
            System.out.println("8. Importar desde archivo               9. Subir de nivel");
            System.out.println("0. Salir");
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> gestor.mostrar();
                    case 2 -> {
                        String n = leerTexto("Nombre: ");
                        Personaje p = new Personaje(n, leerEntero("Vida: "), leerEntero("Ataque: "),
                                leerEntero("Defensa: "), leerEntero("Alcance: "));
                        System.out.println(gestor.anadir(p) ? "Personaje añadido."
                                : "Ya existe un personaje con ese nombre; no se creó.");
                    }
                    case 3 -> {
                        String n = leerTexto("Nombre a modificar: ");
                        boolean ok = gestor.modificar(n, leerEntero("Nueva vida: "),
                                leerEntero("Nuevo ataque: "), leerEntero("Nueva defensa: "),
                                leerEntero("Nuevo alcance: "));
                        System.out.println(ok ? "Personaje modificado." : "No existe ese personaje.");
                    }
                    case 4 -> System.out.println(gestor.borrar(leerTexto("Nombre a borrar: "))
                            ? "Personaje borrado." : "No existe ese personaje.");
                    case 5 -> gestor.mostrarOrdenadosPor(
                            leerTexto("Atributo (vida/ataque/defensa/alcance/nivel): "));
                    case 6 -> {
                        String n = leerTexto("Nombre: ");
                        String a = leerTexto("Atributo (vida/ataque/defensa/alcance): ");
                        int v = leerEntero("Nuevo valor: ");
                        System.out.println(gestor.actualizarAtributo(n, a, v)
                                ? "Atributo actualizado." : "No existe ese personaje.");
                    }
                    case 7 -> gestor.mostrarEstadisticas();
                    case 8 -> gestor.importar(leerTexto("Ruta del archivo a importar: "));
                    case 9 -> System.out.println(gestor.subirNivel(leerTexto("Nombre: "))
                            ? "El personaje subió de nivel." : "No existe ese personaje.");
                    case 0 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }
}
