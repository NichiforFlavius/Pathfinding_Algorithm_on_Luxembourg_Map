import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ReadMap {
    public NodeList arcList;
    public NodeList nodeList;
    public Vector<GraphNode> convertedNodeList;
    public Vector<Vector<Arcs>> listaAdiacenta;

    public void readNodes() {
        convertedNodeList = new Vector<>();
        try {
            File file = new File("map2.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            nodeList = doc.getElementsByTagName("node");
            int nodeListLength = nodeList.getLength();
            listaAdiacenta = new Vector<>(nodeListLength);

            for (int index = 0; index < nodeListLength; index++) {
                Node node = nodeList.item(index);
                Element nodeE = (Element) node;
                GraphNode auxNod = new GraphNode(MapScaling.convertLong((double) Integer.parseInt(nodeE.getAttribute("longitude")) / 100000),
                        MapScaling.convertLat((double) Integer.parseInt(nodeE.getAttribute("latitude")) / 100000),
                        Integer.parseInt(nodeE.getAttribute("id")));
                convertedNodeList.add(auxNod);

                Vector<Arcs> auxVec = new Vector<>();
                Arcs arc = new Arcs(Integer.parseInt(nodeE.getAttribute("id")), 0);
                auxVec.add(arc);
                listaAdiacenta.add(auxVec);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
        }
    }

    public void readList() {
        try {
            File file = new File("map2.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            arcList = doc.getElementsByTagName("arc");
            int arcListLength = arcList.getLength();
            for (int index = 0; index < arcListLength; index++) {
                Node arc = arcList.item(index);
                Element arcE = (Element) arc;
                listaAdiacenta.elementAt(Integer.parseInt(arcE.getAttribute("from"))).add(new Arcs(Integer.parseInt(arcE.getAttribute("to")), Integer.parseInt(arcE.getAttribute("length"))));
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
        }
    }

    public ReadMap() {

    }
}
