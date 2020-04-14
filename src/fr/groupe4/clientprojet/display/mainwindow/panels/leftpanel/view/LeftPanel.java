package fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.mainwindow.panels.centerpanel.view.CenterPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.controller.EventLeftPanel;
import fr.groupe4.clientprojet.display.mainwindow.view.MainWindow;
import fr.groupe4.clientprojet.display.view.ScrollBarUI;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.project.ProjectList;
import fr.groupe4.clientprojet.display.view.draw.DrawPanel;
import fr.groupe4.clientprojet.model.resource.human.User;
import fr.groupe4.clientprojet.utils.Location;
import fr.groupe4.clientprojet.display.view.RoundButton;

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
public class LeftPanel extends DrawPanel {
    /**
     * La liste des boutons
     */
    private ArrayList<RoundButton> buttons;
    /**
     * le panel du centre
     */
    private CenterPanel centerPanel;
    private MainWindow owner;
    private boolean first = true;
    /**
     * La liste des projets
     */
    private ProjectList projectList;

    /**
     * Le constructeur
     *
     * @param centerPanel : le panel du centre
     */
    public LeftPanel(CenterPanel centerPanel, MainWindow owner) {
        buttons = new ArrayList<>();
        this.centerPanel = centerPanel;
        this.owner = owner;
        Communication comm = Communication.builder().sleepUntilFinished().startNow().getProjectList().build();
        projectList = (ProjectList) comm.getResult();


        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK), new EmptyBorder(20, 10, 20, 10)));

        drawContent();
    }

    /**
     * Renvoie la liste des boutons
     *
     * @return : les boutons
     */
    public ArrayList<RoundButton> getButtons() {
        return buttons;
    }

    /**
     * Dessine le contenu du panel
     */
    @Override
    protected void drawContent() {
        EventLeftPanel eventLeftPanel = new EventLeftPanel(this, centerPanel);
        final int TAILLE_BOUTONS = 25; // TODO: Le final pourra être enlevé quand on ajoutera les paramètres
        Font buttonFont = new Font("Arial", Font.PLAIN, TAILLE_BOUTONS);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(15, 0, 15, 0);

        // Boutons projets
        drawProjectButton(eventLeftPanel, buttonFont);


        // Boutons du bas (calendrier, profil)
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(new CompoundBorder(new EmptyBorder(25, 0, 0, 0),
                new MatteBorder(3, 0, 0, 0, Color.BLACK)));
        bottomPanel.setBackground(Color.WHITE);

        RoundButton button = new RoundButton(new File(Location.getImgDataPath() + "/plus.png"));
        if(User.getUser().canCreateProject()) {
            c.gridy = 0;
            buttons.add(button);
            button.setActionCommand(EventLeftPanel.NEWPROJECT);
            button.addActionListener(eventLeftPanel);
            button.setFont(buttonFont);
            bottomPanel.add(button, c);
        }
        c.gridy = 1;
        button = new RoundButton(new File(Location.getImgDataPath() + "/calendar.png"));
        buttons.add(button);
        button.setActionCommand(CenterPanel.CALENDAR);
        button.addActionListener(eventLeftPanel);
        button.setFont(buttonFont);
        bottomPanel.add(button, c);
        c.gridy = 2;
        button = new RoundButton(new File(Location.getImgDataPath() + "/user.png"));
        buttons.add(button);
        button.setActionCommand(CenterPanel.USER);
        if (first) {
            button.setSelected(true);
            first = false;
        }
        button.addActionListener(eventLeftPanel);
        button.setFont(buttonFont);
        bottomPanel.add(button, c);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Dessine la liste des projets
     *
     * @param eventLeftPanel : le listener du panel
     * @param buttonFont : la police des boutons
     */
    private void drawProjectButton(EventLeftPanel eventLeftPanel, Font buttonFont) {
        JPanel projectPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 5, 0);
        projectPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(projectPanel);
        scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBar(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        for (Project p: projectList) {
            String name = p.getName();
            RoundButton button = new RoundButton(name.substring(0, 1));
            button.setActionCommand(name);
            button.addActionListener(eventLeftPanel);
            button.setFont(buttonFont);
            projectPanel.add(button, c);
            c.gridy++;
            buttons.add(button);
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    public MainWindow getOwner(){
        return owner;
    }
}
