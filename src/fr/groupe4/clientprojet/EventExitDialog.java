package fr.groupe4.clientprojet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventExitDialog extends WindowAdapter implements ActionListener {
    public final static String EXIT = "exit";
    public final static String CANCEL = "cancel";

    private Dialog source;

    EventExitDialog(Dialog pSource) {
        source = pSource;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case EXIT:
                source.getOwner().dispose();
                System.exit(0);
                break;

            case CANCEL:
                source.dispose();
                break;

            default:
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
    }
}
