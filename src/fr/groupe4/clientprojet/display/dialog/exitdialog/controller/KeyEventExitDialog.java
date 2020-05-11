package fr.groupe4.clientprojet.display.dialog.exitdialog.controller;

import fr.groupe4.clientprojet.Main;
import fr.groupe4.clientprojet.display.dialog.exitdialog.view.ExitDialog;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Le listener du clavier pour le dialog de sortie
 */
public class KeyEventExitDialog extends KeyAdapter {
    /**
     * Le dialog de sortie
     */
    @NotNull
    private final ExitDialog source;

    /**
     * Le constructeur
     *
     * @param source Dialog de sortie
     */
    public KeyEventExitDialog(@NotNull ExitDialog source) {
        this.source = source;
    }

    /**
     * Quand une touche est pressée
     *
     * @param e Event
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
                break;
        }
    }
}
