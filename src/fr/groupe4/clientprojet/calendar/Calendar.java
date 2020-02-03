package fr.groupe4.clientprojet.calendar;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class Calendar {
    public Calendar() {
        try {
            File fXmlFile = new File("data/staff.xml");
            // https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
            System.out.println("ok");
        } catch (Exception e) {
            System.out.println("nok");
            e.printStackTrace();
        }
    }
}
