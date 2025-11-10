import gui.NinnoControlador;
import gui.NinnoModelo;
import gui.Ventana;

public class Principal {

    public static void main(String[] args) {
        Ventana ventana = new Ventana();
        NinnoModelo ninnoModelo = new NinnoModelo();
        NinnoControlador ninnoControlador = new NinnoControlador(ventana, ninnoModelo);

    }

}
