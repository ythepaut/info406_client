package fr.groupe4.clientprojet.logger;

import fr.groupe4.clientprojet.logger.enums.LoggerColor;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import fr.groupe4.clientprojet.logger.enums.LoggerType;
import fr.groupe4.clientprojet.utils.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Gestion des logs <br>
 * <br>
 * Avantages : couleurs, fichier, surcharge d'arguments (sans restriction de nombre ou de type) <br>
 * Dossier des logs : ./logs (out/production/info406_client/logs pour IntelliJ) <br>
 * Chaque fichier .log est nommé selon l'heure à laquelle il a été créé <br>
 * <br>
 * Exemples d'utilisation simple : <br><code>
 * Logger.warning("Mauvais caractère"); <br>
 * // Affiche un texte en jaune et l'enregiste dans le fichier</code><br>
 * <br>
 * Exemple plus complexe : <br><code>
 * Logger.debug("variable ", maVariable, " devait valoir", uneAutreVariable, LoggerOption.LOG_FILE_ONLY); <br>
 * // N'affiche rien à l'écran mais sauvegarde dans le dossier une ligne, <br>
 * // ici "Variable 1 66 variable 2 Jean DUJARDIN, COLLABORATOR, Jardinier" (sans quote) </code><br>
 */
public abstract class Logger {
    /**
     * Pour écrire dans le fichier
     */
    @Nullable
    private static PrintWriter printWriter = null;

    /**
     * Nombre de lignes écrites, sauvegarde le fichier toutes lees 10 lignes
     */
    private static int nbWrite = 0;

    /**
     * Initialisation
     */
    public static synchronized void init() {
        if (printWriter != null) {
            return;
        }

        boolean ok;
        boolean dirCreated = false;
        BufferedWriter bufferedWriter = null;

        try {
            dirCreated = new File(Location.getPath() + "/logs").mkdir();

            FileWriter fileWriter = new FileWriter(
                    Location.getPath()
                            + "/logs/fr-groupe4-clientprojet_log_"
                            + getDate() + ".log"
            );

            bufferedWriter = new BufferedWriter(fileWriter);
            ok = true;
        } catch (IOException e) {
            e.printStackTrace();
            ok = false;
        }

        if (ok) {
            printWriter = new PrintWriter(bufferedWriter);

            info("Début des logs", LoggerOption.LOG_FILE_ONLY);

            if (dirCreated) {
                warning("Dossier logs créé");
            }
        } else {
            error("Erreur de log");
        }
    }

    /**
     * Log générique
     *
     * @param where System.out, System.err, ou même possiblement ailleurs
     * @param args  Arguments (messages, options de log...)
     * @param type  Type de log (debug, warning...)
     */
    private static synchronized void genericLog(@NotNull PrintStream where,
                                                @NotNull Object[] args,
                                                @NotNull LoggerType type) {
        ArrayList<String> messages = new ArrayList<>();
        ArrayList<LoggerOption> options = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof LoggerOption) {
                options.add((LoggerOption) arg);
            } else {
                messages.add(String.valueOf(arg));
            }
        }

        final String separator = " ";

        StringBuilder message = new StringBuilder();

        for (int i = 0; i < messages.size(); i++) {
            message.append(messages.get(i));

            if (i != messages.size() - 1) {
                message.append(separator);
            }
        }

        if (!options.contains(LoggerOption.LOG_FILE_ONLY)) {
            where.println(type.getTextColor() + message.toString() + LoggerColor.ANSI_RESET);
        }

        if (!options.contains(LoggerOption.LOG_CONSOLE_ONLY)) {
            writeToFile(message.toString(), type);
        }
    }

    /**
     * Info
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void info(Object... args) {
        genericLog(System.out, args, LoggerType.INFO);
    }

    /**
     * Stats
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void stats(Object... args) {
        genericLog(System.out, args, LoggerType.STATS);
    }

    /**
     * Succès
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void success(Object... args) {
        genericLog(System.out, args, LoggerType.SUCCESS);
    }

    /**
     * Erreur
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void error(Object... args) {
        genericLog(System.err, args, LoggerType.ERROR);
    }

    /**
     * Warning
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void warning(Object... args) {
        genericLog(System.err, args, LoggerType.WARNING);
    }

    /**
     * Debug
     *
     * @param args Arguments (messages, options de log...)
     */
    public static void debug(Object... args) {
        genericLog(System.out, args, LoggerType.DEBUG);
    }

    /**
     * Récupère l'heure
     *
     * @return Heure sous forme "hh:mm:ss"
     */
    @NotNull
    private static String getHour() {
        LocalTime now = LocalTime.now();

        return String.format("%02d", now.getHour())
                + ":" + String.format("%02d", now.getMinute())
                + ":" + String.format("%02d", now.getSecond())
                + ":" + String.format("%03d", now.getNano() / 1000);
    }

    /**
     * Récupère la date
     *
     * @return Date sous forme "yyyy-mm-dd_hh-mm-ss"
     */
    @NotNull
    private static String getDate() {
        LocalDateTime now = LocalDateTime.now();

        return now.getYear()
                + "-" + String.format("%02d", now.getMonthValue())
                + "-" + String.format("%02d", now.getDayOfMonth())
                + "@" + String.format("%02d", now.getHour())
                + "-" + String.format("%02d", now.getMinute())
                + "-" + String.format("%02d", now.getSecond());
    }

    /**
     * Écrit dans le fichier
     *
     * @param msg  Messsage à écrire
     * @param type Type de log
     */
    private static synchronized void writeToFile(@NotNull String msg, @NotNull LoggerType type) {
        if (printWriter == null) {
            init();

            warning("Logger initialisé en interne");
        }

        if (printWriter != null) {
            String toPrint = "[";
            toPrint += nbWrite + "-";
            toPrint += getHour() + "-";
            // toPrint += String.format("%7s", type.toString()) + "] ";
            toPrint += type + "] ";
            toPrint += msg;

            printWriter.println(toPrint);

            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            StringBuilder builder = new StringBuilder();
            builder.append("@");

            for (StackTraceElement stackTraceElement : stackTrace) {
                if (!stackTraceElement.toString().contains("java.desktop")
                        && !stackTraceElement.toString().contains("java.base")
                        && !stackTraceElement.toString().contains("Logger.writeToFile")
                        && !stackTraceElement.toString().contains("Logger.genericLog")) {

                    builder.append(" : ");
                    builder.append(stackTraceElement);
                }
            }

            printWriter.println(builder);

            nbWrite++;

            printWriter.flush();
        }
    }

    /**
     * Pour quitter
     */
    public static synchronized void exit() {
        if (printWriter != null) {
            writeToFile("Fin des logs, fermeture", LoggerType.INFO);
            printWriter.close();
        }
    }
}
