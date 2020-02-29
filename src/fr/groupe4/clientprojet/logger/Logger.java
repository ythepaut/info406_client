package fr.groupe4.clientprojet.logger;

import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import fr.groupe4.clientprojet.logger.enums.LoggerType;
import fr.groupe4.clientprojet.utils.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static fr.groupe4.clientprojet.logger.enums.LoggerColor.*;
import static fr.groupe4.clientprojet.logger.enums.LoggerType.*;
import static fr.groupe4.clientprojet.logger.enums.LoggerOption.*;

/**
 * Gestion des logs <br>
 * <br>
 * Avantages : couleurs, fichier, surcharge d'arguments (sans restriction de nombre ou de type) <br>
 * Dossier des logs : ./logs (out/production/info406_client/logs pour IntelliJ) <br>
 * Chaque fichier .log est nommé selon l'heure à laquelle il a été créé <br>
 * <br>
 * Exemples d'utilisation simple : <br><code>
 *      Logger.warning("Mauvais caractère"); <br>
 *      // Affiche un texte en jaune et l'enregiste dans le fichier</code><br>
 * <br>
 * Exemple plus complexe : <br><code>
 *      Logger.debug("variable ", maVariable, " devait valoir", uneAutreVariable, LoggerOption.LOG_FILE_ONLY); <br>
 *      // N'affiche rien à l'écran mais sauvegarde dans le dossier une ligne, <br>
 *      // ici "Variable 1 66 variable 2 Jean DUJARDIN, COLLABORATOR, Jardinier" (sans quote) </code><br>
 */
public abstract class Logger {
    /**
     * Pour écrire dans le fichier
     */
    private static PrintWriter printWriter = null;

    /**
     * Nombre de lignes écrites, sauvegarde le fichier toutes lees 10 lignes
     */
    private static int nbWrite = 0;

    /**
     * Initialisation
     */
    public static void init() {
        if (printWriter != null) {
            return;
        }

        boolean ok;
        boolean dirCreated = false;
        BufferedWriter bufferedWriter = null;

        try {
            dirCreated = new File(Location.getPath() + "/logs").mkdir();
            FileWriter fileWriter = new FileWriter(Location.getPath() + "/logs/log_" + getDate() + ".log");
            bufferedWriter = new BufferedWriter(fileWriter);
            ok = true;
        }
        catch (IOException e) {
            e.printStackTrace();
            ok = false;
        }

        if (ok) {
            printWriter = new PrintWriter(bufferedWriter);

            info("Début des logs", LOG_FILE_ONLY);

            if (dirCreated) {
                warning("Dossier logs créé");
            }
        }
        else {
            error("Erreur de log");
        }
    }

    /**
     * Log générique
     *
     * @param where System.out, System.err, ou même possiblement ailleurs
     * @param args Arguments (messages, options de log...)
     * @param type Type de log (debug, warning...)
     */
    private static void genericLog(PrintStream where, Object[] args, LoggerType type) {
        ArrayList<String> messages = new ArrayList<>();
        ArrayList<LoggerOption> options = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof LoggerOption) {
                options.add((LoggerOption) arg);
            }
            else {
                messages.add(String.valueOf(arg));
            }
        }

        final String separator = " ";

        StringBuilder message = new StringBuilder();

        for (int i=0; i<messages.size(); i++) {
            message.append(messages.get(i));

            if (i != messages.size()-1) {
                message.append(separator);
            }
        }

        if (!options.contains(LOG_FILE_ONLY)) {
            where.println(type.getTextColor() + message.toString() + ANSI_RESET);
        }

        if (!options.contains(LOG_CONSOLE_ONLY)) {
            writeToFile(message.toString(), type);
        }
    }

    /**
     * Info
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void info(Object... args) {
        genericLog(System.out, args, INFO);
    }

    /**
     * Stats
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void stats(Object... args) {
        genericLog(System.out, args, STATS);
    }

    /**
     * Succès
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void success(Object... args) {
        genericLog(System.out, args, SUCCESS);
    }

    /**
     * Erreur
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void error(Object... args) {
        genericLog(System.err, args, ERROR);
    }

    /**
     * Warning
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void warning(Object... args) {
        genericLog(System.err, args, WARNING);
    }

    /**
     * Debug
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void debug(Object... args) {
        genericLog(System.out, args, DEBUG);
    }

    /**
     * Récupère l'heure
     *
     * @return Heure sous forme "hh:mm:ss"
     */
    private static String getHour() {
        GregorianCalendar c = new GregorianCalendar();
        return String.format("%02d", c.get(Calendar.HOUR_OF_DAY))
                + ":" + String.format("%02d", c.get(Calendar.MINUTE))
                + ":" + String.format("%02d", c.get(Calendar.SECOND));
    }

    /**
     * Récupère la date
     *
     * @return Date sous forme "yyyy-mm-dd_hh-mm-ss"
     */
    private static String getDate() {
        GregorianCalendar c = new GregorianCalendar();
        return c.get(Calendar.YEAR)
                + "-" + String.format("%02d", c.get(Calendar.MONTH)+1)
                + "-" + String.format("%02d", c.get(Calendar.DAY_OF_MONTH))
                + "_" + String.format("%02d", c.get(Calendar.HOUR_OF_DAY))
                + "-" + String.format("%02d", c.get(Calendar.MINUTE))
                + "-" + String.format("%02d", c.get(Calendar.SECOND));
    }

    /**
     * Écrit dans le fichier
     *
     * @param msg Messsage à écrire
     * @param type Type de log
     */
    private static void writeToFile(String msg, LoggerType type) {
        if (printWriter != null) {
            String toPrint = "[";
            toPrint += nbWrite + "-";
            toPrint += getHour() + "-";
            // toPrint += String.format("%7s", type.toString()) + "] ";
            toPrint += type.toString() + "] ";
            toPrint += msg;

            printWriter.println(toPrint);

            nbWrite ++;

            if (nbWrite % 10 == 0) {
                // Sauvegarde auto toutes les 10 lignes
                printWriter.flush();
            }
        }
    }

    /**
     * Pour quitter
     */
    public static void exit() {
        if (printWriter != null) {
            printWriter.close();
        }
    }
}
