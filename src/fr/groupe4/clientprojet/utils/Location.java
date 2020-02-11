package fr.groupe4.clientprojet.utils;

/**
 * Classe abstraite qui permet de connaître le répertoire de l'éxécutable via sa méthode statique getPath();
 */
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
