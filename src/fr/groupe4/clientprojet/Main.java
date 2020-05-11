package fr.groupe4.clientprojet;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.view.ConnectionDialog;
import fr.groupe4.clientprojet.display.dialog.firstrundialog.view.FirstRunDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import fr.groupe4.clientprojet.display.mainwindow.view.MainWindow;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import org.jetbrains.annotations.NotNull;

/**
 * Classe principale
 */
public class Main {
    /**
     * Main
     *
     * @param args Arguments de ligne de commande
     */
    public static void main(@NotNull String[] args) {
        Logger.init();
        Parameters.init();

        if (Parameters.isFirstRun()) {
            new FirstRunDialog();
        }

        // Juste pour ne pas avoir à se connecter à chaque fois
        // TODO : enlever pour le rendu
        new LoadDialog(Communication.builder().connect("user3", "HippopotameParallèle").build(), null);

        if (!Communication.isConnected()) {
            new ConnectionDialog();
        }
        if (Communication.isConnected()) {
            new MainWindow("Team's Project");
        }
    }

    /**
     * À appeler à la sortie
     */
    public static void exit() {
        Communication.exit();
        Parameters.exit();
        Logger.exit();
    }

}
