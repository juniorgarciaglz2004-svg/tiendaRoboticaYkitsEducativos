package atributos;

import java.time.LocalDate;

public class NinnoPrincipiante extends  Ninno{

private NivelesPrincipiantes nivel;


    public NinnoPrincipiante(String dni, LocalDate fechaNacimiento, boolean genero, String nombre, NivelesPrincipiantes nivel) {
        super(dni, fechaNacimiento, genero, nombre);
        this.nivel = nivel;
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
