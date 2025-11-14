package atributos;

import java.time.LocalDate;

/**
 *  Clase Kit Educativo
 *  Contiene Atributos
 */
public class KitEducativo {

    private String id;
    private  String nombre;
    private int cantidad;
    private LocalDate fechaDeProduccion;
    private boolean productoCondicion;
    private int clasificacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaDeProduccion() {
        return fechaDeProduccion;
    }

    public void setFechaDeProduccion(LocalDate fechaDeProduccion) {
        this.fechaDeProduccion = fechaDeProduccion;
    }

    public boolean isProductoCondicion() {
        return productoCondicion;
    }

    public void setProductoCondicion(boolean productoCondicion) {
        this.productoCondicion = productoCondicion;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public KitEducativo(String id, String nombre, int cantidad, LocalDate fechaDeProduccion, boolean productoCondicion, int clasificacion) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaDeProduccion = fechaDeProduccion;
        this.productoCondicion = productoCondicion;
        this.clasificacion = clasificacion;
    }

    public KitEducativo() {
    }


    @Override
    public String toString() {
        return "KitEducativo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", fechaDeProduccion=" + fechaDeProduccion +
                ", productoCondicion=" + productoCondicion +
                ", clasificacion=" + clasificacion +
                '}';
    }
}

