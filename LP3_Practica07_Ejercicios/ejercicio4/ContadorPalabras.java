package ejercicio4;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lee un archivo de texto y reporta líneas, palabras, caracteres (sin saltos de línea),
 * promedio de palabras por línea y las palabras más frecuentes.
 * Una palabra es una secuencia consecutiva de letras y/o dígitos (Character.isLetterOrDigit).
 */
public class ContadorPalabras {
    private static final int TOP = 5; // cuántas palabras frecuentes se muestran

    private int totalLineas;
    private int totalPalabras;
    private int totalCaracteres;
    private final Map<String, Integer> frecuencias = new HashMap<>();

    /** Abre un JFileChooser hasta que el usuario elija un archivo válido (o cancele). */
    public static File pedirArchivo() {
        while (true) {
            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Seleccione el archivo de texto a analizar");
            selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = selector.showOpenDialog(null);
            if (resultado != JFileChooser.APPROVE_OPTION) {
                return null; // el usuario canceló
            }
            File archivo = selector.getSelectedFile();
            if (archivo != null && archivo.exists() && archivo.isFile() && archivo.canRead()) {
                return archivo;
            }
            JOptionPane.showMessageDialog(null,
                    "El archivo no existe o no es válido. Elija otro.",
                    "Archivo inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Recorre el archivo línea por línea acumulando las estadísticas. */
    public void analizar(File archivo) throws IOException {
        totalLineas = 0;
        totalPalabras = 0;
        totalCaracteres = 0;
        frecuencias.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                totalLineas++;
                totalCaracteres += linea.length(); // readLine ya quitó el fin de línea
                contarPalabras(linea);
            }
        }
    }

    private void contarPalabras(String linea) {
        StringBuilder palabra = new StringBuilder();
        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                palabra.append(c);
            } else if (palabra.length() > 0) {
                registrar(palabra.toString());
                palabra.setLength(0);
            }
        }
        if (palabra.length() > 0) registrar(palabra.toString());
    }

    private void registrar(String palabra) {
        totalPalabras++;
        frecuencias.merge(palabra.toLowerCase(), 1, Integer::sum);
    }

    public void mostrarResultados(File archivo) {
        System.out.println("Archivo analizado: " + archivo.getAbsolutePath());
        System.out.println("Total de líneas: " + totalLineas);
        System.out.println("Total de palabras: " + totalPalabras);
        System.out.println("Total de caracteres (sin saltos de línea): " + totalCaracteres);
        double promedio = totalLineas == 0 ? 0 : (double) totalPalabras / totalLineas;
        System.out.printf("Promedio de palabras por línea: %.2f%n", promedio);

        System.out.println("Palabras más frecuentes:");
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(frecuencias.entrySet());
        lista.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue()); // mayor frecuencia primero
            return cmp != 0 ? cmp : a.getKey().compareTo(b.getKey());
        });
        // se muestran las TOP primeras, incluyendo las que empatan con la última mostrada
        int ultimaFrecuencia = -1;
        for (int i = 0; i < lista.size(); i++) {
            int frecuencia = lista.get(i).getValue();
            if (i >= TOP && frecuencia < ultimaFrecuencia) break;
            System.out.printf("  %-12s %d%n", lista.get(i).getKey(), frecuencia);
            ultimaFrecuencia = frecuencia;
        }
    }

    public static void main(String[] args) {
        while (true) {
            File archivo = pedirArchivo();
            if (archivo == null) {
                System.out.println("No se seleccionó ningún archivo. Programa finalizado.");
                return;
            }
            ContadorPalabras contador = new ContadorPalabras();
            try {
                contador.analizar(archivo);
                contador.mostrarResultados(archivo);
                return;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "No se pudo leer el archivo: " + e.getMessage(),
                        "Error de lectura", JOptionPane.ERROR_MESSAGE);
                // se vuelve a pedir otro archivo
            }
        }
    }
}
