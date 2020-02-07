package fr.groupe4.clientprojet.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public abstract class Location {

    /**
     * Renvoie le répertoire de l'exécutable java
     * Renvoie "/" si non trouvé
     *
     * @return String : sous la forme "blabla/"
     */
    public static String getPath() {
        return System.getProperty("java.class.path");
    }
}
