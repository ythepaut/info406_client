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
    private int nbProjet, debutListe = 0, scrollValue = 0;

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
        Font buttonFont = new Font("Arial", Font.PLAIN, TAILLE_BOUTONS);
        GridBagConstraints c = new GridBagConstraints(); c.gridx = 0; c.insets = new Insets(25, 0, 25, 0);

        // Boutons projets
        drawProjectButton(eventLeftPanel, buttonFont, c);


        // Boutons du bas (calendrier, profil)
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(new MatteBorder(3, 0, 0, 0, Color.BLACK));
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

    private void drawProjectButton(EventLeftPanel eventLeftPanel, Font buttonFont, GridBagConstraints c) {
        JPanel projectPanel = new JPanel(new BorderLayout());
        setNbProjets(6); // TODO: Cette variable sera déterminé par le nombre de projets reçu par le modèle

        // La scrollBar
        int nbProjetsMax = 5; // TODO: Valeur à déterminer en fonction de la taille de la fenêtre
        if (nbProjet > nbProjetsMax) {
            JPanel scrollPanel = new JPanel(new BorderLayout());
            JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL, scrollValue, 1, 0, nbProjet);
            scrollBar.addAdjustmentListener(eventLeftPanel);
            scrollBar.setVisible(true);
            scrollPanel.add(scrollBar, BorderLayout.CENTER);

            projectPanel.add(scrollPanel, BorderLayout.WEST);
        }


        // Les boutons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);

        for (int i = debutListe; i < nbProjetsMax + debutListe; i++) {
            c.gridy = i;
            if (i < nbProjet) {
                RoundButton button = new RoundButton(Integer.toString(i));
                button.setActionCommand(Integer.toString(i));
                button.addActionListener(eventLeftPanel);
                button.setFont(buttonFont);
                buttonPanel.add(button, c);
                buttons.add(button);
            } else {
                JLabel label = new JLabel(" ");
                label.setFont(buttonFont);
                buttonPanel.add(label, c);
            }
        }

        projectPanel.add(buttonPanel, BorderLayout.CENTER);


        add(projectPanel, BorderLayout.NORTH);
    }

    public void setNbProjets(int nbProjets) {
        this.nbProjet = nbProjets;
    }

    public void setDebutListe(int debutListe) {
        this.debutListe = debutListe;
    }

    public void setScrollValue(int scrollValue) {
        this.scrollValue = scrollValue;
    }

    public void redraw() {
        removeAll();
        validate();
        revalidate();
        repaint();
        drawContent();
    }
}
