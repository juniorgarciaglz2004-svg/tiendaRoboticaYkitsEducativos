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
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase Modelo de MVC
 *
 *
 */

public class NinnoModelo {
    /**
     * listado de ninnos
     */
public ArrayList<Ninno> ninnos = new ArrayList<>();
    /**
     * listado de Kits educativos
     */
public ArrayList<KitEducativo> kits = new ArrayList<>();


    /**
     * Adiciona un principiante al listado
     * @param dni identificador de niño
     * @param fechaNacimiento fecha de nacimiento
     * @param genero el genero (true/hombre) (false/mujer)
     * @param nombre nombre
     * @param nivel el nivel actual del niño
     */
    public void adicionarPrincipiante ( String dni,  LocalDate fechaNacimiento, boolean genero, String nombre , NivelesPrincipiantes nivel)
{
    NinnoPrincipiante principiante = new NinnoPrincipiante(dni,fechaNacimiento,genero,nombre,nivel);
    ninnos.add(principiante);
}

    /**
     * Adiciona un avanzado al listado
     * @param dni identificador de niño
     * @param fechaNacimiento fecha de nacimiento
     * @param genero el genero (true/hombre) (false/mujer)
     * @param nombre nombre
     * @param logros los logros actuales del niño
     */

public void adicionarAvanzado (String dni,  LocalDate fechaNacimiento, boolean genero, String nombre , LogrosAvanzados logros)
{
    NinnoAvanzado avanzado = new NinnoAvanzado(dni,fechaNacimiento,genero,nombre,logros);
    ninnos.add(avanzado);
}

    /**
     * Adiciona un kit educativo
     * @param id la id del kit
     * @param nombre nombre
     * @param cantidad la cantidad de kits
     * @param fechaDeProduccion fecha en la que hizo/produjo
     * @param productoCondicion indica el estado del producto (true/nuevo) (false/reacondicionado)
     * @param clasificacion la valoracion del kit
     */

public void adicionarKit (String id, String nombre, int cantidad, LocalDate fechaDeProduccion, boolean productoCondicion, int clasificacion)
{
    KitEducativo kitEducativo = new KitEducativo(id,nombre,cantidad,fechaDeProduccion,productoCondicion ,clasificacion);
    kits.add(kitEducativo);
}


    /**
     *  Exporta los datos de la lista sobre los ninnos a un fichero xml
     * @param fichero fichero/archivo donde se almacena el xml
     * @throws ParserConfigurationException
     * @throws TransformerException
     */

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

    /**
     * Exporta los datos de la lista sobre los kits a un xml
     * @param fichero fichero/archivo donde se almacena el xml
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
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

    /**
     * importa los datos del archivo xml en el listado de ninnos
     * @param fichero fichero/archivo donde se encuentra el xml
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */

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

    /**
     * importa los datos del archivo xml en el listado de kits
     * @param fichero fichero/archivo donde se encuentra el xml
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */

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

    /**
     * Se exporta los datos de kits convertiendolos a xml en un archivo
     * @param selectedFile fichero destino
     * @throws IOException
     */

    public void exportarHtmlKits(File selectedFile) throws IOException {

        FileWriter fileWriter = new FileWriter(new File(selectedFile.getPath()));

        fileWriter.write("<html>\n" +
                "    <head>\n" +
                "        <title>Html exportado</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        \n" +
                "        <p>Listado de Kits</p>\n" +
                "\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <th>ID</th>\n" +
                "                <th>Nombre</th>\n" +
                "                <th>Cantidad</th>\n" +
                "                <th>Fecha de Produccion</th>\n" +
                "                <th>Condicion</th>\n" +
                "                <th>Clasificacion</th>\n" +
                "            </tr>");
        for (KitEducativo kitEducativo : kits)
        {
            String condicion = null;
            if (kitEducativo.isProductoCondicion())
            {
                condicion = "Nuevo";

            }
            else{
                condicion = "Reacondicionado";
            }


            fileWriter.write("<tr>\n" +
                    "                <td>"+ kitEducativo.getId()  +"</td>\n" +
                    "                <td>" + kitEducativo.getNombre()  +"</td>\n" +
                    "                <td>" + kitEducativo.getCantidad() +"</td>\n" +
                    "                <td>"+ kitEducativo.getFechaDeProduccion() +"</td>\n" +
                    "                <td>"+ condicion+"</td>\n" +
                    "                <td>"+ kitEducativo.getClasificacion() +"</td>\n" +
                    "            </tr>");
        }


        fileWriter.write(" </table>\n" +
                "\n" +
                "    </body>\n" +
                "</html>");

        fileWriter.close();




    }

    /**
     * Se exporta los datos de ninnos convertiendolos a xml en un archivo
     * @param selectedFile fichero destino
     * @throws IOException
     */

    public void exportarHtmlNinnos(File selectedFile) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(selectedFile.getPath()));

        fileWriter.write("<html>\n" +
                "    <head>\n" +
                "        <title>Html exportado</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        \n" +
                "        <p>Listado de ninnos</p>\n" +
                "\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <th>Tipo</th>\n" +
                "                <th>Nombre</th>\n" +
                "                <th>DNI</th>\n" +
                "                <th>Sexo</th>\n" +
                "                <th>Fecha de nacimiento</th>\n" +
                "                <th>Nivel/logros</th>\n" +
                "            </tr>");
        for (Ninno n : ninnos)
        {
            String tipo = null;
            String sexo = null;
            String nivel = null;
            if (n instanceof  NinnoPrincipiante)
            {
                tipo = "Principiante";
                nivel = ((NinnoPrincipiante) n).getNivel().toString();
            }
            else{

                tipo = "Avanzado";
                nivel = ((NinnoAvanzado)n).getLogros().toString();
            }

            if (n.isGenero())
            {
                sexo = "Hombre";
            }
            else {
                sexo = "Mujer";
            }




            fileWriter.write("<tr>\n" +
                    "                <td>"+ tipo  +"</td>\n" +
                    "                <td>" + n.getNombre()  +"</td>\n" +
                    "                <td>" + n.getDni()  +"</td>\n" +
                    "                <td>"+ sexo +"</td>\n" +
                    "                <td>"+ n.getFechaNacimiento() +"</td>\n" +
                    "                <td>"+ nivel +"</td>\n" +
                    "            </tr>");
        }


        fileWriter.write(" </table>\n" +
                "\n" +
                "    </body>\n" +
                "</html>");

        fileWriter.close();
    }
}
