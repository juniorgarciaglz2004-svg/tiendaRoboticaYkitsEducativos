package gui;

import atributos.LogrosAvanzados;
import atributos.Ninno;
import atributos.NivelesPrincipiantes;
import org.xml.sax.SAXException;
import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class NinnoControlador implements ActionListener , ListSelectionListener , WindowListener {
private Ventana vista;
private NinnoModelo ninnoModelo;



    public NinnoControlador(Ventana vista, NinnoModelo ninnoModelo) {
        this.vista = vista;
        this.ninnoModelo = ninnoModelo;

        addActionListener(this);
        addWindowListener(this);
        addListSelectionListener(this);

    }

    private void addActionListener(NinnoControlador ninnoControlador) {
        vista.AdicionarBtn.addActionListener(ninnoControlador);
        vista.exportatBtn.addActionListener(ninnoControlador);
        vista.importarBtn.addActionListener(ninnoControlador);
        vista.principianteRadioButton.addActionListener(ninnoControlador);
        vista.avanzadoRadioButton.addActionListener(ninnoControlador);

    }

    private void addWindowListener(WindowListener listener) {
        vista.frame.addWindowListener(listener);
    }

    private void addListSelectionListener(ListSelectionListener listener) {
        vista.list1.addListSelectionListener(listener);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand)
        {
            case "Principiante" :
                vista.NivelLbl.show();
                vista.NivelcomboBox.show();
                vista.LogroscomboBox.hide();
                vista.LogrosLbl.hide();

                break;

            case "Avanzado":
                vista.NivelLbl.hide();
                vista.NivelcomboBox.hide();
                vista.LogroscomboBox.show();
                vista.LogrosLbl.show();

                break;

            case "ADICIONAR" :

                if (isNOTValid())
                {
                    Util.mensajeError("Por favor complete toda la informacion");
                    break;

                }

                if (vista.principianteRadioButton.isSelected())
                {
                    ninnoModelo.adicionarPrincipiante(
                            vista.txtDni.getText(),
                            vista.fechaDeNacimientoDPicker.getDate(),
                            vista.hombreRadioButton.isSelected(),
                            vista.txtNombre.getText(),
                            NivelesPrincipiantes.valueOf(vista.NivelcomboBox.getSelectedItem().toString())
                    );
                }
                else {
                    ninnoModelo.adicionarAvanzado(
                            vista.txtDni.getText(),
                            vista.fechaDeNacimientoDPicker.getDate(),
                            vista.hombreRadioButton.isSelected(),
                            vista.txtNombre.getText(),
                            LogrosAvanzados.valueOf(vista.LogroscomboBox.getSelectedItem().toString())
                    );
                }
                limpiarCampos();
                vista.ninnoDefaultListModel.addElement(ninnoModelo.ninnos.get(ninnoModelo.ninnos.size()-1));
                break;

            case "EXPORTAR" :
                JFileChooser selectorFichero2 = Util.crearSelectorFichero("Archivos XML","xml");
                int opt2=selectorFichero2.showSaveDialog(null);
                if (opt2==JFileChooser.APPROVE_OPTION) {
                    try {

                        ninnoModelo.exportarXml(selectorFichero2.getSelectedFile());

                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "IMPORTAR":

                JFileChooser selectorFichero = Util.crearSelectorFichero("Archivos XML","xml");
                int opt=selectorFichero.showOpenDialog(null);
                if (opt==JFileChooser.APPROVE_OPTION) {
                    try {
                        ninnoModelo.importarXml(selectorFichero.getSelectedFile());

                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    }
                    refrescar();
                }
                break;

        }


    }

    private void limpiarCampos() {
        vista.txtDni.setText(null);
        vista.fechaDeNacimientoDPicker.setDate(null);
        vista.hombreRadioButton.setSelected(true);
        vista.mujerRadioButton.setSelected(false);
        vista.txtNombre.setText(null);
        vista.NivelcomboBox.setSelectedIndex(0);
        vista.LogroscomboBox.setSelectedIndex(0);

    }

    private boolean isNOTValid() {
    if (vista.txtDni.getText().trim().length()==0)
    {
        return true;
    }
        if (vista.txtNombre.getText().trim().length()==0)
        {
            return true;
        }
        if (!vista.hombreRadioButton.isSelected() && !vista.mujerRadioButton.isSelected())
        {
            return true;
        }
        if (vista.fechaDeNacimientoDPicker.getText().trim().length()==0)
        {
            return true;
        }
        if (!vista.principianteRadioButton.isSelected() && !vista.avanzadoRadioButton.isSelected())
        {
            return true;
        }



    return false;
    }

    private void refrescar() {
        vista.ninnoDefaultListModel.clear();
        for (Ninno ninno:ninnoModelo.ninnos) {
            vista.ninnoDefaultListModel.addElement(ninno);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int resp= Util.mensajeConfirmacion("¿Desea cerrar la ventana?","Salir");
        if (resp== JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
