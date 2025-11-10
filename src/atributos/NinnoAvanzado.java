package atributos;

import java.time.LocalDate;

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

    @Override
    public String toString() {
        return "NinnoPrincipiante{" +
                "dni='" + getDni() + '\'' +
                ", fechaNacimiento=" + getFechaNacimiento() +
                ", genero=" + isGenero() +
                ", nombre='" + getNombre() + '\'' +
                ", logros=" + logros +
                '}';
    }
}
