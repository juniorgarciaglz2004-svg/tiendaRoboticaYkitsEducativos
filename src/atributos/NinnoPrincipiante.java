package atributos;

import java.time.LocalDate;

/**
 * Clase heredada del padre (Ninno)
 * Contien un atributo para los Niveles
 *
 */

public class NinnoPrincipiante extends  Ninno{

private NivelesPrincipiantes nivel;


    public NinnoPrincipiante(String dni, LocalDate fechaNacimiento, boolean genero, String nombre, NivelesPrincipiantes nivel) {
        super(dni, fechaNacimiento, genero, nombre);
        this.nivel = nivel;
    }

    public NinnoPrincipiante() {
        super();

    }

    public NivelesPrincipiantes getNivel() {
        return nivel;
    }

    public void setNivel(NivelesPrincipiantes nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "NinnoPrincipiante{" +
                "dni='" + getDni() + '\'' +
                ", fechaNacimiento=" + getFechaNacimiento() +
                ", genero=" + isGenero() +
                ", nombre='" + getNombre() + '\'' +
                ", nivel=" + nivel +
                '}';
    }
}
