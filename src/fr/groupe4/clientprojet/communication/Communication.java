package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.utils.Location;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Communication, effectue les appels API
 *
 * @author Romain
 */
public abstract class Communication {

    /**
     * Récupère les tâches
     *
     * TODO: Appels API + JSON / XML ?
     *
     * @return Liste des tâches
     */
    public static TaskList getTasks() {
        TaskList tasks = new TaskList();

        try {
            File fileXml = new File(Location.getPath() + "fr/groupe4/clientprojet/data/XML/calendar.xml");
            // Récupération du XML

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileXml);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("calendar");
            // Liste des nodes XML

            for (int i=0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // Création de la tâche à partir du node actuel

                    Element element = (Element) node;

                    Task task = new Task(element.getAttribute("id"));

                    task.setDescription(element.getElementsByTagName("description").item(0).getTextContent());

                    tasks.add(task);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }
}
