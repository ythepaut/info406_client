package fr.groupe4.clientprojet.mainwindow.panels.centerpanel;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    public static final String CALENDAR = "calendar", USER = "user";
    private String view;

    public CenterPanel(String view) {
        this.view = view;


        setBackground(Color.WHITE);

        drawContent();
    }

    public void setView(String view) {
        this.view = view;

        // On redéssine
        removeAll();
        validate();
        revalidate();
        repaint();
        drawContent();
    }

    public String getView() {
        return view;
    }

    private void drawContent() {
        switch (view) {
            case CALENDAR:
                drawCalendar();
                break;

            case USER:
                drawUser();
                break;

            default:
                drawProject();
        }
    }

    private void drawCalendar() {
        setLayout(new BorderLayout());

        // Titre
        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        titlePanel.add(new JLabel(view));

        add(titlePanel, BorderLayout.NORTH);
    }

    private void drawUser() {
        setLayout(new BorderLayout());

        // Titre
        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        titlePanel.add(new JLabel(view));

        add(titlePanel, BorderLayout.NORTH);
    }

    private void drawProject() {
        setLayout(new BorderLayout());

        // Titre
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        JLabel title = new JLabel(view);
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        titlePanel.add(title, c);

        add(titlePanel, BorderLayout.NORTH);

        // Partie centrale (onglets)
        JTabbedPane tabbedPane = new JTabbedPane();
        // Onglet équipe
        JPanel teamPanel = new JPanel();
        // TODO: Ajouter liste des membres de l'équipe
        tabbedPane.addTab("Equipe", teamPanel);
        // Onglet tâches
        JPanel taskPanel = new JPanel();
        // TODO: Ajouter liste des tâches
        tabbedPane.addTab("Tâches", taskPanel);
        // Onglet messages
        JPanel messagePanel = new JPanel();
        // TODO: Ajouter messagerie générale du projet
        tabbedPane.addTab("Messages", messagePanel);

        add(tabbedPane, BorderLayout.CENTER);
    }
}
