package fr.groupe4.clientprojet.display.view.slide.view;

import fr.groupe4.clientprojet.display.view.RoundButton;
import fr.groupe4.clientprojet.display.view.slide.controller.EventSlide;
import fr.groupe4.clientprojet.display.view.slide.enums.SlideMove;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Permet de créer des slides
 * Un peu comme les onglets, mais en mieux
 */
public class Slide extends JPanel {
    /**
     * Le slide sur lequel on est
     */
    private int slide;
    /**
     * Le nombre de slide total
     */
    private int nbSlide;
    /**
     * La liste des noms de slides
     */
    private ArrayList<String> slideName;
    /**
     * La liste des panels des slides
     */
    private ArrayList<JPanel> slidePanel;
    /**
     * Le listener du panel
     */
    private EventSlide eventSlide;

    /**
     * Le constructeur
     *
     * @param slidePanel : la liste des panel
     * @param slideName : la liste des noms
     */
    public Slide(ArrayList<JPanel> slidePanel, ArrayList<String> slideName) {
        this.slidePanel = slidePanel;
        this.slideName = slideName;
        nbSlide = slideName.size();
        slide = nbSlide > 0 ? 0 : -1;
        eventSlide = new EventSlide(this);

        if (nbSlide > 0) {
            drawContent();
        }
    }

    /**
     * Le constructeur par défaut
     * Il n'y a aucun slide de base, il faut les rajouter via la méthode addSlide(JPanel, String)
     */
    public Slide() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Permet d'ajouter un slide
     * Redéssine le panel
     *
     * @param panel : le panel
     * @param name : le nom du slide
     */
    public void addSlide(JPanel panel, String name) {
        nbSlide++;
        slideName.add(name);
        slidePanel.add(panel);

        if (slide == -1) {
            slide = 0;
        }

        redraw();
    }

    /**
     * Dessine le contenu du panel
     */
    private void drawContent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Bouton gauche
        RoundButton leftButton = new RoundButton("<");
        leftButton.setActionCommand(SlideMove.LEFT.getName());
        leftButton.addActionListener(eventSlide);
        add(leftButton, BorderLayout.WEST);
        // Bouton droite
        RoundButton rightButton = new RoundButton(">");
        rightButton.setActionCommand(SlideMove.RIGHT.getName());
        rightButton.addActionListener(eventSlide);
        add(rightButton, BorderLayout.EAST);
        // Bouton haut
        JPanel topButtons = new JPanel(new FlowLayout());
        topButtons.setBackground(Color.WHITE);
        for (String name: slideName) {
            JButton button = new JButton(name);
            button.setActionCommand(name);
            button.addActionListener(eventSlide);
            topButtons.add(button);
        }
        add(topButtons, BorderLayout.NORTH);
        if (nbSlide > 0 && slide != -1) {
            add(slidePanel.get(slide), BorderLayout.CENTER);
        }
    }

    /**
     * Défini le slide sur lequel on veut être
     *
     * @param slide : le numéro du slide sur lequel on veut être
     */
    public void setSlide(int slide) {
        this.slide = Math.max(0, Math.min(slide, nbSlide-1));
    }

    /**
     * Renvoie le slide sur lequel on est
     *
     * @return : Le numéro du slide
     */
    public int getSlide() {
        return slide;
    }

    /**
     * Renvoie la liste des noms des slides
     *
     * @return : les noms
     */
    public ArrayList<String> getSlideName() {
        return slideName;
    }

    /**
     * Redéssine le contenu
     */
    public void redraw() {
        removeAll();
        validate();
        revalidate();
        repaint();
        drawContent();
    }
}
