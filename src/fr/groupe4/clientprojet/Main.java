package fr.groupe4.clientprojet;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.view.ConnectionDialog;
import fr.groupe4.clientprojet.display.mainwindow.view.MainWindow;

public class Main {

    public static void main(String[] args) {
        if (!Communication.isConnected()) {
            new ConnectionDialog();
        }
        //if (Communication.isConnected()) {
            new MainWindow("Team's Project");
        //}
    }

}
