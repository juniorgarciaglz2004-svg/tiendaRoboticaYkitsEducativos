package atributos;


import java.time.LocalDate;

public abstract class Ninno  {

	private String dni;
	private LocalDate fechaNacimiento;
	private boolean genero;
	private String nombre;

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public boolean isGenero() {
		return genero;
	}

	public void setGenero(boolean genero) {
		this.genero = genero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Ninno{" +
				"dni='" + dni + '\'' +
				", fechaNacimiento=" + fechaNacimiento +
				", genero=" + genero +
				", nombre='" + nombre + '\'' +
				'}';
	}

	public Ninno() {

	}

	public Ninno(String dni, LocalDate fechaNacimiento, boolean genero, String nombre) {
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.nombre = nombre;
	}
}