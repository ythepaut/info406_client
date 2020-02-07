package fr.groupe4.clientprojet.utils;

import java.io.FileNotFoundException;
import java.util.StringTokenizer;

public abstract class Location {

    /**
     * Renvoie le répertoire de l'exécutable java
     * Renvoie "/" si non trouvé
     *
     * @return String : sous la forme "blabla/"
     */
    public static String getPath() {
        StringTokenizer stringTokenizer = new StringTokenizer(System.getProperty("java.class.path", "." ), System.getProperty("path.separator"));
        try {
            if (stringTokenizer.hasMoreTokens()) {
                return stringTokenizer.nextToken() + System.getProperty("file.separator");
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return System.getProperty("file.separator");
    }
}
