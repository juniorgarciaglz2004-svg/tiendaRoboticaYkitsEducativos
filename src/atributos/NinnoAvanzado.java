package atributos;

import java.time.LocalDate;

/**
 * Clase heredada del padre (Ninno)
 * Contien un atributo para los logros
 *
 */

public class NinnoAvanzado extends Ninno{

    private  LogrosAvanzados logros;


    public NinnoAvanzado(String dni, LocalDate fechaNacimiento, boolean genero, String nombre, LogrosAvanzados logros) {
        super(dni, fechaNacimiento, genero, nombre);
        this.logros = logros;
    }


    public LogrosAvanzados getLogros() {
        return logros;
    }

    public void setLogros(LogrosAvanzados logros) {
        this.logros = logros;
    }

    public NinnoAvanzado() {
    }

    @Override
    public String toString() {
        return "NinnoAvanzado{" +
                "dni='" + getDni() + '\'' +
                ", fechaNacimiento=" + getFechaNacimiento() +
                ", genero=" + isGenero() +
                ", nombre='" + getNombre() + '\'' +
                ", logros=" + logros +
                '}';
    }
}
