package fr.groupe4.clientprojet.logger;

import fr.groupe4.clientprojet.logger.enums.LoggerType;
import fr.groupe4.clientprojet.utils.Location;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static fr.groupe4.clientprojet.logger.enums.LoggerColor.*;
import static fr.groupe4.clientprojet.logger.enums.LoggerType.*;

public abstract class Logger {
    private static PrintWriter printWriter = null;
    private static int nbWrite = 0;

    public static void init() {
        try {
            new File(Location.getPath() + "/logs").mkdir();
            FileWriter fileWriter = new FileWriter(Location.getPath() + "/logs/log_" + getFullDate() + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void info(Object msg) {
        System.out.println(msg);
    }

    public static void stats(Object msg) {
        System.out.println(ANSI_CYAN + String.valueOf(msg) + ANSI_RESET);
    }

    public static void success(Object msg) {
        System.out.println(ANSI_GREEN + String.valueOf(msg) + ANSI_RESET);
    }

    public static void error(Object msg) {
        System.err.println(ANSI_RED + String.valueOf(msg) + ANSI_RESET);
    }

    public static void warning(Object msg) {
        System.err.println(ANSI_YELLOW + String.valueOf(msg) + ANSI_RESET);
    }

    public static void debug(Object msg) {
        System.out.println(ANSI_PURPLE + String.valueOf(msg) + ANSI_RESET);
        writeToFile(msg, DEBUG);
    }

    private static String getDate() {
        GregorianCalendar c = new GregorianCalendar();
        return String.format("%02d", c.get(Calendar.HOUR_OF_DAY))
                + ":" + String.format("%02d", c.get(Calendar.MINUTE))
                + ":" + String.format("%02d", c.get(Calendar.SECOND));
    }

    private static String getFullDate() {
        GregorianCalendar c = new GregorianCalendar();
        return c.get(Calendar.YEAR)
                + "-" + String.format("%02d", c.get(Calendar.MONTH)+1)
                + "-" + String.format("%02d", c.get(Calendar.DAY_OF_MONTH))
                + "_" + String.format("%02d", c.get(Calendar.HOUR_OF_DAY))
                + "-" + String.format("%02d", c.get(Calendar.MINUTE))
                + "-" + String.format("%02d", c.get(Calendar.SECOND));
    }

    private static void writeToFile(Object msg, LoggerType type) {
        if (printWriter != null) {
            String toPrint = "[";
            toPrint += String.valueOf(nbWrite) + "-";
            toPrint += getDate() + "-";
            // toPrint += String.format("%7s", type.toString()) + "] ";
            toPrint += type.toString() + "] ";
            toPrint += String.valueOf(msg);

            printWriter.println(toPrint);

            nbWrite ++;

            if (nbWrite % 10 == 0) {
                printWriter.flush();
            }
        }
    }

    public static void exit() {
        if (printWriter != null) {
            printWriter.close();
        }
    }
}
