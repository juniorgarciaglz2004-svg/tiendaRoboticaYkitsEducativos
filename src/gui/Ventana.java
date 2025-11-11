package gui;

import atributos.KitEducativo;
import atributos.Ninno;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public class Ventana {
     JPanel PanelPrincipal;
     JTextField txtDni;
     JTextField txtNombre;
     JRadioButton hombreRadioButton;
     JRadioButton mujerRadioButton;
     DatePicker fechaDeNacimientoDPicker;
     JRadioButton principianteRadioButton;
     JRadioButton avanzadoRadioButton;
    JComboBox NivelcomboBox;
    JComboBox LogroscomboBox;
    JButton AdicionarBtn;
    JButton exportatBtn;
    JButton importarBtn;
    JList list1;
     JLabel NivelLbl;
     JLabel LogrosLbl;
 JTextField txtID;
     JTextField txtNombre_Kit;
     JSpinner cantidadSpinner1;
     JCheckBox nuevoCheckBox;
     JCheckBox reacondicionadoCheckBox;
     JSlider clasificacionSlider1;
     DatePicker fechaDeProduccion;
     JList listaKit;
     JButton adicionarKitBtn;
     JButton exportarKitBtn;
     JButton importarKitBtn;

    public JFrame frame;
    public DefaultListModel<Ninno> ninnoDefaultListModel;
    public DefaultListModel<KitEducativo> kitDefaultListModel;

    public Ventana() {
        frame = new JFrame("Ventana");
        frame.setContentPane(this.PanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ninnoDefaultListModel = new DefaultListModel<>();

        list1.setModel(ninnoDefaultListModel);


       kitDefaultListModel  = new DefaultListModel<>();

        listaKit.setModel(kitDefaultListModel);

        LogrosLbl.hide();
        LogroscomboBox.hide();
    }



}
