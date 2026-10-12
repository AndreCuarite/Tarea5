package ejercicio3;

import java.util.List;
import java.util.Scanner;

/** Vista: interacción por consola. */
public class EmpleadoVista {
    private final Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n===== EMPLEADOS =====");
        System.out.println("1. Listar todos los empleados");
        System.out.println("2. Agregar un nuevo empleado");
        System.out.println("3. Buscar un empleado por número");
        System.out.println("4. Eliminar un empleado por número");
        System.out.println("5. Salir");
        return leerEntero("Elija una opción: ");
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                mostrarMensaje("Error: ingrese un número entero válido.");
            }
        }
    }

    public double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v < 0) throw new NumberFormatException();
                return v;
            } catch (NumberFormatException e) {
                mostrarMensaje("Error: ingrese un monto válido (mayor o igual a 0).");
            }
        }
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public void mostrarEmpleados(List<Empleado> lista) {
        if (lista.isEmpty()) {
            mostrarMensaje("No hay empleados registrados.");
            return;
        }
        lista.forEach(System.out::println);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
