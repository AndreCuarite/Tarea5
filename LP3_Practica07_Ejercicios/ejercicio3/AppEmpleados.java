package ejercicio3;

public class AppEmpleados {
    public static void main(String[] args) {
        EmpleadoModelo modelo = new EmpleadoModelo("empleados.dat");
        EmpleadoVista vista = new EmpleadoVista();
        new EmpleadoControlador(modelo, vista).iniciar();
    }
}
