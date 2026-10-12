package ejercicio2;

/** Personaje con nivel: al subir de nivel mejora sus atributos. Atributos enteros > 0. */
public class Personaje {
    private String nombre;
    private int vida, ataque, defensa, alcance, nivel;

    public Personaje(String nombre, int vida, int ataque, int defensa, int alcance) {
        this(nombre, vida, ataque, defensa, alcance, 1);
    }

    public Personaje(String nombre, int vida, int ataque, int defensa, int alcance, int nivel) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        this.nombre = nombre.trim();
        this.vida = validar(vida, "vida");
        this.ataque = validar(ataque, "ataque");
        this.defensa = validar(defensa, "defensa");
        this.alcance = validar(alcance, "alcance");
        this.nivel = validar(nivel, "nivel");
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
    public int getNivel() { return nivel; }

    public void setVida(int vida) { this.vida = validar(vida, "vida"); }
    public void setAtaque(int ataque) { this.ataque = validar(ataque, "ataque"); }
    public void setDefensa(int defensa) { this.defensa = validar(defensa, "defensa"); }
    public void setAlcance(int alcance) { this.alcance = validar(alcance, "alcance"); }

    /** Sube un nivel y mejora los atributos del personaje. */
    public void subirNivel() {
        nivel++;
        vida += 2;
        ataque += 1;
        defensa += 1;
        alcance += 1;
    }

    /** Devuelve el valor de un atributo según su nombre (vida, ataque, defensa, alcance, nivel). */
    public int getAtributo(String atributo) {
        switch (atributo.toLowerCase()) {
            case "vida": return vida;
            case "ataque": return ataque;
            case "defensa": return defensa;
            case "alcance": return alcance;
            case "nivel": return nivel;
            default: throw new IllegalArgumentException("Atributo desconocido: " + atributo);
        }
    }

    /** Cambia un solo atributo (vida, ataque, defensa o alcance). */
    public void setAtributo(String atributo, int valor) {
        switch (atributo.toLowerCase()) {
            case "vida": setVida(valor); break;
            case "ataque": setAtaque(valor); break;
            case "defensa": setDefensa(valor); break;
            case "alcance": setAlcance(valor); break;
            default: throw new IllegalArgumentException("Atributo desconocido: " + atributo);
        }
    }

    /** Línea del archivo: nombre;vida;ataque;defensa;alcance;nivel */
    public String aLinea() {
        return nombre + ";" + vida + ";" + ataque + ";" + defensa + ";" + alcance + ";" + nivel;
    }

    @Override
    public String toString() {
        return String.format("%-12s Nv.%d  Vida: %d  Ataque: %d  Defensa: %d  Alcance: %d",
                nombre, nivel, vida, ataque, defensa, alcance);
    }
}
