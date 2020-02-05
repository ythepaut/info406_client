package fr.groupe4.clientprojet.mainwindow.panels.leftpanel;


import fr.groupe4.clientprojet.mainwindow.panels.centerpanel.CenterPanel;
import fr.groupe4.clientprojet.utils.Location;
import fr.groupe4.clientprojet.utils.RoundButton;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Créé le panel de gauche de la fenêtre
 * Celui qui contient la liste des projets et le bouton 'calendrier' et 'profil'
 */
public class LeftPanel extends JPanel {
    private final int TAILLE_BOUTONS = 25; // TODO: Le final pourra être enlevé quand on ajoutera les paramètres
    private ArrayList<RoundButton> buttons;
    private CenterPanel centerPanel;

    public LeftPanel(CenterPanel centerPanel) {
        buttons = new ArrayList<>();
        this.centerPanel = centerPanel;


        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK), new EmptyBorder(20, 10, 20, 10)));

        drawContent();
    }

    public ArrayList<RoundButton> getButtons() {
        return buttons;
    }

    private void drawContent() {
        EventLeftPanel eventLeftPanel = new EventLeftPanel(this, centerPanel);

        // Boutons projets
        JPanel projectPanel = new JPanel(new GridBagLayout());
        projectPanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints(); c.gridx = 0; c.insets = new Insets(25, 0, 25, 0);

        Font buttonFont = new Font("Arial", Font.PLAIN, TAILLE_BOUTONS);
        for (int i = 0; i < 5; i++) {
            c.gridy = i;
            RoundButton button = new RoundButton(Integer.toString(i));
            button.setActionCommand(Integer.toString(i));
            button.addActionListener(eventLeftPanel);
            button.setFont(buttonFont);
            projectPanel.add(button, c);
            buttons.add(button);
        }

        add(projectPanel, BorderLayout.NORTH);


        // Boutons du bas (calendrier, profil)
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.WHITE);
        c.gridy = 0;
        RoundButton button = new RoundButton(new File(Location.getPath() + "/calendar.png"));
        buttons.add(button);
        button.setActionCommand(CenterPanel.CALENDAR);
        button.addActionListener(eventLeftPanel);
        button.setFont(buttonFont);
        bottomPanel.add(button, c);
        c.gridy = 1;
        button = new RoundButton("P");
        buttons.add(button);
        button.setActionCommand(CenterPanel.USER);
        button.setSelected(true);
        button.addActionListener(eventLeftPanel);
        button.setFont(buttonFont);
        bottomPanel.add(button, c);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
