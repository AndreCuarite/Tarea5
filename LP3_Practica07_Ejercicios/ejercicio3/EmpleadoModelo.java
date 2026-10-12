package ejercicio3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** Modelo: acceso al archivo secuencial binario de empleados. */
public class EmpleadoModelo {
    private final File archivo;

    public EmpleadoModelo(String ruta) {
        this.archivo = new File(ruta);
    }

    /** Lee todos los empleados del archivo (lista vacía si aún no existe). */
    public List<Empleado> leerEmpleados() throws IOException {
        List<Empleado> lista = new ArrayList<>();
        if (!archivo.exists()) return lista;
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream(archivo)))) {
            while (true) {
                try {
                    int numero = in.readInt();
                    String nombre = in.readUTF();
                    double sueldo = in.readDouble();
                    lista.add(new Empleado(numero, nombre, sueldo));
                } catch (EOFException fin) {
                    break; // fin del archivo
                }
            }
        }
        return lista;
    }

    /** Agrega al final del archivo (lo crea si no existe). No permite números repetidos. */
    public void agregarEmpleado(Empleado e) throws IOException {
        if (buscarEmpleado(e.getNumero()) != null)
            throw new IllegalArgumentException("Ya existe un empleado con el número " + e.getNumero());
        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(archivo, true)))) {
            escribir(out, e);
        }
    }

    public Empleado buscarEmpleado(int numero) throws IOException {
        for (Empleado e : leerEmpleados())
            if (e.getNumero() == numero) return e;
        return null;
    }

    /** Elimina reescribiendo el archivo sin ese empleado. */
    public boolean eliminarEmpleado(int numero) throws IOException {
        List<Empleado> lista = leerEmpleados();
        boolean eliminado = lista.removeIf(e -> e.getNumero() == numero);
        if (!eliminado) return false;
        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(archivo)))) {
            for (Empleado e : lista) escribir(out, e);
        }
        return true;
    }

    private void escribir(DataOutputStream out, Empleado e) throws IOException {
        out.writeInt(e.getNumero());
        out.writeUTF(e.getNombre());
        out.writeDouble(e.getSueldo());
    }
}
