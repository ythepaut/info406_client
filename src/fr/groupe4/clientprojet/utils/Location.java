package fr.groupe4.clientprojet.utils;

import java.util.StringTokenizer;

public abstract class Location {

    /**
     * Renvoie le path des .class
     *
     * @return : String
     */
    public static String getPath() {
        StringTokenizer stringTokenizer = new StringTokenizer(System.getProperty("java.class.path", "." ), System.getProperty("path.separator"));
        if (stringTokenizer.hasMoreTokens()) {
            return stringTokenizer.nextToken();
        } else {
            return "";
        }
    }
}
