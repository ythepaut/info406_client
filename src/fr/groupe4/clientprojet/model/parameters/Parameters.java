package fr.groupe4.clientprojet.model.parameters;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.themes.ThemeName;
import fr.groupe4.clientprojet.utils.Location;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;

/**
 * Classe abstraite qui contient les paramètres de l'application
 */
public abstract class Parameters {
    /**
     * URL par défaut pour le serveur
     */
    private static final String DEFAULT_SERVER_URL = "https://api.ythepaut.com/g4/actions";

    /**
     * Nom du fichier paramètres
     */
    private static final String fileName = "/parameters.set";

    /**
     * Liste des paramètres avec leurs valeurs<br>
     * ParametersName -&gt; Object<br>
     * Nom paramètre  -&gt; valeur<br>
     * <p>
     * La liste des paramètres est dans une énumération
     *
     * @see ParametersNames
     */
    private static HashMap<ParametersNames, Object> parameters;

    /**
     * <b> Obligatoire ! </b><br>
     * Permet de récupérer tous les paramètres stocké dans le fichier
     */
    public static void init() {
        parameters = new HashMap<>();

        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(fileName));

            parameters = (HashMap<ParametersNames, Object>) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            Logger.error("Le fichier 'parameters.set' n'existe pas !");

            // Définition des paramètres par défaut
            parameters.put(ParametersNames.FIRSTRUN, true);
            parameters.put(ParametersNames.SERVERURL, DEFAULT_SERVER_URL);
            parameters.put(ParametersNames.THEME, ThemeName.CLAIR);
        }
    }

    /**
     * <b>Obligatoire !</b><br>
     * Permet de sauvegarder tous les paramètres dans le fichier
     */
    public static void exit() {
        try {
            if (parameters == null) throw new InitParametersException(); // Si init() n'a pas été appelé

            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(fileName));
            outputStream.writeObject(parameters);
            outputStream.close();
        } catch (IOException | InitParametersException e) {
            Logger.error(e.getMessage());
        }
    }

    // _.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-.
    // Getters et Setters

    /**
     * Récupère l'URL du serveur
     *
     * @return URL
     */
    @NotNull
    public static String getServerUrl() {
        String res = "";
        try {
            if (parameters == null) throw new InitParametersException();
            res = (String) parameters.get(ParametersNames.SERVERURL);
        } catch (InitParametersException e) {
            Logger.error(e.getMessage());
        }
        return res;
    }

    /**
     * Set l'URL du serveur
     *
     * @param serverUrl URL
     */
    public static void setServerUrl(@NotNull String serverUrl) {
        try {
            if (parameters == null) throw new InitParametersException();
            parameters.put(ParametersNames.SERVERURL, serverUrl);
        } catch (InitParametersException e) {
            Logger.error(e.getMessage());
        }
    }

    /**
     * Premier lancement ou non ?
     *
     * @return Premier lancement ?
     */
    public static boolean isFirstRun() {
        boolean res = true;
        try {
            if (parameters == null) throw new InitParametersException();
            res = (boolean) parameters.get(ParametersNames.FIRSTRUN);
        } catch (InitParametersException e) {
            Logger.error(e.getMessage());
        }
        return res;
    }

    /**
     * Set premier lancement
     *
     * @param firstRun Premier lancement
     */
    public static void setFirstRun(boolean firstRun) {
        try {
            if (parameters == null) throw new InitParametersException();
            parameters.put(ParametersNames.FIRSTRUN, firstRun);
        } catch (InitParametersException e) {
            Logger.error(e.getMessage());
        }
    }

    /**
     * Récupère le nom du thème
     *
     * @return Thème
     */
    public static ThemeName getThemeName() {
        ThemeName res = ThemeName.SOMBRE;
        try {
            if (parameters == null) throw new InitParametersException();
            res = (ThemeName) parameters.get(ParametersNames.THEME);
        } catch (InitParametersException e) {
            Logger.error(e.getMessage());
        }

        return res;
    }

    /**
     * Change le thème
     *
     * @param name Nouveau thème
     */
    public static void setThemeName(@NotNull ThemeName name) {
        try {
            if (parameters == null) throw new InitParametersException();
            parameters.put(ParametersNames.THEME, name);
        } catch (InitParametersException e) {
            Logger.error(e.getMessage());
        }
    }
}
