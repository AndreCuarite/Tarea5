package ejercicio3;

import java.io.IOException;

/** Controlador: conecta la vista con el modelo. */
public class EmpleadoControlador {
    private final EmpleadoModelo modelo;
    private final EmpleadoVista vista;

    public EmpleadoControlador(EmpleadoModelo modelo, EmpleadoVista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {
        listar(); // al iniciar, se muestran los empleados almacenados
        int opcion;
        do {
            opcion = vista.mostrarMenu();
            try {
                switch (opcion) {
                    case 1 -> listar();
                    case 2 -> agregar();
                    case 3 -> buscar();
                    case 4 -> eliminar();
                    case 5 -> vista.mostrarMensaje("Programa finalizado.");
                    default -> vista.mostrarMensaje("Opción no válida.");
                }
            } catch (IOException e) {
                vista.mostrarMensaje("Error de lectura/escritura: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                vista.mostrarMensaje("Operación fallida: " + e.getMessage());
            }
        } while (opcion != 5);
    }

    private void listar() {
        try {
            vista.mostrarEmpleados(modelo.leerEmpleados());
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudo leer el archivo: " + e.getMessage());
        }
    }

    private void agregar() throws IOException {
        int numero = vista.leerEntero("Número: ");
        String nombre = vista.leerTexto("Nombre: ");
        if (nombre.isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        double sueldo = vista.leerDecimal("Sueldo: ");
        modelo.agregarEmpleado(new Empleado(numero, nombre, sueldo));
        vista.mostrarMensaje("Empleado agregado correctamente.");
    }

    private void buscar() throws IOException {
        Empleado e = modelo.buscarEmpleado(vista.leerEntero("Número a buscar: "));
        vista.mostrarMensaje(e != null ? e.toString() : "No se encontró el empleado.");
    }

    private void eliminar() throws IOException {
        boolean ok = modelo.eliminarEmpleado(vista.leerEntero("Número a eliminar: "));
        vista.mostrarMensaje(ok ? "Empleado eliminado correctamente."
                : "No existe un empleado con ese número.");
    }
}
