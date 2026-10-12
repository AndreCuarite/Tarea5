package ejercicio1;

/** Representa un personaje; todos sus atributos numéricos deben ser enteros > 0. */
public class Personaje {
    private String nombre;
    private int vida, ataque, defensa, alcance;

    public Personaje(String nombre, int vida, int ataque, int defensa, int alcance) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        this.nombre = nombre.trim();
        this.vida = validar(vida, "vida");
        this.ataque = validar(ataque, "ataque");
        this.defensa = validar(defensa, "defensa");
        this.alcance = validar(alcance, "alcance");
    }

    private static int validar(int valor, String campo) {
        if (valor <= 0)
            throw new IllegalArgumentException("El atributo " + campo + " debe ser un entero mayor que cero.");
        return valor;
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getAlcance() { return alcance; }

    public void setVida(int vida) { this.vida = validar(vida, "vida"); }
    public void setAtaque(int ataque) { this.ataque = validar(ataque, "ataque"); }
    public void setDefensa(int defensa) { this.defensa = validar(defensa, "defensa"); }
    public void setAlcance(int alcance) { this.alcance = validar(alcance, "alcance"); }

    /** Formato de una línea del archivo. */
    public String aLinea() {
        return nombre + ";" + vida + ";" + ataque + ";" + defensa + ";" + alcance;
    }

    @Override
    public String toString() {
        return String.format("%-12s Vida: %d  Ataque: %d  Defensa: %d  Alcance: %d",
                nombre, vida, ataque, defensa, alcance);
    }
}
