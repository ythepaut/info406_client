package fr.groupe4.clientprojet.display.dialog.projectcreationdialog.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.projectcreationdialog.controller.EventProjectCreation;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * Fenetre de création de projet
 */
public class ProjectCreationDialog extends DrawDialog {

    //Les attributs du projet
    public String strnomprojet;
    public String strdescription;
    public String strdate;
    public Temporal localDate;

    //Ce qu'il faut pour bien configurer la date
    private String datePattern = "d/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    GridBagConstraints c = new GridBagConstraints();
    /**
     * La frame qui appelle ce dialog
     */
    private JFrame owner;

    /**
     * Constructeur
     * @param owner
     */
    public ProjectCreationDialog(JFrame owner) {
        setTitle("Fenêtre de création de Projet");
        this.owner = owner;
        setModal(true);
        setSize(300, 550);
        setResizable(false);
        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        drawContent();
        setVisible(true);
    }



    /**
     * Dessine le contenu du dialog de création
     */
    @Override
    protected void drawContent() {
        //Déclaration du layout
        setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5,0,5,0);

        // Entrée du nom et description de projet
        JLabel labelnom = new JLabel("Entrez le nom du projet : ");
        add(labelnom,c);
        JTextField nomprojet = new JTextField(15);
        c.gridy++;
        add(nomprojet,c);
        String strnomprojet = nomprojet.getText();

        c.gridy++;
        JLabel labeldescription = new JLabel("Entrez la description du projet : ");
        add(labeldescription,c);
        c.gridy++;
        JTextArea description = new JTextArea(18, 22);
        description.setLineWrap(true);
        add(description,c);
        String strdescription = description.getText();


        // Entrée de la date limite du projet
        c.gridy++;
        JLabel datefinlabel = new JLabel("Entrez la date limite du projet : ");
        add(datefinlabel, c);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker = new DatePicker(dateSettings);
        c.gridy++;
        add(datePicker, c);

        // Création des boutons de confirmation/annulation
        c.gridwidth = 1;
        c.gridy++;
        JButton creeprojet = new JButton("Création Projet");
        creeprojet.addActionListener(new EventProjectCreation(this, datePicker, nomprojet, description));
        add(creeprojet,c);
        c.gridx++;
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new GenericExitEvent(this));
        add(cancelButton,c);

    }
}
