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
public ArrayList<KitEducativo> kits = new ArrayList<>();


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

public void adicionarKit (String id, String nombre, int cantidad, LocalDate fechaDeProduccion, boolean productoCondicion, int clasificacion)
{
    KitEducativo kitEducativo = new KitEducativo(id,nombre,cantidad,fechaDeProduccion,productoCondicion ,clasificacion);
    kits.add(kitEducativo);
}

public void exportarXmlNinnos(File fichero) throws ParserConfigurationException, TransformerException {
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

    public void exportarXmlKits(File fichero) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation dom = builder.getDOMImplementation();
        Document documento = dom.createDocument(null, "xml", null);


        Element raiz = documento.createElement("Kits");
        documento.getDocumentElement().appendChild(raiz);

        for (KitEducativo kit : kits)
        {
            Element nodoKit = documento.createElement("Kit");


            raiz.appendChild(nodoKit);
            adicionaNodo(nodoKit,documento,"Nombre",kit.getNombre());
            adicionaNodo(nodoKit,documento,"ID",kit.getId());

            if (kit.isProductoCondicion())
            {
                adicionaNodo(nodoKit,documento,"Condificion_Producto","Nuevo");
            }
            else{
                adicionaNodo(nodoKit,documento,"Condificion_Producto","Reacondicionado");
            }

            adicionaNodo(nodoKit,documento,"Fecha_Produccion",kit.getFechaDeProduccion().toString());

            adicionaNodo(nodoKit,documento,"Cantidad",String.valueOf( kit.getCantidad()));
            adicionaNodo(nodoKit,documento,"Clasificacion",String.valueOf( kit.getClasificacion()));


        }



        Source source = new DOMSource(documento);
        Result resultado = new StreamResult(fichero);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, resultado);



    }



    public void importarXmlNinnos(File fichero) throws ParserConfigurationException, IOException, SAXException {
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
                principiante.setNombre(nodoNinno.getElementsByTagName("Nombre").item(0).getTextContent());
                principiante.setDni(nodoNinno.getElementsByTagName("Dni").item(0).getTextContent());
                principiante.setGenero(nodoNinno.getElementsByTagName("Sexo").item(0).getTextContent().equals("Hombre"));
                principiante.setFechaNacimiento(LocalDate.parse(nodoNinno.getElementsByTagName("Fecha_Nacimiento").item(0).getTextContent()));
                principiante.setNivel(NivelesPrincipiantes.valueOf(nodoNinno.getElementsByTagName("Nivel").item(0).getTextContent()));
                this.ninnos.add(principiante);
            }
            else if (nodoNinno.getTagName().equals("Avanzado")) {
                NinnoAvanzado avanzado = new NinnoAvanzado();
                avanzado.setNombre(nodoNinno.getElementsByTagName("Nombre").item(0).getTextContent());
                avanzado.setDni(nodoNinno.getElementsByTagName("Dni").item(0).getTextContent());
                avanzado.setGenero(nodoNinno.getElementsByTagName("Sexo").item(0).getTextContent().equals("Hombre"));
                avanzado.setFechaNacimiento(LocalDate.parse(nodoNinno.getElementsByTagName("Fecha_Nacimiento").item(0).getTextContent()));
                avanzado.setLogros(LogrosAvanzados.valueOf(nodoNinno.getElementsByTagName("Logros").item(0).getTextContent()));
                this.ninnos.add(avanzado);
            }

        }
    }

    public void importarXmlKit(File fichero) throws ParserConfigurationException, IOException, SAXException {
        this.kits.clear();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        NodeList listaElementos = documento.getElementsByTagName("*");

        for (int i = 0; i < listaElementos.getLength(); i++)
        {
            Element nodoKit = (Element) listaElementos.item(i);
            if (nodoKit.getTagName().equals("Kit"))
            {
                KitEducativo kitEducativo = new KitEducativo();
                kitEducativo.setNombre(nodoKit.getElementsByTagName("Nombre").item(0).getTextContent());
                kitEducativo.setId(nodoKit.getElementsByTagName("ID").item(0).getTextContent());
                kitEducativo.setProductoCondicion(nodoKit.getElementsByTagName("Condificion_Producto").item(0).getTextContent().equals("Nuevo"));
                kitEducativo.setFechaDeProduccion(LocalDate.parse(nodoKit.getElementsByTagName("Fecha_Produccion").item(0).getTextContent()));
                kitEducativo.setCantidad(Integer.parseInt(nodoKit.getElementsByTagName("Cantidad").item(0).getTextContent()));
                kitEducativo.setCantidad(Integer.parseInt(nodoKit.getElementsByTagName("Clasificacion").item(0).getTextContent()));
                this.kits.add(kitEducativo);
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
