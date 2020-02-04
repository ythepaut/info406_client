package fr.groupe4.clientprojet.calendar;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class Calendar {
    public Calendar() {
        try {
            File fileXml = new File("src/fr/groupe4/clientprojet/data/XML/calendar.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileXml);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("calendar");

            for (int i=0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    System.out.println("id : " + element.getAttribute("id"));
                    System.out.println("Description : " + element.getElementsByTagName("description").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
