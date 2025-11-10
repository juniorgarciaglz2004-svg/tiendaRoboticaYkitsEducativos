package gui;

import atributos.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class NinnoModelo {
public ArrayList<Ninno> ninnos = new ArrayList<>();

public void adicionarPrincipiante ( String dni,  LocalDate fechaNacimiento, boolean genero, String nombre , NivelesPrincipiantes nivel)
{
    NinnoPrincipiante principiante = new NinnoPrincipiante(dni,fechaNacimiento,genero,nombre,nivel);
    ninnos.add(principiante);
}

public void adicionarAvanzado (String dni,  LocalDate fechaNacimiento, boolean genero, String nombre , LogrosAvanzados logros)
{
    NinnoAvanzado avanzado = new NinnoAvanzado(dni,fechaNacimiento,genero,nombre,logros);
    ninnos.add(avanzado);
}



}
