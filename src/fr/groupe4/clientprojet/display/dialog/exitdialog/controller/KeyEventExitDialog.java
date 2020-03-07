package fr.groupe4.clientprojet.display.dialog.exitdialog.controller;

import fr.groupe4.clientprojet.Main;
import fr.groupe4.clientprojet.display.dialog.exitdialog.view.ExitDialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Le listener du clavier pour le dialog de sortie
 */
public class KeyEventExitDialog extends KeyAdapter {
    /**
     * Le dialog de sortie
     */
    private ExitDialog source;

    /**
     * Le constructeur
     *
     * @param source : Le dialog de sortie
     */
    public KeyEventExitDialog(ExitDialog source) {
        this.source = source;
    }

    /**
     * Quand une touche est préssée
     *
     * @param e : L'event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                source.getOwner().dispose();
                source.dispose();
                Main.exit();
                break;

            case KeyEvent.VK_ESCAPE:
                source.dispose();
                break;

            default:
        }
    }
}
