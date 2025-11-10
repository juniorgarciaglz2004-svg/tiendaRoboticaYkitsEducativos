package gui;

import atributos.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
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

public void exportarXml(File fichero) throws ParserConfigurationException, TransformerException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    DOMImplementation dom = builder.getDOMImplementation();
    Document documento = dom.createDocument(null, "xml", null);


    Element raiz = documento.createElement("Ninnos");
    documento.getDocumentElement().appendChild(raiz);

    for (Ninno ninno : ninnos)
    {
        Element nodoNino = null;

        if (ninno instanceof  NinnoPrincipiante)
        {
           nodoNino = documento.createElement("Principiante");
        }
        else{

            nodoNino = documento.createElement("Avanzado");

        }

        raiz.appendChild(nodoNino);
        adicionaNodo(nodoNino,documento,"Nombre",ninno.getNombre());
        adicionaNodo(nodoNino,documento,"Dni",ninno.getDni());

        if (ninno.isGenero())
        {
            adicionaNodo(nodoNino,documento,"Sexo","Hombre");
        }
        else{
            adicionaNodo(nodoNino,documento,"Sexo","Mujer");
        }

        adicionaNodo(nodoNino,documento,"Fecha_Nacimiento",ninno.getFechaNacimiento().toString());

        if (ninno instanceof  NinnoPrincipiante)
        {
            adicionaNodo(nodoNino,documento,"Nivel",(((NinnoPrincipiante) ninno).getNivel().toString()));
        }
        else{

            adicionaNodo(nodoNino,documento,"Logros",(((NinnoAvanzado) ninno).getLogros().toString()));

        }


    }



    Source source = new DOMSource(documento);
    Result resultado = new StreamResult(fichero);

    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.transform(source, resultado);



}

public void importarXml (File fichero) throws ParserConfigurationException, IOException, SAXException {
    this.ninnos.clear();

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document documento = builder.parse(fichero);

    NodeList listaElementos = documento.getElementsByTagName("*");

    for (int i = 0; i < listaElementos.getLength(); i++)
    {
        Element nodoNinno = (Element) listaElementos.item(i);
        if (nodoNinno.getTagName().equals("Principiante"))
        {
            NinnoPrincipiante principiante = new NinnoPrincipiante();
            principiante.setNombre(nodoNinno.getChildNodes().item(0).getTextContent());
            principiante.setDni(nodoNinno.getChildNodes().item(1).getTextContent());
            principiante.setGenero(nodoNinno.getChildNodes().item(2).getTextContent().equals("Hombre"));
            principiante.setFechaNacimiento(LocalDate.parse(nodoNinno.getChildNodes().item(3).getTextContent()));
            principiante.setNivel(NivelesPrincipiantes.valueOf(nodoNinno.getChildNodes().item(4).getTextContent()));
            this.ninnos.add(principiante);
        }
        else if (nodoNinno.getTagName().equals("Avanzado")) {
            NinnoAvanzado avanzado = new NinnoAvanzado();
            avanzado.setNombre(nodoNinno.getChildNodes().item(0).getTextContent());
            avanzado.setDni(nodoNinno.getChildNodes().item(1).getTextContent());
            avanzado.setGenero(nodoNinno.getChildNodes().item(2).getTextContent().equals("Hombre"));
            avanzado.setFechaNacimiento(LocalDate.parse(nodoNinno.getChildNodes().item(3).getTextContent()));
            avanzado.setLogros(LogrosAvanzados.valueOf(nodoNinno.getChildNodes().item(4).getTextContent()));
            this.ninnos.add(avanzado);
        }

    }


}



private void adicionaNodo(Element raiz,Document documento ,String tag, String value)
{
    Element nodoDatos = documento.createElement(tag);
    raiz.appendChild(nodoDatos);

    Text texto = documento.createTextNode(value);
    nodoDatos.appendChild(texto);
}



}
