package fr.groupe4.clientprojet;

import java.awt.*;

public class ExitDialog extends Dialog {
    private EventExitDialog eventExitDialog;

    public ExitDialog(Window owner) {
        super(owner, "Êtes-vous sûr de vouloir quitter ?");

        eventExitDialog = new EventExitDialog(this); // Pour les events


        setSize(300, 70);
        setResizable(false);
        setLocationRelativeTo(null); // Place la fenêtre au centre

        GridLayout layout = new GridLayout(1, 2);
        setLayout(layout);
        Button exitButton = new Button("Quitter");
        exitButton.setActionCommand(EventExitDialog.EXIT);
        exitButton.addActionListener(eventExitDialog);
        add(exitButton);
        Button cancelButton = new Button("Annuler");
        cancelButton.setActionCommand(EventExitDialog.CANCEL);
        cancelButton.addActionListener(eventExitDialog);
        add(cancelButton);


        setVisible(true);
    }
}
