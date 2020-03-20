package fr.groupe4.clientprojet.model.parameters;

import java.util.HashMap;

/**
 * Classe abstraite qui contient les paramètres de l'application
 */
public abstract class Parameters {
    /**
     * Liste des paramètres avec leurs valeurs
     * ParametersName -> Object
     * Nom paramètre  -> valeur
     *
     * La liste des paramètres est dans une énumération
     * @see ParametersNames
     */
    private static HashMap<ParametersNames, Object> parameters;

    /**
     * <!> Obligatoire </!>
     * Permet de récuperer tous les paramètres stocké dans le fichier
     */
    public static void init() {
        parameters = new HashMap<>();

        // Temporaire, le temps de créer le fichier
        parameters.put(ParametersNames.SERVERURL, "https://api.ythepaut.com/g4/actions");
        parameters.put(ParametersNames.FIRSTRUN, true);
    }

    /**
     * <!> Obligatoire </!>
     * Permet de sauvegarder tous les paramètres dans le fichier
     */
    public static void exit() {

    }

    // _.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-.
    // Getters et Setters

    public static String getServerUrl() {
        return (String) parameters.get(ParametersNames.SERVERURL);
    }

    public static void setServerUrl(String serverUrl) {
        parameters.put(ParametersNames.SERVERURL, serverUrl);
    }

    public static boolean isFirstRun() {
        return (boolean) parameters.get(ParametersNames.FIRSTRUN);
    }

    public static void setFirstRun(boolean firstRun) {
        parameters.put(ParametersNames.FIRSTRUN, firstRun);
    }
}
