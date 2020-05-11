package fr.groupe4.clientprojet.display.view.slide.view;

import fr.groupe4.clientprojet.display.view.RoundButton;
import fr.groupe4.clientprojet.display.view.slide.SlideItem;
import fr.groupe4.clientprojet.display.view.slide.controller.EventSlide;
import fr.groupe4.clientprojet.display.view.slide.enums.SlideMove;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

/**
 * Permet de créer des slides <br>
 * Un peu comme les onglets, mais en mieux
 */
public class Slide extends JPanel {
    /**
     * Le slide sur lequel on est
     */
    private int slideId;

    /**
     * Slides sous forme de tuple
     */
    private ArrayList<SlideItem> slides;

    /**
     * Le listener du panel
     */
    private final EventSlide eventSlide;

    /**
     * Le constructeur
     *
     * @param slides Liste des noms et des panels associés
     */
    public Slide(ArrayList<SlideItem> slides) {
        this.slides = slides;

        slideId = slides.isEmpty() ? -1 : 0;

        eventSlide = new EventSlide(this);

        if (!slides.isEmpty()) {
            drawContent();
        }
    }

    /**
     * Le constructeur par défaut <br>
     * Il n'y a aucun slide de base, il faut les rajouter via la méthode <code>addSlide</code>
     *
     * @see #addSlide
     */
    public Slide() {
        this(new ArrayList<>());
    }

    /**
     * Permet d'ajouter un slide
     * Redéssine le panel
     *
     * @param item Item à ajouter (tuple)
     */
    public void addSlide(@NotNull SlideItem item) {
        slides.add(item);

        if (slideId == -1) {
            slideId = 0;
        }

        redraw();
    }

    /**
     * Dessine le contenu du panel
     */
    private void drawContent() {
        setLayout(new BorderLayout());
        setBackground(Theme.FOND.getColor(Parameters.getThemeName()));

        // Bouton gauche
        RoundButton leftButton = new RoundButton("<");
        leftButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        leftButton.setActionCommand(SlideMove.LEFT.getName());
        leftButton.addActionListener(eventSlide);
        add(leftButton, BorderLayout.WEST);
        // Bouton droite
        RoundButton rightButton = new RoundButton(">");
        rightButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        rightButton.setActionCommand(SlideMove.RIGHT.getName());
        rightButton.addActionListener(eventSlide);
        add(rightButton, BorderLayout.EAST);
        // Bouton haut
        JPanel topButtons = new JPanel(new FlowLayout());
        topButtons.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));

        for (SlideItem slide : slides) {
            String name = slide.getName();
            JButton button = new JButton(name);
            button.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
            button.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
            button.setActionCommand(name);
            button.addActionListener(eventSlide);
            topButtons.add(button);
        }

        add(topButtons, BorderLayout.NORTH);

        if (!slides.isEmpty() && slideId != -1) {
            add(slides.get(slideId).getPanel(), BorderLayout.CENTER);
        }
    }

    /**
     * Défini le slide sur lequel on veut être
     *
     * @param slideId Numéro du slide sur lequel on veut être
     */
    public void setSlide(int slideId) {
        this.slideId = Math.max(0, Math.min(slideId, slides.size()-1));
        redraw();
    }

    /**
     * Renvoie le slide sur lequel on est
     *
     * @return Numéro du slide
     */
    public int getSlide() {
        return slideId;
    }

    /**
     * Renvoie la liste des noms des slides
     *
     * @return les items
     */
    public ArrayList<SlideItem> getSlideItems() {
        return slides;
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
