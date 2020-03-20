package fr.groupe4.clientprojet;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.view.ConnectionDialog;
import fr.groupe4.clientprojet.display.mainwindow.view.MainWindow;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.Parameters;

public class Main {

    public static void main(String[] args) {
        Logger.init();
        Parameters.init();

        if (!Communication.isConnected()) {
            new ConnectionDialog();
        }
        if (Communication.isConnected()) {
            new MainWindow("Team's Project");
        }
    }

    public static void exit() {
        Communication.exit();
        Logger.exit();
        Parameters.exit();
    }

}
